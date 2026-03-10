package com.wonganjatan.taskmanager.service;

import com.wonganjatan.taskmanager.model.Task;

import java.util.Collection;

public interface TaskService {
    Collection<Task> getAllTasks();
    long getTasksCount();

    void createTask(Task task);
}
