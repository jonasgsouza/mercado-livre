package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.NewCategoryRequest;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Deveria criar uma nova categoria")
    public void shouldCreateNewCategory() throws Exception {
        var request = new NewCategoryRequest("Celulares", null);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(MockMvcResultMatchers.status().isOk());
        var optional = categoryRepository.findById(1L);
        Assertions.assertTrue(optional.isPresent());
        var category = optional.get();
        Assertions.assertEquals(request.getName(), category.getName());
    }

    @Test
    @DisplayName("Deveria retornar status 400 se o nome está nulo")
    public void shouldReturnBadRequestWithBlankName() throws Exception {
        var request = new NewCategoryRequest(null, null);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
