package com.example.ourivesaria.repositories.categories;


import com.example.ourivesaria.entities.categories.CategoryEntity;
import com.example.ourivesaria.enums.CategoryEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;


    @Test
    public void CategoryRepository_SaveAll_ReturnSavedProducts(){


        // Arrange
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryName(CategoryEnum.ANEIS)
                .build();

        //Act
        CategoryEntity categorySaved = categoryRepository.save(categoryEntity);

        //Assert
        Assertions.assertThat(categorySaved).isNotNull();
        Assertions.assertThat(categorySaved.getId()).isGreaterThan(0);

    }

    @Test
    public void CategoryRepository_GetAll_ReturnMoreThenOneCategory(){


        // Arrange
        CategoryEntity categoryEntity1 = CategoryEntity.builder()
                .categoryName(CategoryEnum.ANEIS)
                .build();

        CategoryEntity categoryEntity2 = CategoryEntity.builder()
                .categoryName(CategoryEnum.BRINCOS)
                .build();

        categoryRepository.save(categoryEntity1);
        categoryRepository.save(categoryEntity2);

        //Act
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();

        //Assert
        Assertions.assertThat(categoryEntityList).isNotNull();
        Assertions.assertThat(categoryEntityList.size()).isEqualTo(2);

    }

    @Test
    public void CategoryRepository_FindById_ReturnCategory(){

        Long categoryId = 1L;

        // Arrange
        CategoryEntity categoryEntity1 = CategoryEntity.builder()
                .categoryName(CategoryEnum.ANEIS)
                .build();

        //Act
        categoryRepository.save(categoryEntity1);

        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).get();

        //Assert
        Assertions.assertThat(categoryEntity).isNotNull();

    }

    @Test
    public void CategoryRepository_Update_ReturnCategory(){

        // Arrange
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryName(CategoryEnum.ANEIS)
                .build();

        //Act
        categoryRepository.save(categoryEntity);

        CategoryEntity savedCategory = categoryRepository.findById(categoryEntity.getId()).get();
        savedCategory.setCategoryName(CategoryEnum.BRINCOS);

        CategoryEntity updatedCategory = categoryRepository.save(savedCategory);

        //Assert

        Assertions.assertThat(updatedCategory).isNotNull();
        Assertions.assertThat(updatedCategory.getCategoryName()).isEqualTo(CategoryEnum.BRINCOS);
    }


    @Test
    public void CategoryRepository_Delete_ReturnEmpty(){

        // Arrange
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryName(CategoryEnum.ANEIS)
                .build();

        //Act
        categoryRepository.save(categoryEntity);

        categoryRepository.deleteById(categoryEntity.getId());

        Optional<CategoryEntity> categoryReturned = categoryRepository.findById(categoryEntity.getId());

        //Assert
        Assertions.assertThat(categoryReturned).isEmpty();
    }
}
