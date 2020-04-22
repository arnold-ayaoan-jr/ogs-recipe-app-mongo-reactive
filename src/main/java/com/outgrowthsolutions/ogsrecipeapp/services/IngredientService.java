package com.outgrowthsolutions.ogsrecipeapp.services;

import com.outgrowthsolutions.ogsrecipeapp.commands.IngredientCommand;
import reactor.core.publisher.Mono;

public interface IngredientService {
    Mono<IngredientCommand> getIngredientCommandByIdAndRecipeId(String ingredientId, String recipeId);

    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand);

    Mono<Void> deleteIngredient(String recipeId, String ingredientId);
}
