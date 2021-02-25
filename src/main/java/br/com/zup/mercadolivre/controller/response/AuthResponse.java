package br.com.zup.mercadolivre.controller.response;

public class AuthResponse {
    private String token;
    private String bearer;

    public AuthResponse(String token, String bearer) {
        this.token = token;
        this.bearer = bearer;
    }

    public String getToken() {
        return token;
    }

    public String getBearer() {
        return bearer;
    }
}
