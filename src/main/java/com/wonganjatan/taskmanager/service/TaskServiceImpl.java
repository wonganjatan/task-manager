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
    public Collection<Task> getAllTasks(String priority, String status) {
        return taskRepository.findAll().stream()
                .filter(task -> priority == null || priority.isEmpty() || task.getPriority().name().equals(priority))
                .filter(task -> status == null || status.isEmpty() || task.getStatus().name().equals(status))
                .toList();
    }

    @Override
    public void createTask(Task task) { taskRepository.save(task); }
}
