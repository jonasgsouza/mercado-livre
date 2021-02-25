package br.com.zup.mercadolivre.controller.response;

import br.com.zup.mercadolivre.model.Product;

public class CreatedProductResponse {

    private Long id;
    private String name;

    public CreatedProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
