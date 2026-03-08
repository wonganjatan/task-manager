package com.wonganjatan.taskmanager.controller;

import com.wonganjatan.taskmanager.model.Task;
import com.wonganjatan.taskmanager.model.TaskCreation;
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
        Collection<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);

        return "dashboard";
    }

    @GetMapping("/new")
    public String taskCreationForm(Model model) {
        if (!model.containsAttribute("taskCreationForm")) {
            model.addAttribute("taskCreationForm", new TaskCreation());
        }

        return "new";
    }

    @PostMapping
    public String taskCreation(@ModelAttribute("taskCreationForm") TaskCreation form, Model model) {
        form.setCreatedAt(LocalDateTime.now());
        form.setIsComplete(false);

        Task newTask = new Task();
        newTask.setTitle(form.getTitle());
        newTask.setDescription(form.getDescription());
        newTask.setPriority(form.getPriority());
        newTask.setCreatedAt(form.getCreatedAt());
        newTask.setDueDate(form.getDueDate());
        newTask.setIsComplete(form.getIsComplete());

        if (taskService.createTask(newTask) != null) {
            System.out.println("created");
        }

        return "redirect:/dashboard";
    }

}
