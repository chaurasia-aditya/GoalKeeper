package com.chauri.GoalKeeper.mapper;

import com.chauri.GoalKeeper.dto.CategoryDTO;
import com.chauri.GoalKeeper.entity.Category;

public class CategoryMapper {

    public static CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }

        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());

        return dto;
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            return null;
        }

        Category dao = new Category();
        dao.setId(categoryDTO.getId());
        dao.setName(categoryDTO.getName());
        dao.setDescription(categoryDTO.getDescription());

        return dao;
    }
}
