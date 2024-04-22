package com.example.ourivesaria.controllers.categories;


import com.example.ourivesaria.dtos.categories.CategoryDto;
import com.example.ourivesaria.mappers.categories.CategoryMapper;
import com.example.ourivesaria.services.category.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://ourivesaria.onrender.com", maxAge = 3600)
@RequestMapping("/api/products/category")
public class CategoryController {

    final private CategoryService categoryService;
    final private CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }


    //#############################  LIST ALL CATEGORIES AVAILABLE  ####################################
    @RequestMapping(method = RequestMethod.GET, path = {"/list"})
    public ResponseEntity<List<CategoryDto>> listCategories() {

        List<CategoryDto> categoryDtoList = categoryMapper.convertToDTOList(categoryService.listAllCategorys());

        return new ResponseEntity<>(categoryDtoList, HttpStatus.OK);
    }
}
