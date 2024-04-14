package com.example.ourivesaria.mappers.categories;

import com.example.ourivesaria.dtos.categories.CategoryDto;
import com.example.ourivesaria.entities.categories.CategoryEntity;
import com.example.ourivesaria.mappers.genericConverters.AbstractConverterImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper extends AbstractConverterImpl<CategoryEntity, CategoryDto> {

    @Override
    public CategoryEntity convertToEntity(CategoryDto categoryDto) {

        CategoryEntity categoryEntity = new CategoryEntity();

        if(categoryDto.id() != null){
            categoryEntity.setId(categoryDto.id());
        }

        categoryEntity.setCategoryName(categoryDto.categoryName());
        return  categoryEntity;

    }

    @Override
    public CategoryDto convertToDto(CategoryEntity entity) {
        return new CategoryDto(entity.getId(), entity.getCategoryName());
    }
}
