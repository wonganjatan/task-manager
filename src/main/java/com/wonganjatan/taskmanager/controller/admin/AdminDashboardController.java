package com.wonganjatan.taskmanager.controller.admin;

import com.wonganjatan.taskmanager.model.Task;
import com.wonganjatan.taskmanager.model.TaskForm;
import com.wonganjatan.taskmanager.model.User;
import com.wonganjatan.taskmanager.service.TaskService;
import com.wonganjatan.taskmanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public AdminDashboardController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public String dashboard(Model model) {

        long taskCount = taskService.getTaskCount();

        model.addAttribute("taskCount", taskCount);

        return "admin/dashboard";
    }
}
