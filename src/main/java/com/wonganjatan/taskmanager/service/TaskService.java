package com.wonganjatan.taskmanager.service;

import com.wonganjatan.taskmanager.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {
    Collection<Task> getAllTasks(String priority, String status, String sortBy);
    Optional<Task> getTaskById(Long id);
    long getTaskCount();

    void saveTask(Task task);
    void deleteTask(Long id);
}
