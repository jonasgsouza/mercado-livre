package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Characteristic;
import br.com.zup.mercadolivre.model.Product;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class NewCharacteristicRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String value;

    public NewCharacteristicRequest(@NotBlank String name, @NotBlank String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Characteristic toModel(Product product) {
        return new Characteristic(name, value, product);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewCharacteristicRequest that = (NewCharacteristicRequest) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
