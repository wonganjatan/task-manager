package com.wonganjatan.taskmanager.controller.user;

import com.wonganjatan.taskmanager.exception.TaskNotFound;
import com.wonganjatan.taskmanager.model.dto.UpdateStatus;
import com.wonganjatan.taskmanager.model.entity.enums.Status;
import com.wonganjatan.taskmanager.model.entity.Task;
import com.wonganjatan.taskmanager.model.entity.User;
import com.wonganjatan.taskmanager.service.JwtService;
import com.wonganjatan.taskmanager.service.TaskService;
import com.wonganjatan.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class UserTasksController {

    private final TaskService taskService;
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public UserTasksController(TaskService taskService, UserService userService, JwtService jwtService) {
        this.taskService = taskService;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> taskList(
            @RequestHeader("Authorization") String token,
            @RequestParam(name = "priority", required = false) String priority,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "sortBy", required = false) String sortBy
            ) {

        String username = jwtService.getUsernameFromToken(token);
        Optional<User> userOptional = userService.getUserByUsername(username);
        User user = userOptional.get();

        Collection<Task> myTasks = taskService.getAllTasksByAssignedUser(user.getId(), priority, status, sortBy);
        long totalTasks = myTasks.size();

        Map<String, Object> response = new HashMap<>();
        response.put("totalTasks", totalTasks);
        response.put("myTasks", myTasks);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> viewTask(@RequestHeader("Authorization") String token,
                           @PathVariable Long id) {

        Optional<Task> taskOptional = taskService.getTaskById(id);

        if (taskOptional.isEmpty()) {
            throw new TaskNotFound("Task not found");
        }

        Task task = taskOptional.get();

        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Object> updateTaskStatus(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestBody UpdateStatus newStatus) {

        String username = jwtService.getUsernameFromToken(token);
        Optional<User> userOptional = userService.getUserByUsername(username);
        User user = userOptional.get();

        Optional<Task> taskOptional = taskService.getTaskById(id);
        if (taskOptional.isEmpty()) {
            throw new TaskNotFound("Task not found");
        }

        Task task = taskOptional.get();

        if (task.getAssignedUser().getId().equals(user.getId())) {
            taskService.updateTaskStatus(id, newStatus.getStatus());
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Forbidden to perform this operation"));
        }
    }
}
