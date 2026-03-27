package com.wonganjatan.taskmanager.controller.admin;

import com.wonganjatan.taskmanager.model.entity.Task;
import com.wonganjatan.taskmanager.model.dto.TaskForm;
import com.wonganjatan.taskmanager.model.entity.User;
import com.wonganjatan.taskmanager.service.JwtService;
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
    private final JwtService jwtService;

    @Autowired
    public AdminTasksController(TaskService taskService, UserService userService, JwtService jwtService) {
        this.taskService = taskService;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllTasks(
            @RequestHeader("Authorization") String token,
            @RequestParam(name = "priority", required = false) String priority,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "dueDate", required = false) String dueDate) {

        String role = jwtService.getRoleFromToken(token);
        if (!role.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "You are not allowed to perform this operation"));
        }

        Map<String, Object> response = new HashMap<>();
        Collection<Task> tasks = taskService.getAllTasks(priority, status, dueDate);
        long totalTasks = tasks.size();
        response.put("tasks", tasks);
        response.put("totalTasks", totalTasks);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/new")
    public ResponseEntity<Map<String, Object>> createTask(@RequestHeader("Authorization") String token,
                                                          @Valid @RequestBody TaskForm form) {

        String role = jwtService.getRoleFromToken(token);
        if (!role.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "You are not allowed to perform this operation"));
        }

        Task newTask = new Task();
        newTask.setTitle(form.getTitle());
        newTask.setDescription(form.getDescription());
        newTask.setPriority(form.getPriority());
        newTask.setStatus(form.getStatus());
        if (form.getAssignedUserId() != null) {
            Optional<User> assignedUserOptional = userService.getUserById(form.getAssignedUserId());
            User assignedUser = assignedUserOptional.get();
            newTask.setAssignedUser(assignedUser);
        } else {
            newTask.setAssignedUser(null);
        }
        newTask.setDueDate(form.getDueDate());

        taskService.saveTask(newTask);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Task created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@RequestHeader("Authorization") String token,
            @PathVariable Long id) {

        String role = jwtService.getRoleFromToken(token);
        if (!role.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "You are not allowed to access this resources"));
        }
        Optional<Task> taskOptional = taskService.getTaskById(id);
        if (taskOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Task task = taskOptional.get();

        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> editTask(@RequestHeader("Authorization") String token,
                                      @PathVariable Long id,
                                      @Valid @RequestBody TaskForm form) {

        String role = jwtService.getRoleFromToken(token);
        if (!role.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "You are not allowed to access this resources"));
        }
        Optional<Task> taskOptional = taskService.getTaskById(id);
        if (taskOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Task not found"));
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

        taskService.saveTask(task);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "Task updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@RequestHeader("Authorization") String token,
                                                         @PathVariable Long id) {

        String role = jwtService.getRoleFromToken(token);
        if (!role.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "You are not allowed to access this resources"));
        }

        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }
}
