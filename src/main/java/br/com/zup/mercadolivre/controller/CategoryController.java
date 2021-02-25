package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.NewCategoryRequest;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> create(@RequestBody @Valid NewCategoryRequest request) {
        categoryRepository.save(request.toModel(categoryRepository));
        return ResponseEntity.ok().build();
    }
}
