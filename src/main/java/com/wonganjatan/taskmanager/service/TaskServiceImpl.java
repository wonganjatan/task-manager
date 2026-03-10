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
    public Collection<Task> getAllIncompleteTasks() {
        return taskRepository.findAll().stream()
                .filter(task -> !task.getIsComplete())
                .toList();
    }

    @Override
    public long getIncompleteTasksCount() {
        return taskRepository.countByIsCompleteFalse();
    }

    @Override
    public Collection<Task> getAllCompletedTasks() {
        return taskRepository.findAll().stream()
                .filter(Task::getIsComplete)
                .toList();
    }

    @Override
    public long getCompletedTasksCount() {
        return taskRepository.countByIsCompleteTrue();
    }

    @Override
    public void createTask(Task task) { taskRepository.save(task); }
}
