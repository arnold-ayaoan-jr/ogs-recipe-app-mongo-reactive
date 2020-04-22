package com.outgrowthsolutions.ogsrecipeapp.repositories.reactive;

import com.outgrowthsolutions.ogsrecipeapp.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.*;
@DataMongoTest
class CategoryReactiveRepositoryIT {
    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @BeforeEach
    void setUp() {
        categoryReactiveRepository.deleteAll().block();
    }

    @Test
    void findByDescription() {
        Category category = new Category();
        String desc = "Test";
        category.setDescription(desc);

        categoryReactiveRepository.save(category).then().block();

        Category fetchedCat = categoryReactiveRepository.findByDescription(desc).block();

        assertNotNull(fetchedCat.getId());
    }

    @Test
    void save() {
        Category category = new Category();
        category.setDescription("Ayaoan");

        categoryReactiveRepository.save(category).block();

        Long count = categoryReactiveRepository.count().block();

        assertEquals(1L, count);
    }
}