package com.chauri.GoalKeeper.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private String priority;
    private CategoryDTO category;
    private TaskDTO parentTask;
    private List<TaskDTO> subtasks;
}
