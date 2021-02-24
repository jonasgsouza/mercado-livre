package br.com.zup.mercadolivre.config.security;

import br.com.zup.mercadolivre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private TokenService tokenService;
//
//    @Autowired
//    private AutenticacaoService autenticacaoService;

    @Autowired
    private UserRepository userRepository;

    // Autenticação
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(this.autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
    }

    // Autorização
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().permitAll()
                .and().csrf().disable();
        http.headers().frameOptions().disable();
//        http.authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/topicos").permitAll()
//                .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
//                .antMatchers(HttpMethod.POST, "/auth").permitAll()
//                .antMatchers("/actuator/**").permitAll()
//                .antMatchers(HttpMethod.DELETE, "/topicos/*").hasRole("MODERADOR")
//                .anyRequest().authenticated()
//                .and().csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().addFilterBefore(new AutenticacaoViaTokenFilter(this.tokenService, this.userRepository), UsernamePasswordAuthenticationFilter.class);
    }

    // Recursos Estáticos
    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
