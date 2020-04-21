package com.outgrowthsolutions.ogsrecipeapp.services;

import com.outgrowthsolutions.ogsrecipeapp.commands.UnitOfMeasureCommand;
import com.outgrowthsolutions.ogsrecipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.outgrowthsolutions.ogsrecipeapp.domain.UnitOfMeasure;
import com.outgrowthsolutions.ogsrecipeapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasureServiceImplTest {
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;
    @Mock
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    @InjectMocks
    UnitOfMeasureServiceImpl unitOfMeasureService;

    @Test
    void getAllUnitOfMeasure() {
        //given
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        unitOfMeasures.add(new UnitOfMeasure());
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId("1L");

        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);
        when(unitOfMeasureToUnitOfMeasureCommand.convert(any())).thenReturn(unitOfMeasureCommand);

        //when
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.getAllUnitOfMeasure();

        //then
        assertEquals(1,unitOfMeasureCommands.size());
        verify(unitOfMeasureRepository).findAll();
        verify(unitOfMeasureToUnitOfMeasureCommand).convert(any());
    }
}