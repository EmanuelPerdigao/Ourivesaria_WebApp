package com.example.ourivesaria.controllers.categories;
import com.example.ourivesaria.dtos.categories.CategoryDto;
import com.example.ourivesaria.entities.categories.CategoryEntity;
import com.example.ourivesaria.enums.CategoryEnum;
import com.example.ourivesaria.mappers.categories.CategoryMapper;
import com.example.ourivesaria.services.category.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(controllers = CategoryController.class)
// "disable" spring security filters to focus only in controller layer
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CategoryControllerTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CategoryMapper categoryMapper;



    @Test
    public void CategoryController_listAllCategory_ReturnsListOfCategories() throws Exception {

        // Arrange
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryName(CategoryEnum.ANEIS)
                .build();

        List<CategoryEntity> categoryEntityList = new ArrayList<>();
        categoryEntityList.add(categoryEntity);

        when(categoryService.listAllCategorys()).thenReturn(categoryEntityList);

        List<CategoryDto> categoryDtoList = new ArrayList<>();

        CategoryDto categoryDto = CategoryDto.builder().id(categoryEntity.getId()).categoryName(CategoryEnum.ANEIS).build();

        categoryDtoList.add(categoryDto);

        when(categoryMapper.convertToDTOList(categoryEntityList)).thenReturn(categoryDtoList);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/category/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(categoryEntityList.size()));
    }
}

