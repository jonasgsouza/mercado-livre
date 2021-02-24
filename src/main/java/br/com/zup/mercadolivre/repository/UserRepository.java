package br.com.zup.mercadolivre.repository;

import br.com.zup.mercadolivre.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
