package com.example.ourivesaria.service.products;

import com.example.ourivesaria.entities.categories.CategoryEntity;
import com.example.ourivesaria.entities.materials.MaterialEntity;
import com.example.ourivesaria.entities.products.ProductEntity;
import com.example.ourivesaria.enums.CategoryEnum;
import com.example.ourivesaria.enums.MaterialEnum;
import com.example.ourivesaria.repositories.products.ProductReposity;
import com.example.ourivesaria.services.products.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @Mock
    private ProductReposity productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void ProductService_SaveProduct_ReturnProduct(){

        // Arrange
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryName(CategoryEnum.ANEIS)
                .build();

        MaterialEntity materialEntity = MaterialEntity.builder()
                .materialName(MaterialEnum.OURO)
                .build();

        ProductEntity productEntity = ProductEntity.builder()
                .productName("name")
                .productDescription("description")
                .productImg("image_url")
                .categoryEntity(categoryEntity)
                .materialEntity(materialEntity)
                .build();

        when(productRepository.save(Mockito.any(ProductEntity.class))).thenReturn(productEntity);

        ProductEntity productSave = productService.saveProduct(productEntity);

        Assertions.assertThat(productSave).isNotNull();

    }


    @Test
    public void ProductService_listAllProducts_ReturnProductsList(){

        // Arrange
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryName(CategoryEnum.ANEIS)
                .build();

        MaterialEntity materialEntity = MaterialEntity.builder()
                .materialName(MaterialEnum.OURO)
                .build();

        ProductEntity productEntity = ProductEntity.builder()
                .productName("name")
                .productDescription("description")
                .productImg("image_url")
                .categoryEntity(categoryEntity)
                .materialEntity(materialEntity)
                .build();

        List<ProductEntity> productEntityList = new ArrayList<>();

        productEntityList.add(productEntity);

        when(productRepository.findAll()).thenReturn(productEntityList);

        // Act
        List<ProductEntity> productEntityListReturned = productService.listAllProducts();


        //Assert
        Assertions.assertThat(productEntityListReturned).isNotNull();
        Assertions.assertThat(productEntityListReturned.size()).isEqualTo(1);

    }



    @Test
    public void ProductService_findById_ReturnProduct(){

        // Arrange
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryName(CategoryEnum.ANEIS)
                .build();

        MaterialEntity materialEntity = MaterialEntity.builder()
                .materialName(MaterialEnum.OURO)
                .build();

        ProductEntity productEntity = ProductEntity.builder()
                .productName("name")
                .productDescription("description")
                .productImg("image_url")
                .categoryEntity(categoryEntity)
                .materialEntity(materialEntity)
                .build();


        when(productRepository.findById(productEntity.getId())).thenReturn(Optional.of(productEntity));

        // Act
        Optional<ProductEntity> productEntityReturned = productService.getById(productEntity.getId());

        //Assert
        Assertions.assertThat(productEntityReturned).isNotNull();

    }



    @Test
    public void ProductService_UpdateProduct_ReturnUpdatedProduct() {

        // Arrange
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryName(CategoryEnum.ANEIS)
                .build();

        MaterialEntity materialEntity = MaterialEntity.builder()
                .materialName(MaterialEnum.OURO)
                .build();

        ProductEntity productEntity = ProductEntity.builder()
                .productName("name")
                .productDescription("description")
                .productImg("image_url")
                .categoryEntity(categoryEntity)
                .materialEntity(materialEntity)
                .build();


        when(productRepository.save(Mockito.any(ProductEntity.class))).thenReturn(productEntity);

        // Act
        ProductEntity updatedProduct = productService.updateProduct(productEntity);

        // Assert
        Assertions.assertThat(updatedProduct).isEqualTo(productEntity);
    }

    @Test
    public void ProductService_DeleteProduct_ProductDeletedSuccessfully() {
        // Arrange
        Long productIdToDelete = 1L;

        // Act
        productService.deleteProduct(productIdToDelete);

        // Assert
        Mockito.verify(productRepository).deleteById(productIdToDelete);
    }

    @Test
    public void ProductService_ListAllProductsByCategoryId_ReturnProductsList() {

        // Arrange
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryName(CategoryEnum.ANEIS)
                .build();

        MaterialEntity materialEntity = MaterialEntity.builder()
                .materialName(MaterialEnum.OURO)
                .build();

        ProductEntity productEntity = ProductEntity.builder()
                .productName("name")
                .productDescription("description")
                .productImg("image_url")
                .categoryEntity(categoryEntity)
                .materialEntity(materialEntity)
                .build();

        List<ProductEntity> productEntityList = new ArrayList<>();

        productEntityList.add(productEntity);

        // Arrange
        Long categoryId = 1L;

        when(productRepository.findByCategoryEntityId(categoryId)).thenReturn(productEntityList);

        // Act
        List<ProductEntity> productListReturned = productService.listAllProductsByCategoryId(categoryId);

        // Assert
        Assertions.assertThat(productListReturned).isNotNull();
    }

    @Test
    public void ProductService_ListAllProductsByCategoryIdAndMaterialId_ReturnProductsList() {


        // Arrange
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryName(CategoryEnum.ANEIS)
                .build();

        MaterialEntity materialEntity = MaterialEntity.builder()
                .materialName(MaterialEnum.OURO)
                .build();

        ProductEntity productEntity = ProductEntity.builder()
                .productName("name")
                .productDescription("description")
                .productImg("image_url")
                .categoryEntity(categoryEntity)
                .materialEntity(materialEntity)
                .build();

        List<ProductEntity> productEntityList = new ArrayList<>();

        productEntityList.add(productEntity);

        Long categoryId = 1L;
        Long materialId = 1L;


        when(productRepository.findByCategoryEntityIdAndMaterialEntityId(categoryId, materialId)).thenReturn(productEntityList);

        // Act
        List<ProductEntity> productListReturned = productService.listAllProductsByCategoryIdAndMaterialId(categoryId, materialId);

        // Assert
        Assertions.assertThat(productListReturned).isNotNull();

    }



}
