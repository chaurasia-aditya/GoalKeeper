package com.chauri.GoalKeeper.service;

import com.chauri.GoalKeeper.dto.CategoryDTO;
import com.chauri.GoalKeeper.entity.Category;

import java.util.List;

public interface ICategoryService {
    CategoryDTO createCategory(CategoryDTO newCategoryDTO);

    CategoryDTO getCategory(Long categoryId);

    List<CategoryDTO> getAllCategories();

    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDtoDetails);

    void deleteCategory(Long categoryId);
}
