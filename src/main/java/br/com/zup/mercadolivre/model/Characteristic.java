package br.com.zup.mercadolivre.model;

import javax.persistence.*;

@Entity
@Table(name = "caracteristicas")
public class Characteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String name;

    @Column(name = "valor")
    private String value;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Product product;

    @Deprecated
    public Characteristic() {
    }

    public Characteristic(String name, String value, Product product) {
        this.name = name;
        this.value = value;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Product getProduct() {
        return product;
    }

}
