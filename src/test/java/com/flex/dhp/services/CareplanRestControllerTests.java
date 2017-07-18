package com.flex.dhp.services;

import com.flex.dhp.services.careplan.Careplan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by david.airth on 7/11/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CareplanRestControllerTests extends AbstractRestControllerTests {

    private String baseUrl = "/patients/%s/careplans";
    @Test
    public void careplanNotFound() throws Exception {
        mockMvc.perform(get(String.format(baseUrl, this.patient.getId()) + "/3333333"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getSingleCareplan() throws Exception {
        String url = String.format(baseUrl, this.patient.getId()) + "/" + this.careplanList.get(0).getId();
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.careplanList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.name", is(this.careplanList.get(0).getName())));
    }

    @Test
    public void getPatientPlans() throws Exception {
        mockMvc.perform(get(String.format(baseUrl, this.patient.getId())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(this.careplanList.get(0).getId().intValue())))
                .andExpect(jsonPath("$[1].id", is(this.careplanList.get(1).getId().intValue())));
    }

    @Test
    public void deleteCareplan() throws Exception {
        String url = String.format(baseUrl, this.patient.getId()) + "/" + this.careplanList.get(0).getId();

        mockMvc.perform(delete(url))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(String.format(baseUrl, this.patient.getId())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(this.careplanList.get(1).getId().intValue())));
    }

    @Test
    public void createCarecard() throws Exception {

        String carecardJson = json(new Careplan(this.patient, "A new care plan"));

        String urlTemplate = String.format(baseUrl, this.patient.getId());

        this.mockMvc.perform(post(urlTemplate)
                .contentType(contentType)
                .content(carecardJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(3)));
    }
}
