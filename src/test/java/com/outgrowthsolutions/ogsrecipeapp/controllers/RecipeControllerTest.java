package com.outgrowthsolutions.ogsrecipeapp.controllers;

import com.outgrowthsolutions.ogsrecipeapp.commands.CategoryCommand;
import com.outgrowthsolutions.ogsrecipeapp.commands.RecipeCommand;
import com.outgrowthsolutions.ogsrecipeapp.domain.Recipe;
import com.outgrowthsolutions.ogsrecipeapp.exceptions.NotFoundException;
import com.outgrowthsolutions.ogsrecipeapp.services.CategoryService;
import com.outgrowthsolutions.ogsrecipeapp.services.RecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
class RecipeControllerTest {
    @Mock
    RecipeServiceImpl recipeService;
    @Mock
    CategoryService categoryService;

    @InjectMocks
    RecipeController recipeController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void showRecipeById() throws Exception {
        String recipeId = "7L";
        Recipe mockRecipe = new Recipe();
        mockRecipe.setId(recipeId);

        when(recipeService.getRecipeById(anyString())).thenReturn(mockRecipe);

        mockMvc.perform(get("/recipe/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
        verify(recipeService).getRecipeById(anyString());

    }

    @Test
    void showRecipeByIdNotFound() throws Exception {

        when(recipeService.getRecipeById(anyString())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/2/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"));
        verify(recipeService).getRecipeById(anyString());

    }

    @Test
    void addRecipe() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
//                .andExpect(model().attribute("recipe", any(RecipeCommand.class)))
                .andExpect(view().name("recipe/form"))
        ;
    }

    @Test
    void saveRecipe() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId("2");

        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some description")
                .param("directions", "some direction")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/show"));
        verify(recipeService).saveRecipeCommand(any());
    }

    @Test
    void saveRecipeFormValidation() throws Exception {

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        )
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(view().name("recipe/form"));
    }

    @Test
    void updateRecipe() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("2L");
        Set<CategoryCommand> mockCategoryCommands = new HashSet<>();
        mockCategoryCommands.add(new CategoryCommand());

        when(categoryService.getAllCategory()).thenReturn(mockCategoryCommands);
        when(recipeService.getRecipeCommandById(anyString())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/2/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attributeExists("categoryList"))
                .andExpect(model().attribute("recipe", recipeCommand))
                .andExpect(model().attribute("categoryList", mockCategoryCommands))
                .andExpect(view().name("recipe/form"));

        verify(recipeService).getRecipeCommandById(anyString());
    }

    @Test
    void deleteRecipe() throws Exception {
        mockMvc.perform(get("/recipe/2/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipeService).deleteById(anyString());
    }
}