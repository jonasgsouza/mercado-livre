package br.com.zup.mercadolivre.config.security;

import br.com.zup.mercadolivre.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        var optional = userRepository.findByEmail(s);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new UsernameNotFoundException("Credenciais Inválidas");
    }
}
