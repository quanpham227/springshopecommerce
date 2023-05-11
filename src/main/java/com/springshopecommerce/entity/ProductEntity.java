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
    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Double price;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "is_featured")
    private Boolean isFeatured;

    @Column(name = "description", length = 2000)
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "manufacture_date")
    private Date manufactureDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private ManufacturerEntity manufacturer;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImageEntity> products ;

    @Column(name = "status")
    private ProductStatus status;

    @PrePersist
    public void prePersist() {
        if(isFeatured == null) isFeatured = false;
        viewCount = 0L;
    }

    @PreUpdate
    public void preUpdate() {
        if (this.quantity < 0) {
            throw new IllegalArgumentException("Quantity must be greater than or equal to 0.");
        }
        if (this.price < 0) {
            throw new IllegalArgumentException("Price must be greater than or equal to 0.");
        }
        if (this.discount < 0 || this.discount > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100.");
        }
        if (this.viewCount < 0) {
            throw new IllegalArgumentException("View count must be greater than or equal to 0.");
        }
        if (this.status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }
    }
}