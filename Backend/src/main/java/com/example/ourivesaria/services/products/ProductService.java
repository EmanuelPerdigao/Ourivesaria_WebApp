package com.example.ourivesaria.services.products;

import com.example.ourivesaria.entities.products.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    /**
     * Gets the Product with the given id
     *
     * @param id the Product id
     * @return the Product
     */
    Optional<ProductEntity> getById(Long id);



    /**
     * Saves a Product
     *
     * @param product the Product to save
     * @return the saved Product
     */
    ProductEntity saveProduct(ProductEntity product);




    /**
     * Update a Product
     *
     * @param product the Product to update
     * @return the updated Product
     */
    ProductEntity updateProduct(ProductEntity product);




    /**
     * Deletes a Product
     *
     * @param id the Product id
     */
    void deleteProduct(Long id);




    /**
     * Gets a list of the products
     *
     * @return the product list
     */
    List<ProductEntity> listAllProducts();



    /**
     * Gets a list of products by category id
     *
     * @param categoryId the category id
     * @return the list of products in the given category
     */
    List<ProductEntity> listAllProductsByCategoryId(Long categoryId);
    ;

    List<ProductEntity> listAllProductsByCategoryIdAndMaterialId(Long categoryId, Long materialId);

}
