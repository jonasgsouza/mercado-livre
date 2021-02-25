package br.com.zup.mercadolivre.model;

import javax.persistence.*;

@Entity
@Table(name = "categorias")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Category parent;

    @Deprecated
    public Category() {
    }

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public Category getParent() {
        return parent;
    }
}
