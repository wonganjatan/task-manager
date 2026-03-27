package com.wonganjatan.taskmanager.controller.user;

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
            @RequestHeader("Authorization")  String token,
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
    public String viewTask(@PathVariable Long id,
                           Model model,
                           RedirectAttributes redirectAttributes) {

        Optional<Task> taskOptional = taskService.getTaskById(id);

        if (taskOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Task is not found");
            return "redirect:/user/tasks";
        }

        Task task = taskOptional.get();
        model.addAttribute("task", task);

        return "user/tasks/view";
    }

    @PostMapping("/{id}/status")
    public String updateTaskStatus(@PathVariable Long id,
                                   @RequestParam Status status,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {

        String username = "d";
        Optional<User> userOptional = userService.getUserByUsername(username);
        User user = userOptional.get();

        Optional<Task> taskOptional = taskService.getTaskById(id);

        if (taskOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Task not Found");
            return "redirect:/user/tasks";
        }

        Task task = taskOptional.get();

        if (!task.getAssignedUser().getId().equals(user.getId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Unauthorized action");
            return "redirect:/user/tasks";
        }

        model.addAttribute("task", task);

        task.setStatus(status);
        taskService.saveTask(task);

        redirectAttributes.addFlashAttribute("successMessage", "Task is successfully updated");

        return "redirect:/user/tasks/" + id;
    }
}
