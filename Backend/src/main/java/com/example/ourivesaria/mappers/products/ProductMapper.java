package com.example.ourivesaria.mappers.products;

import com.example.ourivesaria.dtos.products.ProductDto;
import com.example.ourivesaria.entities.products.ProductEntity;
import com.example.ourivesaria.mappers.genericConverters.AbstractConverterImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper extends AbstractConverterImpl<ProductEntity, ProductDto> {

    @Override
    public ProductEntity convertToEntity(ProductDto productDto) {

        ProductEntity productEntity = new ProductEntity();

        if(productDto.id() != null){
            productEntity.setId(productDto.id());
        }

        productEntity.setProductName(productDto.productName());
        productEntity.setProductDescription(productDto.productDescription());
        productEntity.setProductImg(productDto.productImg());
        productEntity.setCategoryEntity(productDto.categoryEntity());
        productEntity.setMaterialEntity(productDto.materialEntity());

        return productEntity;
    }

    @Override
    public ProductDto convertToDto(ProductEntity productEntity) {

        return new ProductDto(productEntity.getId(),
                productEntity.getProductName(),
                productEntity.getProductDescription(),
                productEntity.getProductImg(),
                productEntity.getCategoryEntity(),
                productEntity.getMaterialEntity()
        );
    }



}
