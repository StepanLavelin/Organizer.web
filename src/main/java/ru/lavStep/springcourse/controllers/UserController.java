package ru.lavStep.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lavStep.springcourse.entites.User;
import ru.lavStep.springcourse.entityManagers.TaskManager;
import ru.lavStep.springcourse.entityManagers.UserManager;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserManager userManager;
    private final TaskManager taskManager;

    @Autowired
    public UserController(UserManager userManager, TaskManager taskManager) {
        this.userManager = userManager;
        this.taskManager = taskManager;
    }

    @GetMapping("")
    public String mainPage(Model model){
        model.addAttribute("users", userManager.getAllUsers());
        return "mainPage";
    }

    @GetMapping("/{id}")
    public String userPage(Model model, @PathVariable Long id){
        model.addAttribute("user", userManager.getUser(id));
        model.addAttribute("tasks", taskManager.getAllUserTasks(id));
        return "/users/user";
    }

    @PostMapping("/{id}")
    public String updateUser(User user, @PathVariable long id){
        userManager.updateUser(id, user);
        System.out.println(user.getFirstName() + " " + user.getLastName());
        return "redirect:/users/" + id;
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable long id){
        userManager.deleteUser(id);
        return "redirect:/first";
    }

    @GetMapping("/new")
    public String pageNewUser(){
        return "users/newUser";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user){
        userManager.createUser(user);
        return "redirect:/first";
    }
}
