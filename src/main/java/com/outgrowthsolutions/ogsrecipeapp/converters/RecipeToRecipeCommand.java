package com.outgrowthsolutions.ogsrecipeapp.converters;

import com.outgrowthsolutions.ogsrecipeapp.commands.RecipeCommand;
import com.outgrowthsolutions.ogsrecipeapp.domain.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {
    private final CategoryToCategoryCommand categoryToCategoryCommand;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryToCategoryCommand,
                                 IngredientToIngredientCommand ingredientToIngredientCommand) {

        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public RecipeCommand convert(Recipe recipe) {

        if (recipe == null)
            return null;

        final RecipeCommand converted = new RecipeCommand();
        converted.setDescription(recipe.getDescription());
        converted.setCookTime(recipe.getCookTime());
        converted.setPrepTime(recipe.getPrepTime());
        converted.setDifficulty(recipe.getDifficulty());
        converted.setDirections(recipe.getDirections());
        converted.setSource(recipe.getSource());
        converted.setUrl(recipe.getUrl());
        converted.setNote(recipe.getNote());
        converted.setImage(recipe.getImage());
        converted.setYield(recipe.getYield());
        converted.setId(recipe.getId());
        if (recipe.getCategories() != null && recipe.getCategories().size() > 0)
            recipe.getCategories().forEach(categoryCommand ->
                    converted.getCategories().add(categoryToCategoryCommand.convert(categoryCommand)));
        if (recipe.getIngredients() != null && recipe.getIngredients().size() > 0)
            recipe.getIngredients().forEach(ingredientCommand ->
                    converted.getIngredients().add(ingredientToIngredientCommand.convert(ingredientCommand)));

        return converted;
    }
}
