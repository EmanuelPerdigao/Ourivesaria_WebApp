package com.example.ourivesaria.repositories.products;

import com.example.ourivesaria.entities.categories.CategoryEntity;
import com.example.ourivesaria.entities.materials.MaterialEntity;
import com.example.ourivesaria.entities.products.ProductEntity;
import com.example.ourivesaria.enums.CategoryEnum;
import com.example.ourivesaria.enums.MaterialEnum;
import com.example.ourivesaria.repositories.categories.CategoryRepository;
import com.example.ourivesaria.repositories.materials.MaterialRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProductRepositoryTests {

    @Autowired
    private ProductReposity productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Test
    public void ProductRepository_SaveAll_ReturnSavedProducts(){


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

        //Act
        materialRepository.save(materialEntity);
        categoryRepository.save(categoryEntity);

        ProductEntity productSaved = productRepository.save(productEntity);

        //Assert
        Assertions.assertThat(productSaved).isNotNull();
        Assertions.assertThat(productSaved.getId()).isGreaterThan(0);

    }



    @Test
    public void ProductRepository_GetAll_ReturnMoreThenOneProduct(){


        // Arrange
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryName(CategoryEnum.ANEIS)
                .build();

        MaterialEntity materialEntity = MaterialEntity.builder()
                .materialName(MaterialEnum.OURO)
                .build();

        ProductEntity productEntity1 = ProductEntity.builder()
                .productName("name")
                .productDescription("description")
                .productImg("image_url")
                .categoryEntity(categoryEntity)
                .materialEntity(materialEntity)
                .build();

        ProductEntity productEntity2 = ProductEntity.builder()
                .productName("name")
                .productDescription("description")
                .productImg("image_url")
                .categoryEntity(categoryEntity)
                .materialEntity(materialEntity)
                .build();


        //Act
        materialRepository.save(materialEntity);
        categoryRepository.save(categoryEntity);

        productRepository.save(productEntity1);
        productRepository.save(productEntity2);

        List<ProductEntity> productEntityList = productRepository.findAll();

        //Assert
        Assertions.assertThat(productEntityList).isNotNull();
        Assertions.assertThat(productEntityList.size()).isEqualTo(2);

    }



    @Test
    public void ProductRepository_FindById_ReturnProduct(){


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

        //Act
        materialRepository.save(materialEntity);
        categoryRepository.save(categoryEntity);

        productRepository.save(productEntity);

        ProductEntity productEntityById = productRepository.findById(productEntity.getId()).get();

        //Assert
        Assertions.assertThat(productEntityById).isNotNull();

    }


    @Test
    public void ProductRepository_Update_ReturnProduct(){


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

        //Act
        materialRepository.save(materialEntity);
        categoryRepository.save(categoryEntity);

        productRepository.save(productEntity);

        ProductEntity productEntityById = productRepository.findById(productEntity.getId()).get();
        productEntityById.setProductName("othername");
        productEntityById.setProductImg("otherimg");

        ProductEntity updatedProductEntity = productRepository.save(productEntityById);

        //Assert
        Assertions.assertThat(updatedProductEntity.getProductName()).isNotNull();
        Assertions.assertThat(updatedProductEntity.getProductImg()).isNotNull();

    }


    @Test
    public void ProductRepository_Delete_ReturnEmpty(){


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

        //Act
        materialRepository.save(materialEntity);
        categoryRepository.save(categoryEntity);

        productRepository.save(productEntity);

        productRepository.deleteById(productEntity.getId());

        Optional<ProductEntity> productReturned = productRepository.findById(productEntity.getId());

        //Assert
        Assertions.assertThat(productReturned).isEmpty();

    }

    @Test
    public void ProductRepository_findByCategoryEntityId_ReturnProductList(){


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

        //Act
        materialRepository.save(materialEntity);
        categoryRepository.save(categoryEntity);

        productRepository.save(productEntity);

        List<ProductEntity> productReturned = productRepository.findByCategoryEntityId(categoryEntity.getId());

        //Assert
        Assertions.assertThat(productReturned).isNotNull();

    }

    @Test
    public void ProductRepository_findByCategoryEntityIdAndMaterialEntityId_ReturnProductList(){


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

        //Act
        materialRepository.save(materialEntity);
        categoryRepository.save(categoryEntity);

        productRepository.save(productEntity);

        List<ProductEntity> productReturned = productRepository.findByCategoryEntityIdAndMaterialEntityId(categoryEntity.getId(), materialEntity.getId());

        //Assert
        Assertions.assertThat(productReturned).isNotNull();

    }


}
