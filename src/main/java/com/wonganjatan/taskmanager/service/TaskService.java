package com.wonganjatan.taskmanager.service;

import com.wonganjatan.taskmanager.model.Task;
import com.wonganjatan.taskmanager.model.User;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {
    Collection<Task> getAllTasks(String priority, String status, String dueDate);
    Collection<Task> getAllTasksByAssignedUser(Long id, String priority, String status, String sortBy);
    Optional<Task> getTaskById(Long id);
    long getTaskCount();
    long getTaskCountByAssignedUser(Long id);

    void saveTask(Task task);
    void deleteTask(Long id);
}
