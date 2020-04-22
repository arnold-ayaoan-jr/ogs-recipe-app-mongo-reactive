package com.outgrowthsolutions.ogsrecipeapp.controllers;

import com.outgrowthsolutions.ogsrecipeapp.commands.IngredientCommand;
import com.outgrowthsolutions.ogsrecipeapp.commands.RecipeCommand;
import com.outgrowthsolutions.ogsrecipeapp.commands.UnitOfMeasureCommand;
import com.outgrowthsolutions.ogsrecipeapp.services.IngredientService;
import com.outgrowthsolutions.ogsrecipeapp.services.RecipeService;
import com.outgrowthsolutions.ogsrecipeapp.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {
    @Mock
    RecipeService recipeService;
    @Mock
    IngredientService ingredientService;
    @Mock
    UnitOfMeasureService unitOfMeasureService;
    @InjectMocks
    IngredientController ingredientController;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    void showIngredients() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.getRecipeCommandById(anyString())).thenReturn(Mono.just(recipeCommand));

        //when
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/ingredient/list"));

        //then
        verify(recipeService).getRecipeCommandById(anyString());
    }

    @Test
    void showIngredient() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();

        //when
        when(ingredientService.getIngredientCommandByIdAndRecipeId(anyString(),anyString())).thenReturn(Mono.just(ingredientCommand));

        //then
        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(view().name("recipe/ingredient/show"));
        verify(ingredientService).getIngredientCommandByIdAndRecipeId(anyString(),anyString());
    }

    @Test
    void addIngredient() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1L");
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = new HashSet<>();
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId("1L");
        unitOfMeasureCommands.add(unitOfMeasureCommand);

        //when
        when(recipeService.getRecipeCommandById(anyString())).thenReturn(Mono.just(recipeCommand));
        when(unitOfMeasureService.getAllUnitOfMeasure()).thenReturn(Flux.fromIterable(unitOfMeasureCommands));

        //then
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("unitOfMeasures"))
                .andExpect(view().name("recipe/ingredient/form"));
        verify(recipeService).getRecipeCommandById(anyString());
        verify(unitOfMeasureService).getAllUnitOfMeasure();
    }

    @Test
    void updateIngredient() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId("1L");
        ingredientCommand.setId("2L");
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId("1L");
        unitOfMeasureCommand.setDescription("Each");
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = new HashSet<>();
        unitOfMeasureCommands.add(unitOfMeasureCommand);

        //when
        when(ingredientService.getIngredientCommandByIdAndRecipeId(anyString(),anyString())).thenReturn(Mono.just(ingredientCommand));
        when(unitOfMeasureService.getAllUnitOfMeasure()).thenReturn(Flux.fromIterable(unitOfMeasureCommands));

        //then
        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("unitOfMeasures"))
                .andExpect(view().name("recipe/ingredient/form"));
        verify(ingredientService).getIngredientCommandByIdAndRecipeId(anyString(),anyString());
        verify(unitOfMeasureService).getAllUnitOfMeasure();
    }

    @Test
    void saveIngredient() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId("1");
        ingredientCommand.setId("2");

        //when
        when(ingredientService.saveIngredientCommand(any(IngredientCommand.class))).thenReturn(Mono.just(ingredientCommand));

        //then
        mockMvc.perform(post("/recipe/1/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","")
                .param("description","Kilogram")
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredient/2/show"));
        verify(ingredientService).saveIngredientCommand(any(IngredientCommand.class));
    }

    @Test
    void deleteIngredient() throws Exception {
        mockMvc.perform(get("/recipe/1/ingredient/2/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredients"));
        verify(ingredientService).deleteIngredient(anyString(),anyString());
    }
}