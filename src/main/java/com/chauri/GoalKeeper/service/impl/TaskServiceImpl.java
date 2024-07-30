package com.chauri.GoalKeeper.service.impl;

import com.chauri.GoalKeeper.dto.TaskDTO;
import com.chauri.GoalKeeper.entity.Category;
import com.chauri.GoalKeeper.entity.Task;
import com.chauri.GoalKeeper.exception.IllegalOperationException;
import com.chauri.GoalKeeper.exception.ResourceNotFoundException;
import com.chauri.GoalKeeper.mapper.TaskMapper;
import com.chauri.GoalKeeper.repository.CategoryRepository;
import com.chauri.GoalKeeper.repository.TaskRepository;
import com.chauri.GoalKeeper.service.ITaskService;
import com.chauri.GoalKeeper.utils.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements ITaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public TaskDTO getTask(Long taskId) {
        return TaskMapper.toDTO(taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("Task", "taskId", taskId.toString())));
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findByParentTaskIsNull().stream().map(TaskMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public TaskDTO createTask(TaskDTO newTaskDTO) {
        Task newTask = TaskMapper.toEntity(newTaskDTO);

        if (newTask.getParentTask() != null) {
            Task parentTask = taskRepository.findById(newTask.getParentTask().getId()).orElseThrow(
                    () -> new ResourceNotFoundException("ParentTask", "ParentTaskId", newTask.getParentTask().getId().toString()));
            parentTask.addSubtask(newTask);
        }

        if (newTaskDTO.getCategory() != null) {
            Category category = categoryRepository.findById(newTaskDTO.getCategory().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", newTaskDTO.getCategory().getId().toString()));
            newTask.setCategory(category);
        }

        return TaskMapper.toDTO(taskRepository.save(newTask));
    }

    @Override
    public TaskDTO updateTask(Long taskId, TaskDTO taskDtoDetails) {
        Task existingTask = taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("Task", "taskId", taskId.toString()));

        existingTask.setTitle(taskDtoDetails.getTitle());
        existingTask.setDescription(taskDtoDetails.getDescription());
        existingTask.setPriority(Priority.valueOf(taskDtoDetails.getPriority()));

        if (taskDtoDetails.getCategory() != null) {
            Category category = categoryRepository.findById(taskDtoDetails.getCategory().getId()).orElseThrow(
                    () -> new ResourceNotFoundException("Category", "CategoryId", taskDtoDetails.getCategory().getId().toString()));
            existingTask.setCategory(category);
        }

        if (taskDtoDetails.getParentTask() != null) {
            if (existingTask.getId().equals(taskDtoDetails.getParentTask().getId())) {
                throw new IllegalOperationException("A task cannot be its own parent.");
            }
            Task newParentTask = taskRepository.findById(taskDtoDetails.getParentTask().getId()).orElseThrow(
                    () -> new ResourceNotFoundException("ParentTask", "ParentTaskId", taskDtoDetails.getParentTask().getId().toString()));
            if (existingTask.getParentTask() != null) {
                existingTask.getParentTask().removeSubtask(existingTask);
            }
            newParentTask.addSubtask(existingTask);
        } else if (existingTask.getParentTask() != null) {
            existingTask.getParentTask().removeSubtask(existingTask);
        }

        return TaskMapper.toDTO(taskRepository.save(existingTask));
    }

    @Override
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("Task", "taskId", taskId.toString()));
        if (task.getParentTask() != null) {
            task.getParentTask().removeSubtask(task);
        }
        taskRepository.deleteById(taskId);
    }
}
