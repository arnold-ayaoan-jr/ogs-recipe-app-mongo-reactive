package com.outgrowthsolutions.ogsrecipeapp.services;

import com.outgrowthsolutions.ogsrecipeapp.commands.CategoryCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {
    Flux<CategoryCommand> getAllCategory();

    Mono<CategoryCommand> getCategoryCommandById(String id);
}
