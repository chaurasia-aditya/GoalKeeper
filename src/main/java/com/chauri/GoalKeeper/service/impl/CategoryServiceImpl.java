package com.chauri.GoalKeeper.service.impl;

import com.chauri.GoalKeeper.dto.CategoryDTO;
import com.chauri.GoalKeeper.entity.Category;
import com.chauri.GoalKeeper.exception.ResourceExistsException;
import com.chauri.GoalKeeper.exception.ResourceNotFoundException;
import com.chauri.GoalKeeper.mapper.CategoryMapper;
import com.chauri.GoalKeeper.repository.CategoryRepository;
import com.chauri.GoalKeeper.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ICategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO newCategoryDTO) {
        Optional<Category> optionalCategory = categoryRepository.findByName(newCategoryDTO.getName());
        if(optionalCategory.isPresent()){
            throw new ResourceExistsException("Category", "categoryName", newCategoryDTO.getName());
        }
        Category newCategory = CategoryMapper.toEntity(newCategoryDTO);

        return CategoryMapper.toDTO(categoryRepository.save(newCategory));
    }

    @Override
    public CategoryDTO getCategory(Long categoryId) {
        return CategoryMapper.toDTO(categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "categoryId", categoryId.toString())));
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDtoDetails) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "categoryId", categoryId.toString()));
        category.setName(categoryDtoDetails.getName());
        category.setDescription(categoryDtoDetails.getDescription());
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "categoryId", categoryId.toString()));
        categoryRepository.deleteById(categoryId);
    }
}
