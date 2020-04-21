package com.outgrowthsolutions.ogsrecipeapp.converters;

import com.outgrowthsolutions.ogsrecipeapp.commands.IngredientCommand;
import com.outgrowthsolutions.ogsrecipeapp.commands.UnitOfMeasureCommand;
import com.outgrowthsolutions.ogsrecipeapp.domain.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {

    IngredientCommandToIngredient converter;

    @BeforeEach
    void setUp() {
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmpty() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    void convert() {
        String uomId = "1L";
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(uomId);
        unitOfMeasureCommand.setDescription("Cup");

        String id = "1L";
        String recipeId = "2L";
        String description = "Rice";
        BigDecimal amount = new BigDecimal(1);

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(id);
        ingredientCommand.setRecipeId(recipeId);
        ingredientCommand.setDescription(description);
        ingredientCommand.setAmount(amount);
        ingredientCommand.setUnitOfMeasure(unitOfMeasureCommand);

        Ingredient converted =converter.convert(ingredientCommand);
        assertNotNull(converted);
        assertEquals(id, converted.getId());
//        assertEquals(recipeId,converted.getRecipe().getId());
        assertEquals(description,converted.getDescription());
        assertEquals(amount,converted.getAmount());
        assertEquals(uomId,converted.getUnitOfMeasure().getId());
    }
}