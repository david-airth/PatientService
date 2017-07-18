package com.flex.dhp.services;

import com.flex.dhp.services.assessment.Assessment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by david.airth on 7/11/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class AssessmentRestControllerTests extends AbstractRestControllerTests {

    private String baseUrl = "/patients/%s/assessments";

    @Test
    public void assessmentNotFound() throws Exception {
        mockMvc.perform(get(String.format(baseUrl, this.patient.getId()) + "/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getSingleAssessment() throws Exception {
        mockMvc.perform(get(String.format(baseUrl, this.patient.getId()) + "/" + this.assessmentList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.assessmentList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.title", is(this.assessmentList.get(0).getTitle())));
    }

    @Test
    public void getPatientAssessments() throws Exception {
        mockMvc.perform(get(String.format(baseUrl, this.patient.getId())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id", is(this.assessmentList.get(0).getId().intValue())))
                .andExpect(jsonPath("$[1].id", is(this.assessmentList.get(1).getId().intValue())));
    }

    @Test
    public void deleteAssessment() throws Exception {
        mockMvc.perform(delete(String.format(baseUrl, this.patient.getId()) + "/" + this.assessmentList.get(0).getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(String.format(baseUrl, this.patient.getId())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(this.assessmentList.get(1).getId().intValue())));
    }

    @Test
    public void createAssessment() throws Exception {

        Assessment a = new Assessment(this.careplanList.get(0), "A new assessment");
        a.setInstructions("Instructions are long");
        a.setText("More text");

        String assessmentJson = json(a);

        String urlTemplate = String.format(baseUrl, this.patient.getId());

        this.mockMvc.perform(post(urlTemplate)
                .contentType(contentType)
                .content(assessmentJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(5)));
    }
}
