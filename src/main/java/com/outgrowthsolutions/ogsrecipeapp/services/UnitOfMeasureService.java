package com.outgrowthsolutions.ogsrecipeapp.services;

import com.outgrowthsolutions.ogsrecipeapp.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

import java.util.Set;

public interface UnitOfMeasureService  {
    Flux<UnitOfMeasureCommand> getAllUnitOfMeasure();
}
