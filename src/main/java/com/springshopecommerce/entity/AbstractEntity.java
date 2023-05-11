package com.springshopecommerce.entity;



import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
@MappedSuperclass // ddinhj nghia PARENT entity
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public AbstractEntity() {
    }

    public AbstractEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
