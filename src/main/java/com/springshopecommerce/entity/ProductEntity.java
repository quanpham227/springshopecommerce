package com.springshopecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private int quantity;

    @Column(name = "unitPrice")
    private BigDecimal price;

    @Column(name = "description", columnDefinition = "nvarchar(500) not null")
    private String description;


    @Column(name = "discount")
    private Float discount;

    @Column(name = "status")
    private ProductStatus status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<OrderDetailEntity> orderDetails = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_product_image",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "product_image_id"))
    private List<ProductImageEntity> images = new ArrayList<>();

    @OneToOne(orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_image_id")
    private ProductImageEntity image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id")
    private ManufacturerEntity manufacturer;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private Date createDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "update_date")
    private Date updateDate;

    @PreUpdate
    public void preUpdate() {
        updateDate = new Date();
    }

    @PrePersist
    public void prePersist() {
        createDate = new Date();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity product = (ProductEntity) o;
        return Objects.equals(getId(), product.getId()) &&
                Objects.equals(getName(), product.getName()) &&
                Objects.equals(getPrice(), product.getPrice()) &&
                Objects.equals(getDescription(), product.getDescription()) &&
                Objects.equals(getCategory(), product.getCategory()) &&
                Objects.equals(getManufacturer(), product.getManufacturer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice(), getDescription(), getCategory(), getManufacturer());
    }

    public ProductEntity(Long id, String name, int quantity, BigDecimal price, String description, Float discount, ProductStatus status,Date createDate, Date updateDate, ProductImageEntity image, CategoryEntity category,  ManufacturerEntity manufacturer) {
        super(id);
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.discount = discount;
        this.status = status;
        this.category = category;
        this.image = image;
        this.manufacturer = manufacturer;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}