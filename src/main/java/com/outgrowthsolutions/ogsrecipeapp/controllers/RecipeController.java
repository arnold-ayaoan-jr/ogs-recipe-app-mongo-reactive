package com.outgrowthsolutions.ogsrecipeapp.controllers;

import com.outgrowthsolutions.ogsrecipeapp.commands.RecipeCommand;
import com.outgrowthsolutions.ogsrecipeapp.exceptions.NotFoundException;
import com.outgrowthsolutions.ogsrecipeapp.services.CategoryService;
import com.outgrowthsolutions.ogsrecipeapp.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RequestMapping("/recipe")
@Controller
public class RecipeController {
    private final RecipeService recipeService;
    private final CategoryService categoryService;
    private final String RECIPE_FORM_URL = "recipe/form";

    public RecipeController(RecipeService recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}/show")
    public String showRecipeById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeById(id));
        return "recipe/show";
    }

    @GetMapping("/new")
    public String addRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return RECIPE_FORM_URL;
    }

    @PostMapping()
    public String saveRecipe(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return RECIPE_FORM_URL;

        Mono<RecipeCommand> savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/" + recipeCommand.getId() + "/show";
//        return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeCommandById(id));
        model.addAttribute("categoryList", categoryService.getAllCategory().collectList());
        return "recipe/form";
    }

    @GetMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable String id, Model model) {
        recipeService.deleteById(id);
        return "redirect:/";
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class, TemplateInputException.class})
    public String handleNotFoundException(Exception exception, Model model) {
        model.addAttribute("exception", exception);
        return "error/404";
    }


}
