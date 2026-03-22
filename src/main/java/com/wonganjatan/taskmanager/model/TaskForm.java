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
    @Size(min = 1, max = 40, message = "Description must be 1-40 characters")
    private String description;

    @NotNull(message = "Priority is required")
    private Priority priority;

    @NotNull(message = "Status is required")
    private Status status;

    @NotNull(message = "Due date is required")
    private LocalDateTime dueDate;

    private Long assignedUserId;

    public TaskForm() {}

    // Setter
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public void setStatus(Status status) { this.status = status; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    public void setAssignedUserId(Long assignedUserId) { this.assignedUserId = assignedUserId; }

    // Getter
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Priority getPriority() { return priority; }
    public Status getStatus() { return status; }
    public LocalDateTime getDueDate() { return dueDate; }
    public Long getAssignedUserId() { return assignedUserId; }
}
