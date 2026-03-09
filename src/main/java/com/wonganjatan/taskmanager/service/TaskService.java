package com.wonganjatan.taskmanager.service;

import com.wonganjatan.taskmanager.model.Task;

import java.util.Collection;

public interface TaskService {
    Collection<Task> getAllIncompleteTasks();
    long getIncompleteTasksCount();

    Collection<Task> getAllCompletedTasks();
    long getCompletedTasksCount();

    void createTask(Task task);
}
