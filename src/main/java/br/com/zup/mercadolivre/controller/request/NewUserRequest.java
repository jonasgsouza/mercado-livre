package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.validation.annotation.Unique;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewUserRequest {

    @Email
    @NotBlank
    @Unique(field = "email", modelClass = User.class)
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    public NewUserRequest(@Email @NotBlank String email, @NotBlank @Size(min = 6) String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public User toModel() {
        return new User(email, password);
    }
}
