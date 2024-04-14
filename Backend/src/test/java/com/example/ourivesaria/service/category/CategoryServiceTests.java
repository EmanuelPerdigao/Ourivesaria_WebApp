package com.example.ourivesaria.service.category;

import com.example.ourivesaria.entities.categories.CategoryEntity;
import com.example.ourivesaria.enums.CategoryEnum;
import com.example.ourivesaria.repositories.categories.CategoryRepository;
import com.example.ourivesaria.services.category.CategoryServiceImpl;
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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;


    @Test
    public void CategoryService_SaveCategory_ReturnSavedCategory() {

        // Arrange
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryName(CategoryEnum.ANEIS)
                .build();
        Mockito.when(categoryRepository.save(Mockito.any(CategoryEntity.class))).thenReturn(categoryEntity);

        // Act
        CategoryEntity savedCategory = categoryService.saveCategory(categoryEntity);

        // Assert
        Assertions.assertThat(savedCategory).isNotNull();
    }

    @Test
    public void CategoryService_listAllCategories_ReturnCategoriesList() {

        // Arrange
        List<CategoryEntity> categoryEntityList = new ArrayList<>();

        categoryEntityList.add(CategoryEntity.builder().categoryName(CategoryEnum.ANEIS).build());

        when(categoryRepository.findAll()).thenReturn(categoryEntityList);

        // Act
        List<CategoryEntity> categoryListReturned = categoryService.listAllCategorys();

        // Assert
        Assertions.assertThat(categoryListReturned).isNotNull();
        Assertions.assertThat(categoryListReturned.size()).isEqualTo(categoryEntityList.size());
    }

    @Test
    public void CategoryService_findById_ReturnCategory() {
        // Arrange
        long categoryId = 1L;
        CategoryEntity categoryEntity = CategoryEntity.builder().id(categoryId).categoryName(CategoryEnum.ANEIS).build();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));

        // Act
        Optional<CategoryEntity> categoryReturned = categoryService.getById(categoryId);

        // Assert
        Assertions.assertThat(categoryReturned).isPresent();
        Assertions.assertThat(categoryReturned.get().getId()).isEqualTo(categoryId);
    }


    @Test
    public void CategoryService_UpdateCategory_ReturnUpdatedCategory() {

        // Arrange
        long categoryId = 1L;

        CategoryEntity categoryToUpdate = CategoryEntity.builder()
                .id(categoryId)
                .categoryName(CategoryEnum.ANEIS)
                .build();

        when(categoryRepository.save(Mockito.any(CategoryEntity.class))).thenReturn(categoryToUpdate);

        // Act
        CategoryEntity updatedCategory = categoryService.updateCategory(categoryToUpdate);

        // Assert
        Assertions.assertThat(updatedCategory).isEqualTo(categoryToUpdate);
    }


    @Test
    public void CategoryService_DeleteCategory_CategoryDeletedSuccessfully() {

        // Arrange
        long categoryIdToDelete = 1L;

        // Act
        categoryService.deleteCategory(categoryIdToDelete);

        // Assert
        Mockito.verify(categoryRepository).deleteById(categoryIdToDelete);
    }


}
