package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.exception.NotFoundException;
import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.validation.annotation.Exists;
import br.com.zup.mercadolivre.validation.annotation.Unique;

import javax.validation.constraints.NotBlank;

public class NewCategoryRequest {

    @NotBlank
    @Unique(field = "name", modelClass = Category.class)
    private String name;

    @Exists(field = "id", modelClass = Category.class)
    private Long parentId;

    public NewCategoryRequest(@NotBlank String name, Long parentId) {
        this.name = name;
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public Long getParentId() {
        return parentId;
    }

    public Category toModel(CategoryRepository categoryRepository) {
        Category category = null;
        if (parentId != null) {
            category = categoryRepository.findById(parentId).orElseThrow(() -> new NotFoundException(parentId));
        }
        return new Category(name, category);
    }
}
