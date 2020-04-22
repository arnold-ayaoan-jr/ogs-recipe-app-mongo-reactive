package com.outgrowthsolutions.ogsrecipeapp.repositories.reactive;

import com.outgrowthsolutions.ogsrecipeapp.domain.UnitOfMeasure;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UnitOfMeasureReactiveRepository extends ReactiveMongoRepository<UnitOfMeasure,String> {
    Mono<UnitOfMeasure> findByDescription(String description);
}
