package com.wonganjatan.taskmanager.controller.admin;

import com.wonganjatan.taskmanager.model.response.AdminDashboardResponse;
import com.wonganjatan.taskmanager.service.JwtService;
import com.wonganjatan.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<AdminDashboardResponse> getTotalTasks(@RequestHeader("Authorization") String token) {
        try {
            String role = jwtService.getRoleFromToken(token);
            if (!role.equals("ADMIN")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new AdminDashboardResponse(
                                "You are not allowed to access this resource"));
            }

            long totalTasks = taskService.getTotalTasks();

            return ResponseEntity.ok(new AdminDashboardResponse(totalTasks));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AdminDashboardResponse(e.getMessage()));
        }
    }
}
