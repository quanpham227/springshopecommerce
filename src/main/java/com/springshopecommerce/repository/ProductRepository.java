package com.springshopecommerce.repository;

import com.springshopecommerce.dto.ProductDTO;
import com.springshopecommerce.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query("SELECT new com.springshopecommerce.dto.ProductDTO(p.id, p.name, p.quantity, p.price, p.description, p.discount, p.status, p.createDate, p.updateDate, pi, c.name, m.name, c.id, m.id) " +
            "FROM ProductEntity p " +
            "JOIN p.image pi " +
            "JOIN p.category c " +
            "JOIN p.manufacturer m " +
            "WHERE LOWER(p.name) LIKE LOWER(concat('%', :name, '%'))")
    Page<ProductDTO> findByNameContainsIgnoreCase(@Param("name") String name, Pageable pageable);

    @Query("SELECT new com.springshopecommerce.dto.ProductDTO(p.id, p.name, p.quantity, p.price, p.description, p.discount, p.status, p.createDate, p.updateDate, pi, c.name, m.name, c.id, m.id) " +
        "FROM ProductEntity p " +
        "JOIN p.image pi " +
        "JOIN p.category c " +
        "JOIN p.manufacturer m")
    Page<ProductDTO> findAllPaginged(Pageable pageable);


    @Query("SELECT new com.springshopecommerce.dto.ProductDTO (p.id, p.name, p.quantity, p.price, p.description, p.discount, p.status, p.createDate, p.updateDate, pi, c.name,  m.name, c.id, m.id)" +
            "FROM ProductEntity p " +
            "LEFT JOIN p.image pi " +
            "LEFT JOIN p.category c " +
            "LEFT JOIN p.manufacturer m " +
            "WHERE p.id = :productId")
    Optional<ProductDTO> findByProductId(@Param("productId") Long id);

    @Query("SELECT new com.springshopecommerce.entity.ProductEntity (p.id, p.name, p.quantity, p.price, p.description, p.discount, p.status, p.createDate, p.updateDate, pi, c,  m)" +
            "FROM ProductEntity p " +
            "LEFT JOIN p.image pi " +
            "LEFT JOIN p.category c " +
            "LEFT JOIN p.manufacturer m " +
            "WHERE p.id = :productId")
    Optional<ProductEntity> findProductEntitiesById(@Param("productId") Long id);

    @Modifying
    @Query(value = "delete from ProductEntity p where p.id = ?1")
    void deleteProductById(Long id);
}
