package com.outgrowthsolutions.ogsrecipeapp.services;

import com.outgrowthsolutions.ogsrecipeapp.commands.RecipeCommand;
import com.outgrowthsolutions.ogsrecipeapp.converters.RecipeCommandToRecipe;
import com.outgrowthsolutions.ogsrecipeapp.converters.RecipeToRecipeCommand;
import com.outgrowthsolutions.ogsrecipeapp.domain.Recipe;
import com.outgrowthsolutions.ogsrecipeapp.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeReactiveRepository recipeRepository;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final RecipeCommandToRecipe recipeCommandToRecipe;

    public RecipeServiceImpl(RecipeReactiveRepository recipeRepository, RecipeToRecipeCommand recipeToRecipeCommand,
                             RecipeCommandToRecipe recipeCommandToRecipe) {
        this.recipeRepository = recipeRepository;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("RecipeServiceImpl::getRecipes");
        return recipeRepository.findAll();
    }

    @Override
    public Mono<Recipe> getRecipeById(String id) {
        return recipeRepository.findById(id);
//                .orElseThrow(() -> new NotFoundException("Recipe not found for id value:" + id));
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand) {
        return recipeRepository.save(recipeCommandToRecipe.convert(recipeCommand))
                .map(recipeToRecipeCommand::convert);
    }

    public Mono<RecipeCommand> getRecipeCommandById(String id) {
        return recipeRepository.findById(id)
                .map(recipe -> {
                    RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

                    recipeCommand.getIngredients().forEach(ingredientCommand -> {
                        ingredientCommand.setRecipeId(recipeCommand.getId());
                    });

                    return recipeCommand;
                });
    }

    public Mono<Void> deleteById(String id) {

        recipeRepository.deleteById(id).block();
        return Mono.empty();
    }
}
