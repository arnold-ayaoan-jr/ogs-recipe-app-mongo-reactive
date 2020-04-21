package com.outgrowthsolutions.ogsrecipeapp.services;

import com.outgrowthsolutions.ogsrecipeapp.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand getIngredientCommandByIdAndRecipeId(String ingredientId, String recipeId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteIngredient(String recipeId, String ingredientId);
}
