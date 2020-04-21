package com.outgrowthsolutions.ogsrecipeapp.services;

import com.outgrowthsolutions.ogsrecipeapp.commands.RecipeCommand;
import com.outgrowthsolutions.ogsrecipeapp.converters.RecipeCommandToRecipe;
import com.outgrowthsolutions.ogsrecipeapp.converters.RecipeToRecipeCommand;
import com.outgrowthsolutions.ogsrecipeapp.domain.Recipe;
import com.outgrowthsolutions.ogsrecipeapp.repositories.RecipeRepository;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@Disabled
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class RecipeServiceIT {
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;
    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Autowired
    RecipeService recipeService;

    @Transactional
    @Test
    void testSaveOfDescription() {
//      given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe foundRecipe = recipes.iterator().next();
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(foundRecipe);
//      when
        String description = "Test Description";
        recipeCommand.setDescription(description);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
//      then
        assertNotNull(savedRecipeCommand);
        assertEquals(description,savedRecipeCommand.getDescription());
        assertEquals(foundRecipe.getId(),savedRecipeCommand.getId());
        assertEquals(foundRecipe.getCategories().size(),savedRecipeCommand.getCategories().size());
        assertEquals(foundRecipe.getIngredients().size(),savedRecipeCommand.getIngredients().size());

    }
}
