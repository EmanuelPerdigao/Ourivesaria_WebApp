package com.example.ourivesaria.dtos.products;

import com.example.ourivesaria.entities.categories.CategoryEntity;
import com.example.ourivesaria.entities.materials.MaterialEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ProductDto(

        Long id,

        @NotEmpty(message = "A product must have a name")
        String productName,

        @NotEmpty(message = "A product must have a description")
        String productDescription,

        @NotEmpty(message = "A product must have an image")
        String productImg,

        @NotNull(message = "A product must have a category")
        CategoryEntity categoryEntity,

        @NotNull(message = "A product must have a material")
        MaterialEntity materialEntity

) {

}
