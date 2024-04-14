package com.example.ourivesaria.services.material;

import com.example.ourivesaria.entities.materials.MaterialEntity;

import java.util.List;
import java.util.Optional;

public interface MaterialService {

    /**
     * Gets the MaterialEntity with the given id
     *
     * @param id the MaterialEntity id
     * @return the MaterialEntity
     */
    Optional<MaterialEntity> getById(Long id);


    /**
     * Saves a MaterialEntity
     *
     * @param MaterialEntity the MaterialEntity to save
     * @return the saved MaterialEntity
     */
    MaterialEntity saveMaterial(MaterialEntity MaterialEntity);




    /**
     * Update a MaterialEntity
     *
     * @param MaterialEntity the MaterialEntity to update
     * @return the updated MaterialEntity
     */
    MaterialEntity updateMaterial(MaterialEntity MaterialEntity);



    /**
     * Deletes a MaterialEntity
     *
     * @param id the MaterialEntity id
     */
    void deleteMaterial(Long id);


    /**
     * Gets a list of the Materials
     *
     * @return the MaterialEntity list
     */
    List<MaterialEntity> listAllMaterials();

    List<MaterialEntity> listAllMaterialsByCategoryId(Long categoryId);


}
