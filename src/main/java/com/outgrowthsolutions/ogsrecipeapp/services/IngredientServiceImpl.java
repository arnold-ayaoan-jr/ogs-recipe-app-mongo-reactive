package com.outgrowthsolutions.ogsrecipeapp.services;

import com.outgrowthsolutions.ogsrecipeapp.commands.IngredientCommand;
import com.outgrowthsolutions.ogsrecipeapp.converters.IngredientCommandToIngredient;
import com.outgrowthsolutions.ogsrecipeapp.converters.IngredientToIngredientCommand;
import com.outgrowthsolutions.ogsrecipeapp.domain.Ingredient;
import com.outgrowthsolutions.ogsrecipeapp.domain.Recipe;
import com.outgrowthsolutions.ogsrecipeapp.repositories.RecipeRepository;
import com.outgrowthsolutions.ogsrecipeapp.repositories.reactive.RecipeReactiveRepository;
import com.outgrowthsolutions.ogsrecipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
    private final RecipeReactiveRepository recipeRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeReactiveRepository recipeRepository,
                                 UnitOfMeasureReactiveRepository unitOfMeasureRepository,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 IngredientToIngredientCommand ingredientToIngredientCommand) {

        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public Mono<IngredientCommand> getIngredientCommandByIdAndRecipeId(String ingredientId, String recipeId) {
        return recipeRepository.findById(recipeId)
                .flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
                .single()
                .map(ingredient -> {
                    IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);
                    ingredientCommand.setRecipeId(recipeId);
                    return ingredientCommand;
                });
    }

    @Override
    @Transactional
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand) {
        Recipe recipe = recipeRepository.findById(ingredientCommand.getRecipeId()).block();


        if(recipe == null){

            //todo toss error if not found!
//            Mono.error(new RuntimeException("Recipe not found for id: " + ingredientCommand.getRecipeId()));
            return Mono.just(new IngredientCommand());
        } else {

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository
                        .findById(ingredientCommand.getUnitOfMeasure().getId()).block());
//                        .doOnError(throwable -> throwable.))
                if (ingredientFound.getUnitOfMeasure() == null){
                    new RuntimeException("UoM not found");
                }; //todo address this
            } else {
                //add new Ingredient
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
//                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe).block();

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            //check by description
            if(!savedIngredientOptional.isPresent()){
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
                        .findFirst();
            }

            //todo check for fail

            IngredientCommand ingredientCommandSaved = ingredientToIngredientCommand.convert(savedIngredientOptional.get());
            ingredientCommandSaved.setRecipeId(recipe.getId());

            return Mono.just(ingredientCommandSaved);
        }
    }

    @Override
    public Mono<Void> deleteIngredient(String recipeId, String ingredientId) {
        log.debug("Deleting ingredient: " + recipeId + ":" + ingredientId);

        Recipe recipe = recipeRepository.findById(recipeId).block();

        if (recipe != null) {

            log.debug("found recipe");

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                log.debug("found Ingredient");

                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe).block();
            }
        } else {
            log.debug("Recipe Id Not found. Id:" + recipeId);
        }

        return Mono.empty();
    }
}
