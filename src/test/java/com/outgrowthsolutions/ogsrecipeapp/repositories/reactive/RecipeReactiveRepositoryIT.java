package com.outgrowthsolutions.ogsrecipeapp.repositories.reactive;

import com.outgrowthsolutions.ogsrecipeapp.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class RecipeReactiveRepositoryIT {
    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;

    @BeforeEach
    void setUp() {
        recipeReactiveRepository.deleteAll().block();
    }

    @Test
    void save() {
        Recipe recipe = new Recipe();
        recipe.setDescription("Kare Kare");

        recipeReactiveRepository.save(recipe).block();

        Long count = recipeReactiveRepository.count().block();

        assertEquals(1L, count);
    }
}