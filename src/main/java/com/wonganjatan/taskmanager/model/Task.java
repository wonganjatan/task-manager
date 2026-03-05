package com.wonganjatan.taskmanager.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String priority;
    private LocalDate createdAt;
    private boolean isComplete;

    public Task() {}

    public Task(
            String )
}
