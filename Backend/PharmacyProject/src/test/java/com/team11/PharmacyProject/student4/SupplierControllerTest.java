package com.team11.PharmacyProject.student4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.integration.test.context.SpringIntegrationTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@SpringIntegrationTest
@WebAppConfiguration
public class SupplierControllerTest {
    private static final String URL_PREFIX = "/api/suppliers";

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
    @WithMockUser(authorities = {"SUPPLIER"})
    public void getStockTest() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/stock/5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType)).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[*].medicineId").value(hasItem(1)))
                .andExpect(jsonPath("$.[*].medicineName").value(hasItem("Brufen")))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(30)))
                .andExpect(jsonPath("$.[*].medicineId").value(hasItem(2)))
                .andExpect(jsonPath("$.[*].medicineName").value(hasItem("Paracetamol")))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(20)));

    }

    @Test
    @WithMockUser(authorities = {"SUPPLIER"})
    public void getOffersTest() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/offers/5")
                .param("type", ""))
                .andExpect(content().contentType(mediaType)).andExpect(jsonPath("$", hasSize(3)))
                        .andExpect(jsonPath("$.[*].id").value(hasItem(1)))
                        .andExpect(jsonPath("$.[*].offerState").value(hasItem("ACCEPTED")))
                        .andExpect(jsonPath("$.[*].orderId").value(hasItem(1)))
                        .andExpect(jsonPath("$.[*].id").value(hasItem(3)))
                        .andExpect(jsonPath("$.[*].offerState").value(hasItem("ACCEPTED")))
                        .andExpect(jsonPath("$.[*].orderId").value(hasItem(2)))
                        .andExpect(jsonPath("$.[*].id").value(hasItem(5)))
                        .andExpect(jsonPath("$.[*].offerState").value(hasItem("PENDING")))
                        .andExpect(jsonPath("$.[*].orderId").value(hasItem(7)));
    }

    @Test
    @WithMockUser(authorities = {"SUPPLIER"})
    public void getAcceptedOffersTest() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/offers/5")
                .param("type", "ACCEPTED"))
                .andExpect(content().contentType(mediaType)).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(1)))
                .andExpect(jsonPath("$.[*].offerState").value(hasItem("ACCEPTED")))
                .andExpect(jsonPath("$.[*].orderId").value(hasItem(1)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(3)))
                .andExpect(jsonPath("$.[*].offerState").value(hasItem("ACCEPTED")))
                .andExpect(jsonPath("$.[*].orderId").value(hasItem(2)));
    }

    @Test
    @WithMockUser(authorities = {"SUPPLIER"})
    public void getPendingOffersTest() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/offers/5")
                .param("type", "PENDING"))
                .andExpect(content().contentType(mediaType)).andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(5)))
                .andExpect(jsonPath("$.[*].offerState").value(hasItem("PENDING")))
                .andExpect(jsonPath("$.[*].orderId").value(hasItem(7)));
    }

    @Test
    @WithMockUser(authorities = {"SUPPLIER"})
    public void getDeniedOffersTest() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/offers/5")
                .param("type", "DENIED"))
                .andExpect(content().contentType(mediaType)).andExpect(jsonPath("$", hasSize(0)));
    }
}
