package com.chauri.GoalKeeper.service;

import com.chauri.GoalKeeper.dto.TaskDTO;
import com.chauri.GoalKeeper.entity.Task;

import java.util.List;

public interface ITaskService {
    TaskDTO createTask(TaskDTO newTask);

    TaskDTO getTask(Long taskId);

    List<TaskDTO> getAllTasks();

    TaskDTO updateTask(Long taskId, TaskDTO taskDtoDetails);

    void deleteTask(Long taskId);
}
