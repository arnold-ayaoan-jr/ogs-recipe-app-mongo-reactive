package com.outgrowthsolutions.ogsrecipeapp.repositories;

import com.outgrowthsolutions.ogsrecipeapp.bootstrap.RecipeBootstrap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
@DataMongoTest
class UnitOfMeasureRepositoryIT {
    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp(){
        recipeRepository.deleteAll();
        unitOfMeasureRepository.deleteAll();
        categoryRepository.deleteAll();

        RecipeBootstrap recipeBootstrap = new RecipeBootstrap(recipeRepository,unitOfMeasureRepository,categoryRepository);

        recipeBootstrap.onApplicationEvent(null);
    }

    @Test
    void findByDescription() {
        String descToFind = "Teaspoon";
        assertEquals(descToFind,
                unitOfMeasureRepository.findByDescription(descToFind).get().getDescription());
    }
    @Test
    void findByDescriptionCup() {
        String descToFind = "Cup";
        assertEquals(descToFind,
                unitOfMeasureRepository.findByDescription(descToFind).get().getDescription());
    }
}