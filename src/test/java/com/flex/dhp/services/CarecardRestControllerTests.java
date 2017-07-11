package com.flex.dhp.services;

import com.flex.dhp.services.carecard.Carecard;
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
public class CarecardRestControllerTests extends BaseRestControllerTests {

    @Test
    public void carecardNotFound() throws Exception {
        mockMvc.perform(get("/carecards/" + this.patient.getId() + "/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getSingleCarecard() throws Exception {
        mockMvc.perform(get("/carecards/" + this.patient.getId() + "/" + this.carecardList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.carecardList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.name", is(this.carecardList.get(0).getName())));
    }

    @Test
    public void getPatientCarecards() throws Exception {
        mockMvc.perform(get("/carecards/" + this.patient.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(this.carecardList.get(0).getId().intValue())))
                .andExpect(jsonPath("$[1].id", is(this.carecardList.get(1).getId().intValue())));
    }

    @Test
    public void deleteCarecard() throws Exception {
        mockMvc.perform(delete("/carecards/" + this.patient.getId() + "/" + this.carecardList.get(0).getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/carecards/" + this.patient.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(this.carecardList.get(1).getId().intValue())));
    }

    @Test
    public void createCarecard() throws Exception {

        String carecardJson = json(new Carecard(this.patient, "A new CareCard"));

        String urlTemplate = String.format("/carecards/%s", this.patient.getId());

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
