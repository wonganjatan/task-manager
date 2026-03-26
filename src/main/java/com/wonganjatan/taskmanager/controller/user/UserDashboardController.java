package com.wonganjatan.taskmanager.controller.user;

import com.wonganjatan.taskmanager.model.entity.User;
import com.wonganjatan.taskmanager.service.JwtService;
import com.wonganjatan.taskmanager.service.TaskService;
import com.wonganjatan.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
public class UserDashboardController {

    private final TaskService taskService;
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public UserDashboardController(TaskService taskService, UserService userService, JwtService jwtService) {
        this.taskService = taskService;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> dashboard(@RequestHeader("Authorization") String token) {

        try {
            String username = jwtService.getUsernameFromToken(token);
            Optional<User> userOptional = userService.getUserByUsername(username);
            User user = userOptional.get();
            long totalTasks = taskService.getTotalTasksByAssignedUser(user.getId());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("totalTasks", totalTasks));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
