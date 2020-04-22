package com.outgrowthsolutions.ogsrecipeapp.controllers;

import com.outgrowthsolutions.ogsrecipeapp.commands.RecipeCommand;
import com.outgrowthsolutions.ogsrecipeapp.services.ImageService;
import com.outgrowthsolutions.ogsrecipeapp.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {
    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{recipeId}/image/upload")
    public String updateRecipeImage(@PathVariable String recipeId, Model model){
        RecipeCommand foundRecipeCommand = recipeService.getRecipeCommandById(recipeId).block();
        if (foundRecipeCommand == null)
            throw new RuntimeException("Recipe not found");
        model.addAttribute("recipe",foundRecipeCommand);
        return "recipe/image/upload";
    }
    @PostMapping("/recipe/{recipeId}/image")
    public String saveRecipeImage(@PathVariable String recipeId, @RequestParam MultipartFile image){
        imageService.saveRecipeImage(recipeId,image).block();
        return "redirect:/recipe/"+recipeId+"/show";
    }
    @GetMapping("/recipe/{recipeId}/image")
    public void showRecipeImage(@PathVariable String recipeId, HttpServletResponse httpServletResponse) throws IOException {
        RecipeCommand foundRecipeCommand = recipeService.getRecipeCommandById(recipeId).block();
        if (foundRecipeCommand == null)
            throw new RuntimeException("Recipe not found");
        if(foundRecipeCommand.getImage() != null){
            byte[] bytes = new byte[foundRecipeCommand.getImage().length];

            int i = 0;
            for (Byte wrappedByte : foundRecipeCommand.getImage()){
                bytes[i++] = wrappedByte; //auto unboxing
            }
            httpServletResponse.setContentType("image/jpeg");
            InputStream inputStream = new ByteArrayInputStream(bytes);
            IOUtils.copy(inputStream,httpServletResponse.getOutputStream());
        }

    }
}
