package com.outgrowthsolutions.ogsrecipeapp.repositories.reactive;

import com.outgrowthsolutions.ogsrecipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class UnitOfMeasureReactiveRepositoryIT {
    public static final String DESCRIPTION = "Each";
    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @BeforeEach
    void setUp() {
        unitOfMeasureReactiveRepository.deleteAll().block();
    }

    @Test
    void save() {
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setDescription(DESCRIPTION);

        unitOfMeasureReactiveRepository.save(uom).block();

        Long count = unitOfMeasureReactiveRepository.count().block();

        assertEquals(Long.valueOf(1L), count);

    }

    @Test
    void findByDescription() {
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setDescription("EACH");

        unitOfMeasureReactiveRepository.save(uom).block();

        UnitOfMeasure fetchedUOM = unitOfMeasureReactiveRepository.findByDescription("EACH").block();

        assertEquals("EACH", fetchedUOM.getDescription());
    }
}