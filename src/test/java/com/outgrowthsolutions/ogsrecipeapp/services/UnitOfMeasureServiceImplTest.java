package com.outgrowthsolutions.ogsrecipeapp.services;

import com.outgrowthsolutions.ogsrecipeapp.commands.UnitOfMeasureCommand;
import com.outgrowthsolutions.ogsrecipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.outgrowthsolutions.ogsrecipeapp.domain.UnitOfMeasure;
import com.outgrowthsolutions.ogsrecipeapp.repositories.UnitOfMeasureRepository;
import com.outgrowthsolutions.ogsrecipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasureServiceImplTest {
    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureRepository;
    @Mock
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    @InjectMocks
    UnitOfMeasureServiceImpl unitOfMeasureService;

    @Test
    void getAllUnitOfMeasure() {
        //given
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId("1L");
        unitOfMeasures.add(unitOfMeasure);

        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId("1L");

        when(unitOfMeasureRepository.findAll()).thenReturn(Flux.fromIterable(unitOfMeasures));
        when(unitOfMeasureToUnitOfMeasureCommand.convert(any())).thenReturn(unitOfMeasureCommand);

        //when
        List<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.getAllUnitOfMeasure().collectList().block();

        //then
        assertEquals(1,unitOfMeasureCommands.size());
        verify(unitOfMeasureRepository).findAll();
        verify(unitOfMeasureToUnitOfMeasureCommand).convert(any());
    }
}