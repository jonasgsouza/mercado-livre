package br.com.zup.mercadolivre.config.security;

import br.com.zup.mercadolivre.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Authentication auth) {
        var user = (User) auth.getPrincipal();
        var now = new Date();
        var expirationDate = new Date(now.getTime() + Long.parseLong(this.expiration));
        return Jwts.builder()
                .setIssuer("br.com.zup.mercadolivre")
                .setSubject(user.getId().toString())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, this.secret)
                .compact();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserId(String token) {
        var claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

}
