package br.com.zup.mercadolivre;

import br.com.zup.mercadolivre.controller.request.NewUserRequest;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class UserTests {

    private UserRepository userRepository;

    private Validator validator;

    @BeforeEach
    private void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Deveria criar um novo usuário")
    void shouldCreateNewUser() {
        var request = new NewUserRequest("usuario@email.com", "senhadousuario");
        validator.validate(request);
        var user = userRepository.save(request.toModel());
        Assertions.assertEquals(request.getEmail(), user.getEmail());
    }

    @Test
    @DisplayName("Deveria lançar uma exceção de validação ao salvar")
    void shouldThrowValidationErrorOnCreate() {
        var request = new NewUserRequest("usuario.com", "senha");
        validator.validate(request);
    }

}
