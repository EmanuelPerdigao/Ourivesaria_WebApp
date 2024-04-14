package com.example.ourivesaria.dtos.materials;

import com.example.ourivesaria.enums.MaterialEnum;
import jakarta.validation.constraints.NotEmpty;

public record MaterialDto(

        Long id,
        @NotEmpty(message = "A material must have a material name")
        MaterialEnum materialName
) {
}
