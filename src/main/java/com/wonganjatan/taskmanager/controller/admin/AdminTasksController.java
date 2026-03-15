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
@RequestMapping("/admin/tasks")
public class AdminTasksController {

    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public AdminTasksController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public String taskList(
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

        return "admin/tasks/list";
    }

    @GetMapping("/new")
    public String showTaskCreationForm(Model model) {

        if (!model.containsAttribute("taskForm")) {
            model.addAttribute("taskForm", new TaskForm());
        }

        Collection<User> users = userService.getAllUsers();

        model.addAttribute("pageTitle", "Create Task");
        model.addAttribute("users", users);
        model.addAttribute("formSubmitButtonLabel", "Create Task");

        return "admin/tasks/form";
    }

    @PostMapping
    public String createTask(@Valid @ModelAttribute("taskForm") TaskForm form,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        if (result.hasErrors()){
            return "admin/tasks/form";
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
        } else {
            newTask.setAssignedUser(null);
        }

        try {
            taskService.saveTask(newTask);
            redirectAttributes.addFlashAttribute("successMessage", "Task is successfully created");
            return "redirect:/admin/tasks";
        } catch (IllegalArgumentException e) {
            model.addAttribute("dateTimeError", e.getMessage());
            return "admin/tasks/form";
        }
    }

    @GetMapping("/{id}")
    public String viewTask(@PathVariable Long id,
                           Model model,
                           RedirectAttributes redirectAttributes) {

        Optional<Task> taskOptional = taskService.getTaskById(id);

        if (taskOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Task is not found");
            return "redirect:/admin/tasks";
        }

        Task task = taskOptional.get();
        model.addAttribute("task", task);

        return "admin/tasks/view";
    }

    @GetMapping("/{id}/edit")
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
        model.addAttribute("pageTitle", "Edit Task");
        model.addAttribute("users", users);
        model.addAttribute("formSubmitButtonLabel", "Edit Task");


        return "admin/tasks/form";
    }

    @PostMapping("/{id}/edit")
    public String editTask(@PathVariable Long id,
                           @Valid @ModelAttribute("taskForm") TaskForm form,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes) {

        if (result.hasErrors()){
            return "admin/tasks/form";
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
            return "redirect:/admin/tasks";
        } catch (IllegalArgumentException e) {
            model.addAttribute("dateTimeError", e.getMessage());
            return "admin/tasks/form";
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

        return "redirect:/admin/tasks";
    }
}
