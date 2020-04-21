package com.outgrowthsolutions.ogsrecipeapp.converters;

import com.outgrowthsolutions.ogsrecipeapp.commands.RecipeCommand;
import com.outgrowthsolutions.ogsrecipeapp.domain.Category;
import com.outgrowthsolutions.ogsrecipeapp.domain.Difficulty;
import com.outgrowthsolutions.ogsrecipeapp.domain.Ingredient;
import com.outgrowthsolutions.ogsrecipeapp.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class RecipeToRecipeCommandTest {

    RecipeToRecipeCommand converter;

    @BeforeEach
    void setUp() {
        converter = new RecipeToRecipeCommand(new CategoryToCategoryCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()));
    }

    @Test
    void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmpty() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    void convert() {
        String id = "1L";
        String description = "Adobo";
        Category category = new Category();
        category.setId("1L");
        Category category1 = new Category();
        category1.setId("2L");
        Integer cookTime = 30;
        Integer prepTime = 15;
        String directions = "Wash the protein";
        String source = "FoodNatics";
        String url = "http://www.youtube.com";
        Difficulty difficulty = Difficulty.EASY;
        Ingredient ingredient = new Ingredient();
        ingredient.setId("1L");
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId("2L");


        Recipe recipe = new Recipe();
        recipe.setId(id);
        recipe.setDescription(description);
        recipe.setCookTime(cookTime);
        recipe.setPrepTime(prepTime);
        recipe.setDifficulty(difficulty);
        recipe.setDirections(directions);
        recipe.setSource(source);
        recipe.setUrl(url);
        recipe.getCategories().add(category);
        recipe.getCategories().add(category1);
        recipe.getIngredients().add(ingredient);
        recipe.getIngredients().add(ingredient1);

        RecipeCommand recipeCommand = converter.convert(recipe);

        assertNotNull(recipeCommand);
        assertEquals(id, recipeCommand.getId());
        assertEquals(description, recipeCommand.getDescription());
        assertEquals(cookTime, recipeCommand.getCookTime());
        assertEquals(prepTime, recipeCommand.getPrepTime());
        assertEquals(difficulty, recipeCommand.getDifficulty());
        assertEquals(directions, recipeCommand.getDirections());
        assertEquals(source, recipeCommand.getSource());
        assertEquals(url, recipeCommand.getUrl());
        assertEquals(2, recipeCommand.getCategories().size());
        assertEquals(2, recipeCommand.getIngredients().size());
    }
}