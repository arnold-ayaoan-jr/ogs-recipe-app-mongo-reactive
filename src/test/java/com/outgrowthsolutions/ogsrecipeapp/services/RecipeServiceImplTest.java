package com.outgrowthsolutions.ogsrecipeapp.services;

import com.outgrowthsolutions.ogsrecipeapp.commands.RecipeCommand;
import com.outgrowthsolutions.ogsrecipeapp.converters.RecipeCommandToRecipe;
import com.outgrowthsolutions.ogsrecipeapp.converters.RecipeToRecipeCommand;
import com.outgrowthsolutions.ogsrecipeapp.domain.Recipe;
import com.outgrowthsolutions.ogsrecipeapp.repositories.RecipeRepository;
import com.outgrowthsolutions.ogsrecipeapp.repositories.reactive.RecipeReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
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
    RecipeReactiveRepository recipeRepository;

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
        when(recipeRepository.findAll()).thenReturn(Flux.fromIterable(recipesFromRepo));

        List<Recipe> recipes = recipeService.getRecipes().collectList().block();
        assertEquals(recipes.size(),1);
//      Verify that the service called the repo
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void getRecipeById(){
        String recipeId = "1L";
        Recipe mockRecipe = new Recipe();
        mockRecipe.setId(recipeId);
        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(mockRecipe));

        Recipe foundRecipe = recipeService.getRecipeById(recipeId).block();
        assertNotNull(foundRecipe,"Null recipe found");
        verify(recipeRepository).findById(anyString());
        verify(recipeRepository,never()).findAll();
        verify(recipeRepository,never()).findAllById(Mono.just(anyString()));
    }
    @Test
    void getRecipeCommandById(){
        String id = "1L";
        Recipe mockRecipe = new Recipe();
        mockRecipe.setId(id);
        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(mockRecipe));

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(id);
        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand foundRecipeCommand = recipeService.getRecipeCommandById(id).block();
        assertNotNull(foundRecipeCommand,"Null recipe found");
        verify(recipeRepository).findById(anyString());
        verify(recipeRepository,never()).findAll();
        verify(recipeRepository,never()).findAllById(Mono.just(anyString()));
    }

    @Test
    void deleteById() {
        String id = "1L";
        when(recipeRepository.deleteById(anyString())).thenReturn(Mono.empty());
        recipeService.deleteById(id);
        verify(recipeRepository).deleteById(anyString());
    }
}