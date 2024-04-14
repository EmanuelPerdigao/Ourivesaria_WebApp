package com.example.ourivesaria.services.category;

import com.example.ourivesaria.entities.categories.CategoryEntity;
import com.example.ourivesaria.repositories.categories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService{

    final private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<CategoryEntity> getById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public CategoryEntity saveCategory(CategoryEntity CategoryEntity) {
        return categoryRepository.save(CategoryEntity);
    }

    @Override
    public CategoryEntity updateCategory(CategoryEntity CategoryEntity) {
        return categoryRepository.save(CategoryEntity);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryEntity> listAllCategorys() {
        return categoryRepository.findAll();
    }


}
