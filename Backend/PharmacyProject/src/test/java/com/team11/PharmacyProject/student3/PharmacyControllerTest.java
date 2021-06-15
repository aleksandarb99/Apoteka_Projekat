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
public class PharmacyControllerTest {

    private static final String URL_PREFIX = "/api/pharmacy";

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
    public void getPharmacyAlternativeMedicineTest() throws Exception {
        //indirektno smo proverili da se u listi lekova za terapiju ne nalazi lek 3 koji bi se
        // inace tu nasao jer je alternativan i ima ga na stanju
        mockMvc.perform(get(URL_PREFIX + "/getAlternativeFromPharmacy")
                .param("worker_id", "6")
                .param("pharm_id", "1")
                .param("patient_id", "1")
                .param("medicine_item_id", "7")
                .param("medicine_id", "5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType)).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[*].medicineID").value(hasItem(1)))
                .andExpect(jsonPath("$.[*].medicineItemID").value(hasItem(1)))
                .andExpect(jsonPath("$.[*].medicineID").value(hasItem(2)))
                .andExpect(jsonPath("$.[*].medicineItemID").value(hasItem(2)));

    }
}