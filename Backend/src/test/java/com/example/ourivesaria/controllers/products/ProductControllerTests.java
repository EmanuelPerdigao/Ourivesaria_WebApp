package com.example.ourivesaria.controllers.products;

import com.example.ourivesaria.entities.categories.CategoryEntity;
import com.example.ourivesaria.entities.materials.MaterialEntity;
import com.example.ourivesaria.entities.products.ProductEntity;
import com.example.ourivesaria.enums.CategoryEnum;
import com.example.ourivesaria.enums.MaterialEnum;
import com.example.ourivesaria.services.products.ProductService;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ProductController.class)
// "disable" spring security filters to focus only in controller layer
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;


    @Test
    public void ProductController_listAllProducts_ReturnsListOfProducts() throws Exception {

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

        when(productService.listAllProducts()).thenReturn(productEntityList);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/product/list")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(productEntityList.size()));
    }

    @Test
    public void ProductController_listProductsByCategory_ReturnsListOfProductsByCategoryId() throws Exception {

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

        when(productService.listAllProductsByCategoryId(categoryId)).thenReturn(productEntityList);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/product/listByCategory/" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(productEntityList.size()));
    }

    @Test
    public void ProductController_listProductsByCategoryAndMaterial_ReturnsListOfProductsByCategoryAndMaterial() throws Exception {

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

        Long categoryId = 1L; // Assuming category ID exists in the database
        Long materialId = 1L; // Assuming material ID exists in the database

        when(productService.listAllProductsByCategoryIdAndMaterialId(categoryId, materialId)).thenReturn(productEntityList);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/product/listByCategory/" + categoryId + "/material/" + materialId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(productEntityList.size()));
    }


}
