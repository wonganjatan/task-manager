package com.wonganjatan.taskmanager.controller.admin;

import com.wonganjatan.taskmanager.model.Task;
import com.wonganjatan.taskmanager.model.TaskForm;
import com.wonganjatan.taskmanager.model.User;
import com.wonganjatan.taskmanager.service.TaskService;
import com.wonganjatan.taskmanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/admin/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminTasksController {

    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public AdminTasksController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllTasks(
            @RequestParam(name = "priority", required = false) String priority,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "dueDate", required = false) String dueDate) {

        Map<String, Object> response = new HashMap<>();
        Collection<Task> tasks = taskService.getAllTasks(priority, status, dueDate);
        long totalTasks = tasks.size();
        response.put("tasks", tasks);
        response.put("totalTasks", totalTasks);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/new")
    public ResponseEntity<Map<String, String>> createTask(@Valid @RequestBody TaskForm form) {

        Task newTask = new Task();
        newTask.setTitle(form.getTitle());
        newTask.setDescription(form.getDescription());
        newTask.setPriority(form.getPriority());
        newTask.setStatus(form.getStatus());
        System.out.println("Form: " + form.getAssignedUserId());
        if (form.getAssignedUserId() != null) {
            Optional<User> assignedUserOptional = userService.getUserById(form.getAssignedUserId());
            User assignedUser = assignedUserOptional.get();
            newTask.setAssignedUser(assignedUser);
        } else {
            newTask.setAssignedUser(null);
        }
        newTask.setDueDate(form.getDueDate());

        System.out.println(newTask.getAssignedUser());

        try {
            taskService.saveTask(newTask);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Task created successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {

        Optional<Task> taskOptional = taskService.getTaskById(id);

        if (taskOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Task task = taskOptional.get();

        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> editTask(@PathVariable Long id,
                           @Valid @RequestBody TaskForm form) {

        Optional<Task> taskOptional = taskService.getTaskById(id);
        if (taskOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Task task = taskOptional.get();

        task.setTitle(form.getTitle());
        task.setDescription(form.getDescription());
        task.setPriority(form.getPriority());
        task.setStatus(form.getStatus());
        task.setDueDate(form.getDueDate());
        if (form.getAssignedUserId() != null) {
            Optional<User> assignedUserOptional = userService.getUserById(form.getAssignedUserId());
            User assignedUser = assignedUserOptional.get();
            task.setAssignedUser(assignedUser);
        } else {
            task.setAssignedUser(null);
        }

        try {
            taskService.saveTask(task);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("message", "Task edited successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {

        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
