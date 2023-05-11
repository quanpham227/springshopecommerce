package com.springshopecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
public class ProductEntity extends AbstractEntity {
    @Column(name = "name", columnDefinition = "nvarchar(100) not null")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unitPrice")
    private Double price;


    @Column(length = 200)
    private String image;

    @Column(name = "description", columnDefinition = "nvarchar(500) not null")
    private String description;


    @Column(name = "discount")
    private Float discount;

    @Temporal(TemporalType.DATE)
    private Date entereDate;

    @Column(name = "status")
    private ProductStatus status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderDetailEntity> orderDetails = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private ManufacturerEntity manufacturer;

    @PreUpdate
    public void preUpdate() {

    }
}