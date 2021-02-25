package br.com.zup.mercadolivre.repository;

import br.com.zup.mercadolivre.model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
}
