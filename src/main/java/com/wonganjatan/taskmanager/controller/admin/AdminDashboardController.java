package com.wonganjatan.taskmanager.controller.admin;

import com.wonganjatan.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private final TaskService taskService;

    @Autowired
    public AdminDashboardController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String dashboard(Model model) {

        long taskCount = taskService.getTaskCount();

        model.addAttribute("taskCount", taskCount);

        return "admin/dashboard";
    }
}
