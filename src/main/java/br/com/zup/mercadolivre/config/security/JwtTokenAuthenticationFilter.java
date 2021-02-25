package br.com.zup.mercadolivre.config.security;

import br.com.zup.mercadolivre.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    public JwtTokenAuthenticationFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
        var token = retrieveToken(req);
        if (token != null && tokenService.isValid(token)) {
            var optional = userRepository.findById(tokenService.getUserId(token));
            if (optional.isPresent()) {
                var user = optional.get();
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(req, resp);
    }

    private String retrieveToken(HttpServletRequest req) {
        var header = req.getHeader("Authorization");
        if (header == null || header.isEmpty() || !header.startsWith("Bearer ")) {
            return null;
        }
        return header.substring(7, header.length());
    }
}
