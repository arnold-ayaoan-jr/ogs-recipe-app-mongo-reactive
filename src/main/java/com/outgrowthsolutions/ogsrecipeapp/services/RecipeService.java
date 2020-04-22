package com.outgrowthsolutions.ogsrecipeapp.services;

import com.outgrowthsolutions.ogsrecipeapp.commands.RecipeCommand;
import com.outgrowthsolutions.ogsrecipeapp.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {
    Flux<Recipe> getRecipes();

    Mono<Recipe> getRecipeById(String id);

    Mono<RecipeCommand> getRecipeCommandById(String id);

    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand);

    Mono<Void> deleteById(String id);
}
