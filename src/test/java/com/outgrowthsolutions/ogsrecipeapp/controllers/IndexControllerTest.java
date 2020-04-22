package com.outgrowthsolutions.ogsrecipeapp.controllers;

import com.outgrowthsolutions.ogsrecipeapp.domain.Recipe;
import com.outgrowthsolutions.ogsrecipeapp.services.RecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {
    @Mock
    RecipeServiceImpl recipeService;
    @Mock
    Model model;

    @InjectMocks
    IndexController indexController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
    }

    @Test
    void testMockMvc() throws Exception {
        when(recipeService.getRecipes()).thenReturn(Flux.empty());
        mockMvc.perform(get("/")).
                andExpect(status().isOk()).
                andExpect(view().name("index"));
    }

    @Test
    void viewIndex() throws Exception {
//      given
        List<Recipe> mockRecipes = new ArrayList<>();
        mockRecipes.add(new Recipe());
        Recipe recipe = new Recipe();
        recipe.setId("2L");
        mockRecipes.add(recipe);
        when(recipeService.getRecipes()).thenReturn(Flux.fromIterable(mockRecipes));

        ArgumentCaptor<List<Recipe>> argumentCaptor = ArgumentCaptor.forClass(List.class);

//      when
        String viewName = indexController.viewIndex(model);

//      then
        assertEquals("index",viewName);
        verify(recipeService,times(1)).getRecipes();
        verify(model,times(1)).addAttribute(eq("recipes"),argumentCaptor.capture());
        List<Recipe> recipesFromController = argumentCaptor.getValue();
        assertEquals(mockRecipes.size(),recipesFromController.size());
    }
}