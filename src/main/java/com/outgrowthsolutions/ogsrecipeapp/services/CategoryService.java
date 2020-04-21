package com.outgrowthsolutions.ogsrecipeapp.services;

import com.outgrowthsolutions.ogsrecipeapp.commands.CategoryCommand;

import java.util.Set;

public interface CategoryService {
    Set<CategoryCommand> getAllCategory();

    CategoryCommand getCategoryCommandById(String id);
}
