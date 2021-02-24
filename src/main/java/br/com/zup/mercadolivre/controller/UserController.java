package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.NewUserRequest;
import br.com.zup.mercadolivre.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid NewUserRequest request) {
        userRepository.save(request.toModel());
        return ResponseEntity.ok().build();
    }
}
