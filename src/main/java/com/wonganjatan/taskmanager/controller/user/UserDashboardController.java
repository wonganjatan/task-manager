package com.wonganjatan.taskmanager.controller.user;

import com.wonganjatan.taskmanager.model.entity.User;
import com.wonganjatan.taskmanager.service.TaskService;
import com.wonganjatan.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserDashboardController {

    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public UserDashboardController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public String dashboard(Model model) {

        String username = "a";
        Optional<User> userOptional = userService.getUserByUsername(username);
        User user = userOptional.get();
        long taskCount = taskService.getTaskCountByAssignedUser(user.getId());

        model.addAttribute("taskCount", taskCount);

        return "user/dashboard";
    }
}
