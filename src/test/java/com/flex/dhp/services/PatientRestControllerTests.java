package com.flex.dhp.services;

import com.flex.dhp.services.patient.Patient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by david.airth on 7/10/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class PatientRestControllerTests extends AbstractRestControllerTests {

    @Test
    public void patientNotFound() throws Exception {
        mockMvc.perform(get("/patients/99999/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getSinglePatient() throws Exception {
        mockMvc.perform(get("/patients/" + this.patient.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.patient.getId().intValue())))
                .andExpect(jsonPath("$.firstname", is(this.patient.getFirstname())))
                .andExpect(jsonPath("$.lastname", is(this.patient.getLastname())));
    }

    @Test
    public void createPatient() throws Exception {

        String patientJson = json(new Patient("John", "Smith"));

        String urlTemplate = "/patients";

        this.mockMvc.perform(post(urlTemplate)
                .contentType(contentType)
                .content(patientJson))
                .andExpect(jsonPath("$.id", not(0)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updatePatient() throws Exception {

        Assert.assertNull(this.patient.getUpdatedDate());
        String newFirstName = "Joan";
        Assert.assertEquals(this.patient.getFirstname(), firstName);
        Assert.assertNotEquals(this.patient.getFirstname(), newFirstName);
        this.patient.setFirstname(newFirstName);

        String patientJson = json(this.patient);

        String urlTemplate = String.format("/patients/%s", this.patient.getId());

        this.mockMvc.perform(put(urlTemplate)
                .contentType(contentType)
                .content(patientJson))
                .andExpect(jsonPath("$.firstname", is(newFirstName)))
                .andExpect(jsonPath("$.updatedDate", notNullValue()))
                .andExpect(status().isOk());
    }
}
