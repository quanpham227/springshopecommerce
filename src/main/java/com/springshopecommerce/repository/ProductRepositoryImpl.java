package com.springshopecommerce.repository;

import com.springshopecommerce.dto.ProductDTO;
import com.springshopecommerce.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
@Repository

public class ProductRepositoryImpl implements ProductRepositoryCustom  {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<ProductDTO> filterProducts(List<Long> manufacturers, List<String> cpus, List<String> rams,List<String> colors, List<String> screenSizes, String name, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductDTO> criteriaQuery = criteriaBuilder.createQuery(ProductDTO.class);
        Root<ProductEntity> root = criteriaQuery.from(ProductEntity.class);

        criteriaQuery.select(criteriaBuilder.construct(
                ProductDTO.class,
                root.get("id"),
                root.get("name"),
                root.get("cpu"),
                root.get("ram"),
                root.get("color"),
                root.get("screenSize"),
                root.get("quantity"),
                root.get("price"),
                root.get("description"),
                root.get("discount"),
                root.get("status"),
                root.get("createDate"),
                root.get("updateDate"),
                root.get("image"),
                root.get("category").get("name"),
                root.get("manufacturer").get("name"),
                root.get("category").get("id"),
                root.get("manufacturer").get("id")
        ));

        List<Predicate> predicates = new ArrayList<>();

        if (manufacturers != null && !manufacturers.isEmpty()) {
            predicates.add(root.get("manufacturer").get("id").in(manufacturers));
        }

        if (cpus != null && !cpus.isEmpty()) {
            predicates.add(root.get("cpu").in(cpus));
        }

        if (rams != null && !rams.isEmpty()) {
            predicates.add(root.get("ram").in(rams));
        }
        if (colors != null && !colors.isEmpty()) {
            predicates.add(root.get("color").in(colors));
        }
        if (screenSizes != null && !screenSizes.isEmpty()) {
            predicates.add(root.get("screenSize").in(screenSizes));
        }

        if (name != null && !name.isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        if (pageable.getSort().isSorted()) {
            List<Order> orders = new ArrayList<>();
            pageable.getSort().forEach(order -> {
                if (order.isAscending()) {
                    orders.add(criteriaBuilder.asc(root.get(order.getProperty())));
                } else {
                    orders.add(criteriaBuilder.desc(root.get(order.getProperty())));
                }
            });
            criteriaQuery.orderBy(orders);
        }

        TypedQuery<ProductDTO> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        return query.getResultList();
    }

}
