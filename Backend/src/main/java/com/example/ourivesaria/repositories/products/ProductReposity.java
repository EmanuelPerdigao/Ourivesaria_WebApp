package com.example.ourivesaria.repositories.products;


import com.example.ourivesaria.entities.products.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReposity extends JpaRepository<ProductEntity,Long> {

    /**
     * Gets a list of products by category id
     *
     * @param categoryId the category id
     * @return the list of products in the given category
     */
    List<ProductEntity> findByCategoryEntityId(Long categoryId);

    /**
     * Finds a list of products by category id and material id.
     *
     * @param categoryId The category id.
     * @param materialId The material id.
     * @return The list of products matching the given category and material.
     */
    List<ProductEntity> findByCategoryEntityIdAndMaterialEntityId(Long categoryId, Long materialId);

}
