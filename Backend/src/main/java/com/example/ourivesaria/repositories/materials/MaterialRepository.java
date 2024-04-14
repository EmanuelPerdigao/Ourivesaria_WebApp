package com.example.ourivesaria.repositories.materials;

import com.example.ourivesaria.entities.materials.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MaterialRepository extends JpaRepository<MaterialEntity, Long> {

  /*  *//**
     * Gets a list of materials by category name
     *
     * @param categoryName the category name
     * @return the list of products in the given category
     *//*
    List<MaterialEntity> findByCategoryEntityList_CategoryName(CategoryEnum categoryName);


    *//**
     * Gets a list of materials by category id
     *
     * @param categoryId the category id
     * @return the list of materials in the given category
     *//*
    List<MaterialEntity> findByCategoryEntityList_Id(Long categoryId);*/

    @Query("SELECT p.materialEntity FROM ProductEntity p WHERE p.categoryEntity.id = :categoryId")
    List<MaterialEntity> findMaterialsByCategoryId(@Param("categoryId") Long categoryId);



}
