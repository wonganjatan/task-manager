package com.wonganjatan.taskmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 3, max = 20, message = "Title must be 3-20 characters")
    private String title;

    @NotBlank
    @Size(max = 500, message = "Desciption cannot exceeed 500 characters")
    private String description;

    private String priority;
    private LocalDateTime createdAt;

    @NotNull(message = "Due date is required")
    private LocalDateTime dueDate;
    private boolean isComplete;

    public Task() {}

    public Task(
            String title, 
            String description, 
            String priority, 
            LocalDateTime createdAt,
            LocalDateTime dueDate, 
            boolean isComplete) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
        this.isComplete = isComplete;
    }

    // Setter
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setPriority(String priority) { this.priority = priority; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    public void setIsComplete(boolean isComplete) { this.isComplete = isComplete; }

    // Getter
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getDueDate() { return dueDate; }
    public boolean getIsComplete() { return isComplete; }
}
