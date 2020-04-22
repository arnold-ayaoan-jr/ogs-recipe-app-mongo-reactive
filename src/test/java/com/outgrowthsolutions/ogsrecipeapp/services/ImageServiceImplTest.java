package com.outgrowthsolutions.ogsrecipeapp.services;

import com.outgrowthsolutions.ogsrecipeapp.domain.Recipe;
import com.outgrowthsolutions.ogsrecipeapp.repositories.RecipeRepository;
import com.outgrowthsolutions.ogsrecipeapp.repositories.reactive.RecipeReactiveRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {
    @Mock
    RecipeReactiveRepository recipeRepository;
    @InjectMocks
    ImageServiceImpl imageService;

    @Test
    void saveRecipeImage() {
        //given
        byte[] fileContent = "Praise the Lord".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("ptL.txt", fileContent);
        Recipe recipe = new Recipe();
        String recipeId = "1L";
        recipe.setId(recipeId);
        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(recipeRepository.save(any())).thenReturn(Mono.just(recipe));

        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        imageService.saveRecipeImage(recipeId,mockMultipartFile);

        //then
        verify(recipeRepository).save(recipeArgumentCaptor.capture());
        Recipe savedRecipe=recipeArgumentCaptor.getValue();
        assertEquals(fileContent.length,savedRecipe.getImage().length);
    }
}