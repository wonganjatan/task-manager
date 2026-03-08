package com.wonganjatan.taskmanager.controller;

import com.wonganjatan.taskmanager.model.Task;
import com.wonganjatan.taskmanager.model.TaskForm;
import com.wonganjatan.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String dashboard(Model model) {
        Collection<Task> allIncompleteTasks = taskService.getAllIncompleteTasks();
        model.addAttribute("allIncompleteTasks", allIncompleteTasks);

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
        form.setCreatedAt(LocalDateTime.now());
        form.setIsComplete(false);

        Task newTask = new Task();
        newTask.setTitle(form.getTitle());
        newTask.setDescription(form.getDescription());
        newTask.setPriority(form.getPriority());
        newTask.setCreatedAt(form.getCreatedAt());
        newTask.setDueDate(form.getDueDate());
        newTask.setIsComplete(form.getIsComplete());

        taskService.createTask(newTask);

        return "redirect:/dashboard";
    }
}
