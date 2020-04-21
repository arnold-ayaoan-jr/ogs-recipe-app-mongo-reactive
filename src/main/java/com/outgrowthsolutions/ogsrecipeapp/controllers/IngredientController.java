package com.outgrowthsolutions.ogsrecipeapp.controllers;

import com.outgrowthsolutions.ogsrecipeapp.commands.IngredientCommand;
import com.outgrowthsolutions.ogsrecipeapp.commands.RecipeCommand;
import com.outgrowthsolutions.ogsrecipeapp.commands.UnitOfMeasureCommand;
import com.outgrowthsolutions.ogsrecipeapp.services.IngredientService;
import com.outgrowthsolutions.ogsrecipeapp.services.RecipeService;
import com.outgrowthsolutions.ogsrecipeapp.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String showIngredients(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeCommandById(recipeId));
        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient",
                ingredientService.getIngredientCommandByIdAndRecipeId(ingredientId, recipeId));
        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String addIngredient(@PathVariable String recipeId, Model model) {
        RecipeCommand recipeCommand = recipeService.getRecipeCommandById(recipeId);
        if (recipeCommand == null || recipeCommand.getId() == null)
            throw new RuntimeException("Recipe not found");

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());

        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("unitOfMeasures", unitOfMeasureService.getAllUnitOfMeasure());
        return "recipe/ingredient/form";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        IngredientCommand ingredientCommand = ingredientService.getIngredientCommandByIdAndRecipeId(ingredientId,recipeId);
        if(ingredientCommand == null || ingredientCommand.getId() == null || ingredientCommand.getRecipeId() == null)
            throw new RuntimeException("Ingredient not found");
        model.addAttribute("ingredient",ingredientCommand);
        model.addAttribute("unitOfMeasures",unitOfMeasureService.getAllUnitOfMeasure());
        return "recipe/ingredient/form";
    }
    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveIngredient(@PathVariable String recipeId, @ModelAttribute IngredientCommand ingredientCommand){
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
        return "redirect:/recipe/"+savedIngredientCommand.getRecipeId()
                +"/ingredient/"+savedIngredientCommand.getId()+"/show";
    }
    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId){
        ingredientService.deleteIngredient(recipeId,ingredientId);
        return "redirect:/recipe/"+recipeId+"/ingredients";

    }
}
