package com.foood.product_svc.specification;

import com.foood.product_svc.entity.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product>getSpecification(String id, String restaurantId, String title, String category){
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(category != null && !category.isEmpty()){
                    predicates.add(criteriaBuilder.equal(root.get("category"), category));
                }
                if(id != null && !id.isEmpty()){
                    predicates.add(criteriaBuilder.equal(root.get("id"), id));
                }
                if(restaurantId != null && !restaurantId.isEmpty()){
                    predicates.add(criteriaBuilder.equal(root.get("restaurantId"), restaurantId));
            }
                if(title != null && !title.isEmpty()){
                    predicates.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };

    }

}
