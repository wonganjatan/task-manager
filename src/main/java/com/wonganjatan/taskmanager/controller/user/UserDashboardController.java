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
@RequestMapping("/api/user/dashboard")
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

        String username = jwtService.getUsernameFromToken(token);
        Optional<User> userOptional = userService.getUserByUsername(username);
        User user = userOptional.get();
        long totalTasks = taskService.getTotalTasksByAssignedUser(user.getId());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("totalTasks", totalTasks));
    }
}
