package com.wonganjatan.taskmanager.controller;

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
@RequestMapping("/home")
public class HomeController {

    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public HomeController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }
    
    @GetMapping
    public String home(
            @RequestParam(name = "priority", required = false) String priority,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            Model model) {

        Collection<Task> allTasks = taskService.getAllTasks(priority, status, sortBy);
        long tasksCount = allTasks.size();

        model.addAttribute("allTasks", allTasks);
        model.addAttribute("tasksCount", tasksCount);
        model.addAttribute("selectedPriority", priority);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedSortBy", sortBy);

        return "home";
    }

    @GetMapping("/create-task")
    public String showTaskCreationForm(Model model) {

        if (!model.containsAttribute("taskForm")) {
            model.addAttribute("taskForm", new TaskForm());
        }

        Collection<User> users = userService.getAllUsers();

        model.addAttribute("pageLabel", "Create Task");
        model.addAttribute("users", users);

        return "task-form";
    }

    @PostMapping
    public String createTask(@Valid @ModelAttribute("taskForm") TaskForm form,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        if (result.hasErrors()){
            return "task-form";
        }

        Task newTask = new Task();
        newTask.setTitle(form.getTitle());
        newTask.setDescription(form.getDescription());
        newTask.setPriority(form.getPriority());
        newTask.setStatus(form.getStatus());
        newTask.setDueDate(form.getDueDate());
        if (form.getAssignedUserId() != null) {
            Optional<User> assignedUserOptional = userService.getUserById(form.getAssignedUserId());
            User assignedUser = assignedUserOptional.get();
            newTask.setAssignedUser(assignedUser);
        }

        try {
            taskService.saveTask(newTask);
            redirectAttributes.addFlashAttribute("successMessage", "Task is successfully created");
            return "redirect:/home";
        } catch (IllegalArgumentException e) {
            model.addAttribute("authError", e.getMessage());
            return "task-form";
        }
    }

    @GetMapping("/task/{id}")
    public String viewTask(@PathVariable Long id,
                           Model model,
                           RedirectAttributes redirectAttributes) {

        Optional<Task> taskOptional = taskService.getTaskById(id);

        if (taskOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Task is not found");
            return "redirect:/home";
        }

        Task task = taskOptional.get();
        model.addAttribute("task", task);

        return "task";
    }
}
