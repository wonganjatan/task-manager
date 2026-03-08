package com.wonganjatan.taskmanager.service;

import com.wonganjatan.taskmanager.model.Task;
import com.wonganjatan.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repo;

    public TaskServiceImpl(TaskRepository repo) {
        this.repo = repo;
    }

    public Task createTask(Task task) {
        return repo.save(task);
    }

    public Collection<Task> getAllIncompleteTasks() {
        return repo.findAll().stream()
                .filter(task -> !task.getIsComplete())
                .toList();
    }
}
