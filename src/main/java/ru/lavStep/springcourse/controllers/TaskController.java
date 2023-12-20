package ru.lavStep.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lavStep.springcourse.entites.Task;
import ru.lavStep.springcourse.entityManagers.TaskManager;
import ru.lavStep.springcourse.entityManagers.UserManager;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskManager taskManager;
    private final UserManager userManager;

    @Autowired
    public TaskController(TaskManager taskManager, UserManager userManager){
        this.taskManager = taskManager;
        this.userManager = userManager;
    }

    @GetMapping("")
    public String mainPage(Model model){
        model.addAttribute("tasks", taskManager.getAllTasks());
        return "redirect:/first";
    }

    @GetMapping("tasksListByUser/{id}")
    public String getTasksList(Model model, @PathVariable Long userId){
        model.addAttribute("tasks", taskManager.getAllUserTasks(userId));
        return "newTask";
    }

    @GetMapping("/{id}")
    public String taskPage(Model model, @PathVariable Long id){
        Task task = taskManager.getTask(id);
        String performer ="";
        if (userManager.getUser(task.getPerformer()) != null) {
            performer = userManager.getUser(task.getPerformer()).getFirstName() + " " +
                    userManager.getUser(task.getPerformer()).getLastName();
        }
        model.addAttribute("task", task);
        model.addAttribute("users", userManager.getAllUsers());
        model.addAttribute("performer", performer);
        return "tasks/task";
    }

    @GetMapping("/new")
    public String pageNewTask(Model model){
        model.addAttribute("users", userManager.getAllUsers());
        return "tasks/newTask";
    }

    @PostMapping()
    public String createTask(@ModelAttribute("task") Task task){
        taskManager.createTask(task);
        return "redirect:/tasks/";
    }

    @PostMapping("/{id}")
    public String updateTask(Task task, @PathVariable long id){
        taskManager.updateTask(id, task);
        return "redirect:/tasks/" + id;
    }

    @GetMapping("/{id}/delete")
    public String deleteTask(@PathVariable long id){
        taskManager.deleteTask(id);
        return "redirect:/first";
    }
}
