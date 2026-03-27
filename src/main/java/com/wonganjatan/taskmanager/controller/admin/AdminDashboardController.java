package com.wonganjatan.taskmanager.controller.admin;

import com.wonganjatan.taskmanager.service.JwtService;
import com.wonganjatan.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminDashboardController {

    private final JwtService jwtService;
    private final TaskService taskService;

    @Autowired
    public AdminDashboardController( JwtService jwtService, TaskService taskService) {
        this.jwtService = jwtService;
        this.taskService = taskService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String,?>> getTotalTasks(@RequestHeader("Authorization") String token) {
        String role = jwtService.getRoleFromToken(token);
        if (!role.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "You are not allowed to access this resource!"));
        }

        long totalTasks = taskService.getTotalTasks();

        return ResponseEntity.ok(Map.of("totalTasks", totalTasks));
    }
}
