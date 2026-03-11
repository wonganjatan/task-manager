package com.wonganjatan.taskmanager.service;

import com.wonganjatan.taskmanager.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {
    Collection<Task> getAllTasks(String priority, String status);
    Optional<Task> getTaskById(Long id);

    void createTask(Task task);
}
