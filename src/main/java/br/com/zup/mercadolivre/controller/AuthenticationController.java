package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.config.security.TokenService;
import br.com.zup.mercadolivre.controller.response.AuthResponse;
import br.com.zup.mercadolivre.controller.request.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<AuthResponse> authenticate(@RequestBody @Valid LoginRequest request) {
        try {
            var authentication = authenticationManager.authenticate(request.build());
            var token = this.tokenService.generateToken(authentication);
            return ResponseEntity.ok(new AuthResponse(token, "Bearer"));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
