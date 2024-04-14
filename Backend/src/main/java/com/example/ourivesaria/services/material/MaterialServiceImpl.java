package com.example.ourivesaria.services.material;

import com.example.ourivesaria.entities.materials.MaterialEntity;
import com.example.ourivesaria.repositories.materials.MaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialServiceImpl(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Override
    public Optional<MaterialEntity> getById(Long id) {
        return materialRepository.findById(id);
    }

    @Override
    public MaterialEntity saveMaterial(MaterialEntity MaterialEntity) {
        return materialRepository.save(MaterialEntity);
    }

    @Override
    public MaterialEntity updateMaterial(MaterialEntity MaterialEntity) {
        return materialRepository.save(MaterialEntity);
    }

    @Override
    public void deleteMaterial(Long id) {
        materialRepository.deleteById(id);
    }

    @Override
    public List<MaterialEntity> listAllMaterials() {
        return materialRepository.findAll();
    }

    @Override
    public List<MaterialEntity> listAllMaterialsByCategoryId(Long categoryId) {
        return materialRepository.findMaterialsByCategoryId(categoryId);
    }


}
