package com.wonganjatan.taskmanager.controller;

import com.wonganjatan.taskmanager.model.Status;
import com.wonganjatan.taskmanager.model.Task;
import com.wonganjatan.taskmanager.model.TaskForm;
import com.wonganjatan.taskmanager.model.User;
import com.wonganjatan.taskmanager.service.TaskService;
import jakarta.servlet.http.HttpSession;
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

    @Autowired
    public HomeController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @GetMapping
    public String home(
            @RequestParam(name = "priority", required = false) String priority,
            @RequestParam(name = "status", required = false) String status,
            Model model,
            HttpSession session) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", loggedInUser);

        Collection<Task> allTasks = taskService.getAllTasks(priority, status);
        long tasksCount = allTasks.size();

        model.addAttribute("allTasks", allTasks);
        model.addAttribute("tasksCount", tasksCount);
        model.addAttribute("selectedPriority", priority);
        model.addAttribute("selectedStatus", status);

        return "home";
    }

    @GetMapping("/create-task")
    public String showTaskCreationForm(Model model,
                                       HttpSession session) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        if (!model.containsAttribute("taskCreationForm")) {
            model.addAttribute("taskCreationForm", new TaskForm());
        }

        return "create-task";
    }

    @PostMapping
    public String createTask(@Valid @ModelAttribute("taskCreationForm") TaskForm form,
                             BindingResult result,
                             Model model) {

        if (result.hasErrors()){
            return "create-task";
        }

        Task newTask = new Task();
        newTask.setTitle(form.getTitle());
        newTask.setDescription(form.getDescription());
        newTask.setPriority(form.getPriority());
        newTask.setDueDate(form.getDueDate());
        newTask.setStatus(Status.TODO);

        try {
            taskService.createTask(newTask);
            return "redirect:/home";
        } catch (IllegalArgumentException e) {
            model.addAttribute("authError", e.getMessage());
            return "create-task";
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
