package com.wonganjatan.taskmanager.service;

import com.wonganjatan.taskmanager.model.entity.Task;
import com.wonganjatan.taskmanager.model.entity.enums.Status;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {
    Collection<Task> getAllTasks(String priority, String status, String dueDate);
    Collection<Task> getAllTasksByAssignedUser(Long id, String priority, String status, String sortBy);
    Optional<Task> getTaskById(Long id);
    long getTotalTasks();
    long getTotalTasksByAssignedUser(Long id);

    void saveTask(Task task);
    void deleteTask(Long id);
    void updateTaskStatus(Long id, Status newStatus);
}
