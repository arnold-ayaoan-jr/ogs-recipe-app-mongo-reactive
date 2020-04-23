package com.outgrowthsolutions.ogsrecipeapp.controllers;

import com.outgrowthsolutions.ogsrecipeapp.config.WebConfig;
import com.outgrowthsolutions.ogsrecipeapp.domain.Recipe;
import com.outgrowthsolutions.ogsrecipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;

import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
public class RouterFunctionTest {
    WebTestClient webTestClient;
    @Mock
    RecipeService recipeService;

    @BeforeEach
    void setUp() {
        WebConfig webConfig = new WebConfig();
        RouterFunction<?> routerFunction = webConfig.routes(recipeService);
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test
    void getRecipesEmpty() {
        when(recipeService.getRecipes()).thenReturn(Flux.just(new Recipe()));
        webTestClient.get().uri("/api/recipes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

    }
}
