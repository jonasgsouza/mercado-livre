package br.com.zup.mercadolivre.repository;

import br.com.zup.mercadolivre.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
