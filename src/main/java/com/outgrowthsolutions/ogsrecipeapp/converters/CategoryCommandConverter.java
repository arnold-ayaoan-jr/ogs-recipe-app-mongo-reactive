package com.outgrowthsolutions.ogsrecipeapp.converters;

import com.outgrowthsolutions.ogsrecipeapp.commands.CategoryCommand;
import com.outgrowthsolutions.ogsrecipeapp.services.CategoryService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandConverter implements Converter<String, CategoryCommand> {
    private final CategoryService categoryService;

    public CategoryCommandConverter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public CategoryCommand convert(String id) {
        return categoryService.getCategoryCommandById(id).block();
    }
}
