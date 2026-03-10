package com.wonganjatan.taskmanager.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class TaskForm {

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 1, max = 20, message = "Title must be 1-20 characters")
    private String title;

    @NotBlank(message = "Description cannot be empty")
    @Size(min = 1, max = 500, message = "Description must be 1-500 characters")
    private String description;

    @NotBlank(message = "Priority is required")
    private Priority priority;

    @NotNull(message = "Created is required")
    private LocalDateTime createdAt;

    @NotNull(message = "Due date is required")
    private LocalDateTime dueDate;

    private boolean isComplete;

    public TaskForm() {}

    // Setter
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    public void setIsComplete(boolean isComplete) { this.isComplete = isComplete; }

    // Getter
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Priority getPriority() { return priority; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getDueDate() { return dueDate; }
    public boolean getIsComplete() { return isComplete; }
}
