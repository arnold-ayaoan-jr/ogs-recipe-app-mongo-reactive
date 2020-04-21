package com.outgrowthsolutions.ogsrecipeapp.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Note {
    @Id
    private String id;
    private String recipeNotes;
}
