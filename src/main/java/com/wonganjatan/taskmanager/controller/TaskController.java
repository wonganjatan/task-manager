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
@RequestMapping("/home/task")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/edit/{id}")
    public String showEditTaskForm(@PathVariable Long id,
                           Model model) {

        Collection<User> users = userService.getAllUsers();
        Optional<Task> taskOptional = taskService.getTaskById(id);
        Task task = taskOptional.get();

        TaskForm taskForm = new TaskForm();
        taskForm.setTitle(task.getTitle());
        taskForm.setDescription(task.getDescription());
        taskForm.setPriority(task.getPriority());
        taskForm.setStatus(task.getStatus());
        taskForm.setDueDate(task.getDueDate());

        model.addAttribute("taskForm", taskForm);
        model.addAttribute("taskId", id);
        model.addAttribute("pageLabel", "Edit Task");
        model.addAttribute("users", users);


        return "task-form";
    }

    @PostMapping("/edit/{id}")
    public String editTask(@PathVariable Long id,
                           @Valid @ModelAttribute("taskForm") TaskForm form,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes) {

        if (result.hasErrors()){
            return "task-form";
        }

        Optional<Task> taskOptional = taskService.getTaskById(id);
        Task task = taskOptional.get();

        task.setTitle(form.getTitle());
        task.setDescription(form.getDescription());
        task.setPriority(form.getPriority());
        task.setStatus(form.getStatus());
        task.setDueDate(form.getDueDate());
        if (form.getAssignedUserId() != null) {
            Optional<User> assignedUserOptional = userService.getUserById(form.getAssignedUserId());
            User assignedUser = assignedUserOptional.get();
            task.setAssignedUser(assignedUser);
        } else {
            task.setAssignedUser(null);
        }

        try {
            taskService.saveTask(task);
            redirectAttributes.addFlashAttribute("successMessage", "Task is successfully edited");
            return "redirect:/home";
        } catch (IllegalArgumentException e) {
            model.addAttribute("authError", e.getMessage());
            return "task-form";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id,
                             RedirectAttributes redirectAttributes) {

        try {
            taskService.deleteTask(id);
            redirectAttributes.addFlashAttribute("successMessage", "Task is successfully deleted");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/home";
    }
}
