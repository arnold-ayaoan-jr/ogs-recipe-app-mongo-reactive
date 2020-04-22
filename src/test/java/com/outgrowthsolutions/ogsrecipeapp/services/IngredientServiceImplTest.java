package com.outgrowthsolutions.ogsrecipeapp.services;

import com.outgrowthsolutions.ogsrecipeapp.commands.IngredientCommand;
import com.outgrowthsolutions.ogsrecipeapp.commands.UnitOfMeasureCommand;
import com.outgrowthsolutions.ogsrecipeapp.converters.IngredientCommandToIngredient;
import com.outgrowthsolutions.ogsrecipeapp.converters.IngredientToIngredientCommand;
import com.outgrowthsolutions.ogsrecipeapp.domain.Ingredient;
import com.outgrowthsolutions.ogsrecipeapp.domain.Recipe;
import com.outgrowthsolutions.ogsrecipeapp.domain.UnitOfMeasure;
import com.outgrowthsolutions.ogsrecipeapp.repositories.RecipeRepository;
import com.outgrowthsolutions.ogsrecipeapp.repositories.UnitOfMeasureRepository;
import com.outgrowthsolutions.ogsrecipeapp.repositories.reactive.RecipeReactiveRepository;
import com.outgrowthsolutions.ogsrecipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    RecipeReactiveRepository recipeRepository;
    @Mock
    IngredientCommandToIngredient ingredientCommandToIngredient;
    @Mock
    IngredientToIngredientCommand ingredientToIngredientCommand;
    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureRepository;

    @InjectMocks
    IngredientServiceImpl ingredientService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getIngredientCommandByIdAndRecipeId() {
        //given
        String recipeId = "1L";
        Recipe mockRecipe = new Recipe();
        mockRecipe.setId(recipeId);

        String onionIngredientId = "2L";
        Ingredient onionIngredient = new Ingredient();
        onionIngredient.setId(onionIngredientId);
        Ingredient saltIngredient = new Ingredient();
        saltIngredient.setId("3L");
        Ingredient garlicIngredient = new Ingredient();
        garlicIngredient.setId("4L");

        mockRecipe.getIngredients().add(onionIngredient);
        mockRecipe.getIngredients().add(garlicIngredient);
        mockRecipe.getIngredients().add(saltIngredient);

        IngredientCommand mockIngredientCommand = new IngredientCommand();
        mockIngredientCommand.setId(onionIngredientId);
        mockIngredientCommand.setRecipeId(recipeId);

        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(mockRecipe));
        when(ingredientToIngredientCommand.convert(any())).thenReturn(mockIngredientCommand );

        //when
        IngredientCommand ingredientCommand = ingredientService.getIngredientCommandByIdAndRecipeId(onionIngredientId,recipeId).block();

        //then
        assertEquals(onionIngredientId,ingredientCommand.getId());
        assertEquals(recipeId,ingredientCommand.getRecipeId());
        verify(recipeRepository).findById(anyString());
    }

    @Test
    void saveIngredientCommand() {
        //given
        String recipeId = "1L";
        String ingredientId = "2L";
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId("1L");

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        ingredientCommand.setId(ingredientId);
        ingredientCommand.setUnitOfMeasure(unitOfMeasureCommand);

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId("1L");
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        ingredient.setUnitOfMeasure(unitOfMeasure);
        recipe.getIngredients().add(ingredient);

        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(recipeRepository.save(any())).thenReturn(Mono.just(recipe));
        when(unitOfMeasureRepository.findById(anyString())).thenReturn(Mono.just(unitOfMeasure));
        when(ingredientToIngredientCommand.convert(any())).thenReturn(ingredientCommand);

        //when
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand).block();

        //then
        assertEquals(ingredientId,savedIngredientCommand.getId());
        assertEquals(recipeId,savedIngredientCommand.getRecipeId());
        verify(recipeRepository).findById(anyString());
        verify(recipeRepository).save(any(Recipe.class));

    }

    @Test
    void deleteIngredientById() {
        //given
        Ingredient ingredient = new Ingredient();
        String ingredientId = "2L";
        ingredient.setId(ingredientId);

        Recipe recipe = new Recipe();
        String recipeId = "1L";
        recipe.setId(recipeId);
        recipe.addIngredient(ingredient);
        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(recipeRepository.save(any())).thenReturn(Mono.just(recipe));

        //when
        ingredientService.deleteIngredient(recipeId, ingredientId);

        verify(recipeRepository).findById(anyString());
        verify(recipeRepository).save(any(Recipe.class));
    }
}