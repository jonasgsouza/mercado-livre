package br.com.zup.mercadolivre.model;

import javax.persistence.*;

@Entity
@Table(name = "imagens_produtos")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String link;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Product product;

    @Deprecated
    public ProductImage() {
    }

    public ProductImage(String link, Product product) {
        this.link = link;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public Product getProduct() {
        return product;
    }
}
