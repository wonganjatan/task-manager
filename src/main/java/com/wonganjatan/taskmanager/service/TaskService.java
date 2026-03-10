package com.wonganjatan.taskmanager.service;

import com.wonganjatan.taskmanager.model.Task;

import java.util.Collection;

public interface TaskService {
    Collection<Task> getAllTasks(String priority, String status);

    void createTask(Task task);
}
