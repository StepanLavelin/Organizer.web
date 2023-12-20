package ru.lavStep.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lavStep.springcourse.entityManagers.TaskManager;
import ru.lavStep.springcourse.entityManagers.UserManager;
import ru.lavStep.springcourse.entites.User;


@Controller
@RequestMapping("/first")
public class MainController {

    private final UserManager userManager;
    private final TaskManager taskManager;

    @Autowired
    public MainController(UserManager userManager, TaskManager taskManager) {
        this.userManager = userManager;
        this.taskManager = taskManager;
    }

    @GetMapping("")
    public String helloPage(Model model) {
        model.addAttribute("users", userManager.getAllUsers());
        model.addAttribute("tasks", taskManager.getAllTasks());
        return "mainPage";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "users/newUser";
    }
}
