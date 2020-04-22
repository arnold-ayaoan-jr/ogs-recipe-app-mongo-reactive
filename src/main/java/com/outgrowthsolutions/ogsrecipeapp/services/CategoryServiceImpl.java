package com.outgrowthsolutions.ogsrecipeapp.services;

import com.outgrowthsolutions.ogsrecipeapp.commands.CategoryCommand;
import com.outgrowthsolutions.ogsrecipeapp.converters.CategoryToCategoryCommand;
import com.outgrowthsolutions.ogsrecipeapp.repositories.reactive.CategoryReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryReactiveRepository categoryRepository;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    public CategoryServiceImpl(CategoryReactiveRepository categoryRepository, CategoryToCategoryCommand categoryToCategoryCommand) {
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Override
    public Flux<CategoryCommand> getAllCategory() {
        return categoryRepository.findAll()
                .map(categoryToCategoryCommand::convert);
    }

    @Override
    public Mono<CategoryCommand> getCategoryCommandById(String id) {
        //todo add error handling
        return categoryRepository.findById(id)
                .map(categoryToCategoryCommand::convert);
    }
}
