package com.chauri.GoalKeeper.entity;

import com.chauri.GoalKeeper.exception.IllegalOperationException;
import com.chauri.GoalKeeper.utils.Priority;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter @Setter @ToString
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "parent_task_id")
    private Task parentTask;

    @OneToMany(mappedBy = "parentTask", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> subtasks = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    //Helper Methods
    public void addSubtask(Task subtask) {
        if (this.parentTask != null) {
            throw new IllegalOperationException("Cannot add subtask to a task that is already a subtask.");
        }
        if (subtask.getParentTask() != null && !Objects.equals(subtask.getParentTask().getId(), this.id)) {
            throw new IllegalOperationException("The subtask already has a parent task.");
        }
        this.subtasks.add(subtask);
        subtask.setParentTask(this);
    }

    public void removeSubtask(Task subtask) {
        this.subtasks.remove(subtask);
        subtask.setParentTask(null);
    }

    public void setParentTask(Task parentTask) {
        if (parentTask != null && !this.subtasks.isEmpty()) {
            throw new IllegalOperationException("Cannot set parent task for a task that already has subtasks.");
        }
        this.parentTask = parentTask;
    }
}
