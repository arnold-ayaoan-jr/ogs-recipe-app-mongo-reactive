package com.outgrowthsolutions.ogsrecipeapp.repositories.reactive;

import com.outgrowthsolutions.ogsrecipeapp.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe,String> {
}
