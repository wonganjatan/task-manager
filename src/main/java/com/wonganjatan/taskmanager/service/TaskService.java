package com.wonganjatan.taskmanager.service;

import com.wonganjatan.taskmanager.model.Task;

import java.util.Collection;

public interface TaskService {
    Task createTask(Task task);

    Collection<Task> getAllTasks();
}
