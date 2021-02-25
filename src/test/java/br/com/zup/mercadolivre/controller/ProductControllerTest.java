package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.NewCharacteristicRequest;
import br.com.zup.mercadolivre.controller.request.NewProductRequest;
import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    private void setUp() {
        categoryRepository.save(new Category("Notebooks", null));
    }

    private ResultActions performRequest(NewProductRequest request) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
    }

    @Test
    @DisplayName("Deveria criar um novo produto")
    public void shouldCreateNewProduct() throws Exception {
        var request =
                new NewProductRequest(
                        "Notebook Dell Latitude 7400",
                        new BigDecimal("12000"),
                        20,
                        Arrays.asList(
                                new NewCharacteristicRequest("Processador", "i7 vPro"),
                                new NewCharacteristicRequest("Memória Ram", "16gb"),
                                new NewCharacteristicRequest("Armazenamento", "512gb")
                        ),
                        "Notebook Dell Latitude 7400",
                        1L
                );
        performRequest(request).andExpect(MockMvcResultMatchers.status().isOk());
        var optional = productRepository.findById(1L);
        Assertions.assertTrue(optional.isPresent());
        var product = optional.get();
        Assertions.assertEquals(request.getName(), product.getName());
        Assertions.assertEquals(request.getCharacteristics().size(), product.getCharacteristics().size());
        product.getCharacteristics().forEach(c -> Assertions.assertNotNull(c.getId()));
    }

    @Test
    @DisplayName("Deveria retonar status 400 quando não enviar nome e preço")
    public void shouldReturnBadRequestWithMissingNameAndPrice() throws Exception {
        var request =
                new NewProductRequest(
                        null,
                        null,
                        20,
                        Arrays.asList(
                                new NewCharacteristicRequest("Processador", "i7 vPro"),
                                new NewCharacteristicRequest("Memória Ram", "16gb"),
                                new NewCharacteristicRequest("Armazenamento", "512gb")
                        ),
                        "Notebook Dell Latitude 7400",
                        1L
                );
        performRequest(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals(0, productRepository.count());
    }

    @Test
    @DisplayName("Deveria retonar status 400 quando enviar menos que 3 características")
    public void shouldReturnBadRequestWithMissingCharacteristics() throws Exception {
        var request =
                new NewProductRequest(
                        "Notebook Dell Latitude 7400",
                        new BigDecimal("12000"),
                        20,
                        Collections.singletonList(
                                new NewCharacteristicRequest("Processador", "i7 vPro")
                        ),
                        "Notebook Dell Latitude 7400",
                        1L
                );
        performRequest(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals(0, productRepository.count());
    }

    @Test
    @DisplayName("Deveria retonar status 400 quando enviar características duplicadas")
    public void shouldReturnBadRequestWithDuplicatedCharacteristics() throws Exception {
        var request =
                new NewProductRequest(
                        "Notebook Dell Latitude 7400",
                        new BigDecimal("12000"),
                        20,
                        Arrays.asList(
                                new NewCharacteristicRequest("Processador", "i7 vPro"),
                                new NewCharacteristicRequest("Processador", "i7 vPro"),
                                new NewCharacteristicRequest("Memória Ram", "16gb")
                        ),
                        "Notebook Dell Latitude 7400",
                        1L
                );
        performRequest(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals(0, productRepository.count());
    }

}
