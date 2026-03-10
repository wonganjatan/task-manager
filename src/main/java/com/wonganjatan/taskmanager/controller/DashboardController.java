package com.wonganjatan.taskmanager.controller;

import com.wonganjatan.taskmanager.model.Status;
import com.wonganjatan.taskmanager.model.Task;
import com.wonganjatan.taskmanager.model.TaskForm;
import com.wonganjatan.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final TaskService taskService;

    @Autowired
    public DashboardController(TaskService taskService) {
        this.taskService = taskService;
    }

    
    @GetMapping
    public String dashboard(
            @RequestParam(name = "priority", required = false) String priority,
            @RequestParam(name = "status", required = false) String status,
            Model model) {
        Collection<Task> allTasks = taskService.getAllTasks(priority, status);
        long tasksCount = allTasks.size();

        model.addAttribute("allTasks", allTasks);
        model.addAttribute("tasksCount", tasksCount);
        model.addAttribute("selectedPriority", priority);
        model.addAttribute("selectedStatus", status);

        return "dashboard";
    }

    @GetMapping("/create-task")
    public String showTaskCreationForm(Model model) {
        if (!model.containsAttribute("taskCreationForm")) {
            model.addAttribute("taskCreationForm", new TaskForm());
        }

        return "create-task";
    }

    @PostMapping
    public String createTask(@ModelAttribute("taskCreationForm") TaskForm form) {

        Task newTask = new Task();
        newTask.setTitle(form.getTitle());
        newTask.setDescription(form.getDescription());
        newTask.setPriority(form.getPriority());
        newTask.setDueDate(form.getDueDate());
        newTask.setStatus(Status.TODO);

        taskService.createTask(newTask);

        return "redirect:/dashboard";
    }
}
