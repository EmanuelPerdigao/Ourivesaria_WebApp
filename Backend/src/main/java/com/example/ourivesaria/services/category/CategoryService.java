package com.example.ourivesaria.services.category;

import com.example.ourivesaria.entities.categories.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    /**
     * Gets the CategoryEntity with the given id
     *
     * @param id the CategoryEntity id
     * @return the CategoryEntity
     */
    Optional<CategoryEntity> getById(Long id);


    /**
     * Saves a CategoryEntity
     *
     * @param CategoryEntity the CategoryEntity to save
     * @return the saved CategoryEntity
     */
    CategoryEntity saveCategory(CategoryEntity CategoryEntity);




    /**
     * Update a CategoryEntity
     *
     * @param CategoryEntity the CategoryEntity to update
     * @return the updated CategoryEntity
     */
    CategoryEntity updateCategory(CategoryEntity CategoryEntity);




    /**
     * Deletes a CategoryEntity
     *
     * @param id the CategoryEntity id
     */
    void deleteCategory(Long id);



    /**
     * Gets a list of the Categorys
     *
     * @return the CategoryEntity list
     */
    List<CategoryEntity> listAllCategorys();

}
