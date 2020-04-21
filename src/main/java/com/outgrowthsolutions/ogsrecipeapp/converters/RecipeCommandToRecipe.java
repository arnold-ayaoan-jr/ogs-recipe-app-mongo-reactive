package com.outgrowthsolutions.ogsrecipeapp.converters;

import com.outgrowthsolutions.ogsrecipeapp.commands.RecipeCommand;
import com.outgrowthsolutions.ogsrecipeapp.domain.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
    private final CategoryCommandToCategory categoryCommandToCategory;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryCommandToCategory,
                                 IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public Recipe convert(RecipeCommand recipeCommand) {

        if (recipeCommand == null)
            return  null;

        final Recipe converted = new Recipe();
        converted.setId(recipeCommand.getId());
        converted.setDescription(recipeCommand.getDescription());
        converted.setCookTime(recipeCommand.getCookTime());
        converted.setPrepTime(recipeCommand.getPrepTime());
        converted.setDifficulty(recipeCommand.getDifficulty());
        converted.setDirections(recipeCommand.getDirections());
        converted.setSource(recipeCommand.getSource());
        converted.setUrl(recipeCommand.getUrl());
        converted.setNote(recipeCommand.getNote());
        converted.setImage(recipeCommand.getImage());
        converted.setYield(recipeCommand.getYield());
        if (recipeCommand.getCategories() != null && recipeCommand.getCategories().size() > 0)
            recipeCommand.getCategories().forEach(categoryCommand ->
                    converted.getCategories().add(categoryCommandToCategory.convert(categoryCommand)));
        if(recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0)
            recipeCommand.getIngredients().forEach(ingredientCommand ->
                    converted.getIngredients().add(ingredientCommandToIngredient.convert(ingredientCommand)));

        return converted;
    }
}
