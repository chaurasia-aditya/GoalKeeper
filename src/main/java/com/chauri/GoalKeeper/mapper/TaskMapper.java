package com.chauri.GoalKeeper.mapper;

import com.chauri.GoalKeeper.dto.TaskDTO;
import com.chauri.GoalKeeper.entity.Task;
import com.chauri.GoalKeeper.utils.Priority;

import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {

    public static TaskDTO toDTO(Task task){
        return toDTO(task, true);
    }
    private static TaskDTO toDTO(Task task, boolean includeRelationships){
        if(task == null)
            return null;

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setPriority(task.getPriority().name());
        taskDTO.setCategory(CategoryMapper.toDTO(task.getCategory()));

//        if(task.getParentTask()!=null)
//            taskDTO.setParentTask(toDTO(task.getParentTask()));
//        else
//            taskDTO.setParentTask(null);

//        if (task.getParentTask() != null && !task.getParentTask().getId().equals(task.getId())) {
//            TaskDTO parentTaskDTO = new TaskDTO();
//            parentTaskDTO.setId(task.getParentTask().getId());
//            taskDTO.setParentTask(parentTaskDTO);
//        }

//        if(task.getSubtasks()!=null)
//            taskDTO.setSubtasks(toDTOList(task.getSubtasks()));
//        else
//            taskDTO.setSubtasks(null);

//        if (task.getSubtasks() != null) {
//            taskDTO.setSubtasks(task.getSubtasks().stream()
//                    .map(subtask -> {
//                        TaskDTO subtaskDTO = toDTO(subtask);
//                        // Avoid setting parentTask for subtasks to prevent recursion
//                        subtaskDTO.setParentTask(null);
//                        return subtaskDTO;
//                    }).collect(Collectors.toList()));
//        }

        if (includeRelationships) {
            if (task.getParentTask() != null)
                taskDTO.setParentTask(toDTO(task.getParentTask(), false));

            if (task.getSubtasks() != null && !task.getSubtasks().isEmpty())
                taskDTO.setSubtasks(task.getSubtasks().stream()
                        .map(subtask -> toDTO(subtask, false))
                        .collect(Collectors.toList()));
        }

        return taskDTO;
    }

    private static List<TaskDTO> toDTOList(List<Task> subtasks) {
        return subtasks.stream().map(TaskMapper::toDTO).collect(Collectors.toList());
    }

    public static Task toEntity(TaskDTO taskDto) {
        if (taskDto == null)
            return null;

        Task task = new Task();
        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setPriority(Priority.valueOf(taskDto.getPriority()));
        task.setCategory(CategoryMapper.toEntity(taskDto.getCategory()));

        if(taskDto.getParentTask() != null){
            Task parentTask = new Task();
            parentTask.setId(taskDto.getParentTask().getId());
            task.setParentTask(parentTask);
        }

        return task;
    }
}
