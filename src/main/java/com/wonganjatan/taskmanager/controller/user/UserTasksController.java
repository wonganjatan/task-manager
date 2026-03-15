package com.wonganjatan.taskmanager.controller.user;

import com.wonganjatan.taskmanager.model.Status;
import com.wonganjatan.taskmanager.model.Task;
import com.wonganjatan.taskmanager.model.TaskForm;
import com.wonganjatan.taskmanager.model.User;
import com.wonganjatan.taskmanager.service.TaskService;
import com.wonganjatan.taskmanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping("/user/tasks")
public class UserTasksController {

    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public UserTasksController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public String taskList(
            Authentication authentication,
            @RequestParam(name = "priority", required = false) String priority,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            Model model) {

        String username = authentication.getName();
        Optional<User> userOptional = userService.getUserByUsername(username);
        User user = userOptional.get();

        Collection<Task> myTasks = taskService.getAllTasksByAssignedUser(user.getId(), priority, status, sortBy);
        long tasksCount = myTasks.size();

        model.addAttribute("myTasks", myTasks);
        model.addAttribute("tasksCount", tasksCount);
        model.addAttribute("selectedPriority", priority);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedSortBy", sortBy);

        return "user/tasks/list";
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
                                   Authentication authentication,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {

        String username = authentication.getName();
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
