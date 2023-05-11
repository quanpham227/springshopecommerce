package com.springshopecommerce.entity;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "category")
public class CategoryEntity extends AbstractEntity {

    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<ProductEntity> products = new ArrayList<>();


    public CategoryEntity() {
    }

    public CategoryEntity(String name, List<ProductEntity> products) {
        this.name = name;
        this.products = products;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

}
