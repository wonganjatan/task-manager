package com.wonganjatan.taskmanager.controller.admin;

import com.wonganjatan.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminDashboardController {

    private final TaskService taskService;

    @Autowired
    public AdminDashboardController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Long>> getTasksCount() {

        long tasksCount = taskService.getTaskCount();

        return ResponseEntity.ok(Map.of("tasksCount", tasksCount));
    }
}
