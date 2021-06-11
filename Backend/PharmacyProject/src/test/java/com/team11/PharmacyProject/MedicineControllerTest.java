package com.team11.PharmacyProject;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@SpringIntegrationTest
@WebAppConfiguration
public class MedicineControllerTest {

    private static final String URL_PREFIX = "/api/medicine";

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
    public void testGetAllMedicines() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/")).andExpect(status().isOk())
                .andExpect(content().contentType(mediaType)).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(1)))
                .andExpect(jsonPath("$.[*].code").value(hasItem("M01AE01")))
                .andExpect(jsonPath("$.[*].name").value(hasItem("Brufen")))
                .andExpect(jsonPath("$.[*].content").value(hasItem("Najjaci lek za glavu.")))
                .andExpect(jsonPath("$.[*].avgGrade").value(hasItem(4.2)));
    }

    @Test
    public void testGetMedicineById() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/" + 1)).andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.code").value("M01AE01"))
                .andExpect(jsonPath("$.name").value("Brufen"))
                .andExpect(jsonPath("$.content").value("Najjaci lek za glavu."))
                .andExpect(jsonPath("$.avgGrade").value(4.2))
                .andExpect(jsonPath("$.sideEffects").value("Glavobolja."))
                .andExpect(jsonPath("$.dailyIntake").value(2))
                .andExpect(jsonPath("$.recipeRequired").value("REQUIRED"))
                .andExpect(jsonPath("$.additionalNotes").value("Nema"))
                .andExpect(jsonPath("$.points").value(10))
                .andExpect(jsonPath("$.medicineType").value("Antibiotik"))
                .andExpect(jsonPath("$.medicineForm").value("Šumeće granule"))
                .andExpect(jsonPath("$.manufacturer").value("ABBVIE S.R.L. - Italija"));
    }

    @Test
    public void testGetCrudMedicines() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/crud")).andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.[*].code").value(hasItem("M02AE01")))
                .andExpect(jsonPath("$.[*].name").value(hasItem("Paracetamol")))
                .andExpect(jsonPath("$.[*].content").value(hasItem("Nema podataka.")))
                .andExpect(jsonPath("$.[*].sideEffects").value(hasItem("Moze doci do ozbiljnih problema.")))
                .andExpect(jsonPath("$.[*].dailyIntake").value(hasItem(2.0)))
                .andExpect(jsonPath("$.[*].recipeRequired").value(hasItem("REQUIRED")))
                .andExpect(jsonPath("$.[*].additionalNotes").value(hasItem("Nema")))
                .andExpect(jsonPath("$.[*].points").value(hasItem(20)));
    }

    /*@Test
    @Transactional
    @Rollback(true)
    public void testSaveStudent() throws Exception {
        MedicineCrudDTO dto = new MedicineCrudDTO();
        dto.setCode("M03AE01");
        dto.setContent("Najbrze deluje.");
        dto.setAdditionalNotes("Nema");
        dto.setDailyIntake(3);
        dto.setName("Fervex");
        dto.setPoints(20);
        dto.setRecipeRequired(RecipeRegime.REQUIRED);
        dto.setSideEffects("Nema");

        String json = TestUtil.json(dto);
        this.mockMvc.perform(post(URL_PREFIX).contentType(mediaType).content(json)).andExpect(status().isOk());
    }*/
}
