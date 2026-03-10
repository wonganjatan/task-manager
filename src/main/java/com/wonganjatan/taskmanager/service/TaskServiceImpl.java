package com.wonganjatan.taskmanager.service;

import com.wonganjatan.taskmanager.model.Task;
import com.wonganjatan.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Collection<Task> getAllTasks() {
        return taskRepository.findAll().stream()
                .toList();
    }

    @Override
    public long getTasksCount() {
        return taskRepository.count();
    }

    @Override
    public void createTask(Task task) { taskRepository.save(task); }
}
