package com.flex.dhp.patientservice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

/**
 * Created by david.airth on 7/11/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CarecardRestControllerTests {
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private String firstName = "Jane";
    private String lastName = "Doe";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Patient patient;

    private List<Carecard> carecardList = new ArrayList<>();

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CarecardRepository cardcareRepository;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

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
    public void carecardNotFound() throws Exception {
        mockMvc.perform(get("/carecards/" + this.patient.getId() + "/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void readSingleCarecard() throws Exception {
        mockMvc.perform(get("/carecards/" + this.patient.getId() + "/" + this.carecardList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.carecardList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.name", is(this.carecardList.get(0).getName())));
    }

    @Test
    public void readCarecards() throws Exception {
        mockMvc.perform(get("/carecards/" + this.patient.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(this.carecardList.get(0).getId().intValue())))
                .andExpect(jsonPath("$[1].id", is(this.carecardList.get(1).getId().intValue())));
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

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}