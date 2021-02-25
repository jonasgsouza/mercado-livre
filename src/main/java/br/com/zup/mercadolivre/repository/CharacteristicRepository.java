package br.com.zup.mercadolivre.repository;

import br.com.zup.mercadolivre.model.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {
}
