package com.outgrowthsolutions.ogsrecipeapp.services;

import com.outgrowthsolutions.ogsrecipeapp.commands.CategoryCommand;
import com.outgrowthsolutions.ogsrecipeapp.converters.CategoryToCategoryCommand;
import com.outgrowthsolutions.ogsrecipeapp.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryToCategoryCommand categoryToCategoryCommand) {
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Override
    public Set<CategoryCommand> getAllCategory() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .map(categoryToCategoryCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public CategoryCommand getCategoryCommandById(String id) {
        return categoryToCategoryCommand.convert(
                categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found")));
    }
}
