package com.springshopecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "manufacturer")
public class ManufacturerEntity extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "logo")
    private String logo;

    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL)
    private List<ProductEntity> products = new ArrayList<>();
    public ManufacturerEntity(){

    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public ManufacturerEntity(String name, String logo, List<ProductEntity> products) {
        this.name = name;
        this.logo = logo;
        this.products = products;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

}
