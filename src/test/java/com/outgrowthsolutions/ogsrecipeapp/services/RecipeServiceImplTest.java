package com.outgrowthsolutions.ogsrecipeapp.services;

import com.outgrowthsolutions.ogsrecipeapp.commands.RecipeCommand;
import com.outgrowthsolutions.ogsrecipeapp.converters.RecipeCommandToRecipe;
import com.outgrowthsolutions.ogsrecipeapp.converters.RecipeToRecipeCommand;
import com.outgrowthsolutions.ogsrecipeapp.domain.Recipe;
import com.outgrowthsolutions.ogsrecipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    RecipeServiceImpl recipeService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getRecipes() {
//      Setup data from repo
        Recipe recipe = new Recipe();
        Set<Recipe> recipesFromRepo = new HashSet<>();
        recipesFromRepo.add(recipe);

//      Intercept getRecipes and return mocked data
        when(recipeRepository.findAll()).thenReturn(recipesFromRepo);

        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(recipes.size(),1);
//      Verify that the service called the repo
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void getRecipeById(){
        String recipeId = "1L";
        Recipe mockRecipe = new Recipe();
        mockRecipe.setId(recipeId);
        when(recipeRepository.findById(anyString())).thenReturn(Optional.of(mockRecipe));

        Recipe foundRecipe = recipeService.getRecipeById(recipeId);
        assertNotNull(foundRecipe,"Null recipe found");
        verify(recipeRepository).findById(anyString());
        verify(recipeRepository,never()).findAll();
        verify(recipeRepository,never()).findAllById(any());
    }
    @Test
    void getRecipeCommandById(){
        String id = "1L";
        Recipe mockRecipe = new Recipe();
        mockRecipe.setId(id);
        when(recipeRepository.findById(anyString())).thenReturn(Optional.of(mockRecipe));

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(id);
        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand foundRecipeCommand = recipeService.getRecipeCommandById(id);
        assertNotNull(foundRecipeCommand,"Null recipe found");
        verify(recipeRepository).findById(anyString());
        verify(recipeRepository,never()).findAll();
        verify(recipeRepository,never()).findAllById(any());
    }

    @Test
    void deleteById() {
        String id = "1L";
        recipeService.deleteById(id);
        verify(recipeRepository).deleteById(anyString());
    }
}