package com.outgrowthsolutions.ogsrecipeapp.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void saveRecipeImage(String recipeId, MultipartFile file);
}
