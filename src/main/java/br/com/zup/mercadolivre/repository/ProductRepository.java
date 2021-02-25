package br.com.zup.mercadolivre.repository;

import br.com.zup.mercadolivre.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
