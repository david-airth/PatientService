package com.flex.dhp.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by david.airth on 7/10/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class PatientRestControllerTests extends BaseRestControllerTests {

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.cardcareRepository.deleteAllInBatch();
        this.patientRepository.deleteAllInBatch();

        this.patient = patientRepository.save(new Patient(firstName, lastName));
        this.carecardList.add(cardcareRepository.save(new Carecard(patient, "Care Card 1")));
        this.carecardList.add(cardcareRepository.save(new Carecard(patient, "Care Card 2")));
    }

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
