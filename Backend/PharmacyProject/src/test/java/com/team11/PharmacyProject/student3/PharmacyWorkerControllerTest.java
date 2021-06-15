package com.team11.PharmacyProject.student3;

import com.team11.PharmacyProject.security.JWTTokenUtil;
import com.team11.PharmacyProject.security.JWTUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.test.context.SpringIntegrationTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
@SpringIntegrationTest
@WebAppConfiguration
public class PharmacyWorkerControllerTest {

    private static final String URL_PREFIX = "/api/workers";

    private final MediaType mediaType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(authorities = {"DERMATOLOGIST"})
    public void getWorkerCalendar() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/calendarAppointments/7"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType)).andExpect(jsonPath("$", hasSize(11)))
                .andExpect(jsonPath("$.[*].appointmentState").value(hasItem("EMPTY"))) //jer je derm a ima slobodne
                .andExpect(jsonPath("$.[*].appointmentState").value(hasItem("RESERVED")))
                .andExpect(jsonPath("$.[*].appointmentState").value(hasItem("FINISHED")))
                .andExpect(jsonPath("$.[*].start").value(hasItem(1621955200000L)))
                .andExpect(jsonPath("$.[*].end").value(hasItem(1621955700000L)))
                .andExpect(jsonPath("$.[*].pharmacy").value(hasItem("Zelena Apoteka")));
    }
}