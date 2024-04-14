package com.example.ourivesaria.dtos.categories;

import com.example.ourivesaria.enums.CategoryEnum;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record CategoryDto(

        Long id,

        @NotEmpty(message = "A category must have a category name")
        CategoryEnum categoryName
) {


}
