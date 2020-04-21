package com.outgrowthsolutions.ogsrecipeapp.repositories;

import com.outgrowthsolutions.ogsrecipeapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, String> {
}
