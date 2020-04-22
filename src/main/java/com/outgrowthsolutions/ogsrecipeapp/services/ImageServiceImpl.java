package com.outgrowthsolutions.ogsrecipeapp.services;

import com.outgrowthsolutions.ogsrecipeapp.domain.Recipe;
import com.outgrowthsolutions.ogsrecipeapp.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    private  final RecipeReactiveRepository recipeRepository;

    public ImageServiceImpl(RecipeReactiveRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Mono<Void> saveRecipeImage(String recipeId, MultipartFile file) {
        log.info("praise the Lord"+file.getOriginalFilename());
        Mono<Recipe> recipeMono = recipeRepository.findById(recipeId)
                .map(recipe -> {
                    Byte[] byteObjects = new Byte[0];
                    try {
                        byteObjects = new Byte[file.getBytes().length];

                        int i = 0;
                        for (byte b : file.getBytes()) {
                            byteObjects[i++] = b;
                        }

                        recipe.setImage(byteObjects);
                        return recipe;
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                });

        Mono<Recipe> recipeMono1= recipeRepository.save(recipeMono.block());
        log.info(recipeMono1.block().getDescription());
        return Mono.empty();
    }
}
