package com.wonganjatan.taskmanager.service;

import com.wonganjatan.taskmanager.model.Task;
import com.wonganjatan.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Collection<Task> getAllTasks(String priority, String status, String sortBy) {
        Stream<Task> stream = taskRepository.findAll().stream()
                .filter(task -> priority == null || priority.isEmpty() || task.getPriority().name().equals(priority))
                .filter(task -> status == null || status.isEmpty() || task.getStatus().name().equals(status));

        if (sortBy != null) {
            if (sortBy.equals("dueDateAsc")) {
                stream = stream.sorted(Comparator.comparing(Task::getDueDate));
            } else if (sortBy.equals("dueDateDesc")) {
                stream = stream.sorted(Comparator.comparing(Task::getDueDate).reversed());
            }
        }

        return stream.toList();
    }

    @Override
    public long getTaskCount() {
        return taskRepository.count();
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public void saveTask(Task task) {
        if (task.getDueDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Due date cannot be in the past");
        }

        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Task is not deleted");
        }

        taskRepository.deleteById(id);
    }
}
