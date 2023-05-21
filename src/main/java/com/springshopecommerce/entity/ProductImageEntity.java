package com.springshopecommerce.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_image")
public class ProductImageEntity extends AbstractEntity {
    @Column(name = "publicid")
    private String publicId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "url")
    private String url;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "images")
    private List<ProductEntity> products = new ArrayList<>();



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof ProductImageEntity)) return false;
        ProductImageEntity that = (ProductImageEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
