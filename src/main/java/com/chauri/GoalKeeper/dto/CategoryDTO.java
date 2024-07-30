package com.chauri.GoalKeeper.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
}
