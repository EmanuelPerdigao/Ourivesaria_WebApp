package com.example.ourivesaria.mappers.materials;

import com.example.ourivesaria.entities.materials.MaterialEntity;
import com.example.ourivesaria.dtos.materials.MaterialDto;
import com.example.ourivesaria.mappers.genericConverters.AbstractConverterImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MaterialMapper extends AbstractConverterImpl<MaterialEntity, MaterialDto> {

    @Override
    public MaterialEntity convertToEntity(MaterialDto materialDto) {

        MaterialEntity materialEntity = new MaterialEntity();

        if(materialDto.id() != null){
            materialEntity.setId(materialDto.id());
        }
        materialEntity.setMaterialName(materialDto.materialName());
        return materialEntity;

    }

    @Override
    public MaterialDto convertToDto(MaterialEntity entity) {
        return new MaterialDto(entity.getId(), entity.getMaterialName());
    }
}
