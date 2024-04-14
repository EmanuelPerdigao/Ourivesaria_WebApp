package com.example.ourivesaria.services.products;

import com.example.ourivesaria.repositories.products.ProductReposity;
import com.example.ourivesaria.entities.products.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductReposity productReposity;

    public ProductServiceImpl(ProductReposity productReposity) {
        this.productReposity = productReposity;
    }

    @Override
    public Optional<ProductEntity> getById(Long id) {
        return productReposity.findById(id);
    }

    @Override
    public ProductEntity saveProduct(ProductEntity product) {
        return productReposity.save(product);
    }

    @Override
    public ProductEntity updateProduct(ProductEntity product) {
        return productReposity.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productReposity.deleteById(id);
    }

    @Override
    public List<ProductEntity> listAllProducts() {
        return productReposity.findAll();
    }

    @Override
    public List<ProductEntity> listAllProductsByCategoryId(Long categoryId) {
        return productReposity.findByCategoryEntityId(categoryId);
    }


    @Override
    public List<ProductEntity> listAllProductsByCategoryIdAndMaterialId(Long categoryId, Long materialId) {
        return productReposity.findByCategoryEntityIdAndMaterialEntityId(categoryId, materialId);
    }


}
