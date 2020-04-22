package com.outgrowthsolutions.ogsrecipeapp.services;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface ImageService {
    Mono<Void> saveRecipeImage(String recipeId, MultipartFile file);
}
