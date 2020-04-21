package com.outgrowthsolutions.ogsrecipeapp.controllers;

import com.outgrowthsolutions.ogsrecipeapp.commands.RecipeCommand;
import com.outgrowthsolutions.ogsrecipeapp.services.ImageService;
import com.outgrowthsolutions.ogsrecipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ImageControllerTest {
    @Mock
    ImageService imageService;
    @Mock
    RecipeService recipeService;

    @InjectMocks
    ImageController imageController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(imageController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void updateRecipeImage() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1L");
        when(recipeService.getRecipeCommandById(anyString())).thenReturn(recipeCommand);

        //when
        mockMvc.perform(get("/recipe/1/image/upload"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/image/upload"));
        //then
        verify(recipeService).getRecipeCommandById(anyString());
    }

    @Test
    void saveRecipeImage() throws Exception {
        //given
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("image", "gloryToGod.txt", "text/plain", "To God be the glory forever and ever. Amen".getBytes());
        doNothing().when(imageService).saveRecipeImage(anyString(), any());

        //when
        mockMvc.perform(multipart("/recipe/1/image").file(mockMultipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/show"));

        //then
        verify(imageService).saveRecipeImage(anyString(), any());

    }

    @Test
    void showRecipeImage() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1L");

        String mockImage = "Holy, holy, holy is the Lord who was and is and is to come";
        Byte[] mockBytes = new Byte[mockImage.getBytes().length];
        int i = 0;
        for (byte primByte : mockImage.getBytes()) {
            mockBytes[i++] = primByte;
        }
        recipeCommand.setImage(mockBytes);
        when(recipeService.getRecipeCommandById(anyString())).thenReturn(recipeCommand);

        //when
        MockHttpServletResponse mockHttpServletResponse =
                mockMvc.perform(get("/recipe/1/image"))
                        .andExpect(status().isOk())
                        .andReturn().getResponse();

        //then
        assertEquals(mockImage.getBytes().length, mockHttpServletResponse.getContentAsByteArray().length);
        verify(recipeService).getRecipeCommandById(anyString());

    }
}
