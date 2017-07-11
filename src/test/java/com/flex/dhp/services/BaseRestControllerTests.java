package com.flex.dhp.services;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by david.airth on 7/11/17.
 */
public class BaseRestControllerTests {
    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    protected MockMvc mockMvc;

    protected String firstName = "Jane";
    protected String lastName = "Doe";

    protected HttpMessageConverter mappingJackson2HttpMessageConverter;

    protected Patient patient;

    protected List<Carecard> carecardList = new ArrayList<>();

    @Autowired
    protected PatientRepository patientRepository;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected CarecardRepository cardcareRepository;

    @Autowired
    protected void setConverters(HttpMessageConverter<?>[] converters) {

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

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
