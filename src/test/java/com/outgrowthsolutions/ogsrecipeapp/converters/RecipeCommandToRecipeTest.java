package com.outgrowthsolutions.ogsrecipeapp.converters;

import com.outgrowthsolutions.ogsrecipeapp.commands.CategoryCommand;
import com.outgrowthsolutions.ogsrecipeapp.commands.IngredientCommand;
import com.outgrowthsolutions.ogsrecipeapp.commands.RecipeCommand;
import com.outgrowthsolutions.ogsrecipeapp.domain.Difficulty;
import com.outgrowthsolutions.ogsrecipeapp.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCommandToRecipeTest {
    RecipeCommandToRecipe converter;

    @BeforeEach
    void setUp() {
        converter = new RecipeCommandToRecipe(new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()));
    }

    @Test
    void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmpty() {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    void convert() {
        String id = "1L";
        String description = "Adobo";
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId("1L");
        CategoryCommand categoryCommand1 = new CategoryCommand();
        categoryCommand1.setId("2L");
        Integer cookTime = 30;
        Integer prepTime = 15;
        String directions = "Wash the protein";
        String source = "FoodNatics";
        String url = "http://www.youtube.com";
        Difficulty difficulty = Difficulty.EASY;
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId("1L");
        IngredientCommand ingredientCommand1 = new IngredientCommand();
        ingredientCommand1.setId("2L");


        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(id);
        recipeCommand.setDescription(description);
        recipeCommand.setCookTime(cookTime);
        recipeCommand.setPrepTime(prepTime);
        recipeCommand.setDifficulty(difficulty);
        recipeCommand.setDirections(directions);
        recipeCommand.setSource(source);
        recipeCommand.setUrl(url);
        recipeCommand.getCategories().add(categoryCommand);
        recipeCommand.getCategories().add(categoryCommand1);
        recipeCommand.getIngredients().add(ingredientCommand);
        recipeCommand.getIngredients().add(ingredientCommand1);

        Recipe recipe = converter.convert(recipeCommand);

        assertNotNull(recipe);
        assertEquals(id, recipe.getId());
        assertEquals(description, recipe.getDescription());
        assertEquals(cookTime, recipe.getCookTime());
        assertEquals(prepTime, recipe.getPrepTime());
        assertEquals(difficulty, recipe.getDifficulty());
        assertEquals(directions, recipe.getDirections());
        assertEquals(source, recipe.getSource());
        assertEquals(url, recipe.getUrl());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
    }
}