package com.team11.PharmacyProject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.integration.test.context.SpringIntegrationTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PharmacyProjectApplication.class)
@AutoConfigureMockMvc
@SpringIntegrationTest
@WebAppConfiguration
public class MyOrderControllerTest {

    private static final String URL_PREFIX = "/api/orders";

    private final MediaType mediaType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testGetAvailableOrders() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/")).andExpect(status().isOk())
                .andExpect(content().contentType(mediaType)).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(8)))
                .andExpect(jsonPath("$.[*].adminId").value(hasItem(9)))
                .andExpect(jsonPath("$.[*].deadline").value(hasItem(1624652935000L)))
                .andExpect(jsonPath("$.[*].orderState").value(hasItem("IN_PROGRESS")));
    }

    @Test
    public void testGetOrder() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/" + 1)).andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.adminId").value(9))
                .andExpect(jsonPath("$.deadline").value(1620817200000L))
                .andExpect(jsonPath("$.orderState").value("IN_PROGRESS"));
    }

    @Test
    public void testGetAvailableOrdersForSupplier() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/without-offers/" + 4)).andExpect(status().isOk())
                .andExpect(content().contentType(mediaType)).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(9)))
                .andExpect(jsonPath("$.[*].adminId").value(hasItem(9)))
                .andExpect(jsonPath("$.[*].deadline").value(hasItem(1624652935000L)))
                .andExpect(jsonPath("$.[*].orderState").value(hasItem("IN_PROGRESS")));
    }

}
