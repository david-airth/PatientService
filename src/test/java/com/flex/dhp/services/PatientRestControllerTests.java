package com.flex.dhp.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by david.airth on 7/10/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class PatientRestControllerTests extends BaseRestControllerTests {

    @Test
    public void patientNotFound() throws Exception {
        mockMvc.perform(get("/patient/99999/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getSinglePatient() throws Exception {
        mockMvc.perform(get("/patient/" + this.patient.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.patient.getId().intValue())))
                .andExpect(jsonPath("$.firstname", is(this.patient.getFirstname())))
                .andExpect(jsonPath("$.lastname", is(this.patient.getLastname())));
    }
}
