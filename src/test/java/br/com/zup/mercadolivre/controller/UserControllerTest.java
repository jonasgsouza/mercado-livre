package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.NewUserRequest;
import br.com.zup.mercadolivre.repository.UserRepository;
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
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Deveria cadastrar um novo usuário")
    public void shouldCreateNewUser() throws Exception {
        var request = new NewUserRequest("pessoa@email.com", "senhadapessoa");
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(MockMvcResultMatchers.status().isOk());
        var optional = userRepository.findById(1L);
        Assertions.assertTrue(optional.isPresent());
        var user = optional.get();
        Assertions.assertEquals(request.getEmail(), user.getEmail());
    }

    @Test
    @DisplayName("Deveria retornar status 400 com email inválido")
    public void shouldReturnBadRequestWithWrongEmail() throws Exception {
        var request = new NewUserRequest("pessoaemail.com", "senhadapessoa");
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Deveria retornar status 400 com senha menor que 6 caracteres")
    public void shouldReturnBadRequestWithShortPassword() throws Exception {
        var request = new NewUserRequest("pessoa@email.com", "senha");
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
