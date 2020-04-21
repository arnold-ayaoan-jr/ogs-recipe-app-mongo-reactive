package com.outgrowthsolutions.ogsrecipeapp.services;

import com.outgrowthsolutions.ogsrecipeapp.commands.RecipeCommand;
import com.outgrowthsolutions.ogsrecipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe getRecipeById(String id);

    RecipeCommand getRecipeCommandById(String id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    void deleteById(String id);
}
