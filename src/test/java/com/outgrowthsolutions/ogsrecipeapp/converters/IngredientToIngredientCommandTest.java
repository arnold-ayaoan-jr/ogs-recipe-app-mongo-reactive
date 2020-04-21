package com.outgrowthsolutions.ogsrecipeapp.converters;

import com.outgrowthsolutions.ogsrecipeapp.commands.IngredientCommand;
import com.outgrowthsolutions.ogsrecipeapp.domain.Ingredient;
import com.outgrowthsolutions.ogsrecipeapp.domain.Recipe;
import com.outgrowthsolutions.ogsrecipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


class IngredientToIngredientCommandTest {

    IngredientToIngredientCommand converter;

    @BeforeEach
    void setUp() {
        converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmpty() {
        assertNotNull(converter.convert(new Ingredient()));
    }

    @Test
    void convert() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        String uomId = "1L";
        unitOfMeasure.setId(uomId);
        unitOfMeasure.setDescription("Cup");

        String recipeId = "2L";
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);

        String id = "1L";
        String description = "Rice";
        BigDecimal amount = new BigDecimal(1);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(id);
//        ingredient.setRecipe(recipe);
        ingredient.setDescription(description);
        ingredient.setAmount(amount);
        ingredient.setUnitOfMeasure(unitOfMeasure);

        IngredientCommand ingredientCommand =converter.convert(ingredient);
        assertNotNull(ingredientCommand);
        assertEquals(id, ingredientCommand.getId());
//        assertEquals(recipeId,ingredientCommand.getRecipeId());
        assertEquals(description,ingredientCommand.getDescription());
        assertEquals(amount,ingredientCommand.getAmount());
        assertEquals(uomId,ingredientCommand.getUnitOfMeasure().getId());
    }
}