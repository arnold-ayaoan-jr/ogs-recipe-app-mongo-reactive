package com.outgrowthsolutions.ogsrecipeapp.services;

import com.outgrowthsolutions.ogsrecipeapp.commands.CategoryCommand;
import com.outgrowthsolutions.ogsrecipeapp.converters.CategoryToCategoryCommand;
import com.outgrowthsolutions.ogsrecipeapp.domain.Category;
import com.outgrowthsolutions.ogsrecipeapp.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    CategoryToCategoryCommand categoryToCategoryCommand;

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Test
    void getAllCategory() {
        //given
        Set<Category> mockCategories = new HashSet<>();
        mockCategories.add(new Category());
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId("1L");

        when(categoryRepository.findAll()).thenReturn(mockCategories);
        when(categoryToCategoryCommand.convert(any(Category.class))).thenReturn(categoryCommand);

        //when
        Set<CategoryCommand> categoryCommands = categoryService.getAllCategory();

        //then
        assertEquals(mockCategories.size(), categoryCommands.size());
        verify(categoryRepository).findAll();
        verify(categoryToCategoryCommand).convert(any());


    }

    @Test
    void getCategoryCommandById() {
        //given
        Category category = new Category();
        String id = "1L";
        category.setId(id);
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(id);

        when(categoryRepository.findById(anyString())).thenReturn(Optional.of(category));
        when(categoryToCategoryCommand.convert(any())).thenReturn(categoryCommand);

        //when
        CategoryCommand resultCommand = categoryService.getCategoryCommandById(id);

        //
        assertEquals(id, resultCommand.getId());
        verify(categoryRepository).findById(anyString());
        verify(categoryToCategoryCommand).convert(any(Category.class));
    }
}