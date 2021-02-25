package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.exception.NotFoundException;
import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.validation.annotation.Exists;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NewProductRequest {

    @NotBlank
    private String name;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal price;

    private Integer quantity;

    @NotNull
    @Size(min = 3)
    private List<NewCharacteristicRequest> characteristics = new ArrayList<>();

    @NotBlank
    @Size(max = 1000)
    private String description;

    @NotNull
    @Exists(field = "id", modelClass = Category.class)
    private Long categoryId;

    public NewProductRequest(@NotBlank String name, @NotNull @DecimalMin("0.00") BigDecimal price, Integer quantity, @NotNull @Size(min = 3) List<NewCharacteristicRequest> characteristics, @NotBlank @Size(max = 1000) String description, @NotNull Long categoryId) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.characteristics.addAll(characteristics);
        this.description = description;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<NewCharacteristicRequest> getCharacteristics() {
        return characteristics;
    }

    public String getDescription() {
        return description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Product toModel(CategoryRepository categoryRepository, User owner) {
        var category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException(categoryId));
        var product = new Product(name, price, quantity, description, category, owner);
        this.characteristics.forEach(characteristicRequest -> product.addCharacteristic(characteristicRequest.toModel(product)));
        return product;
    }
}
