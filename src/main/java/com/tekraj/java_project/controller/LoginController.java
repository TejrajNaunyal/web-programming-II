package com.tekraj.java_project.controller;

import com.tekraj.java_project.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    private List<User> userList = new ArrayList<>(); // List to store registered users

    // Hardcoded for initial login
    private final String defaultEmail = "";
    private final String defaultPassword = "";

    public LoginController() {
        // Add a default user to the list
        User defaultUser = new User(defaultEmail, defaultPassword);
        userList.add(defaultUser);
    }

    // Show login form
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    // Handle login
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model) {
        // Validate login
        boolean isValidUser = userList.stream()
                .anyMatch(user -> user.getEmail().equals(email) && user.getPassword().equals(password));

        if (isValidUser) {
            return "redirect:/home"; // Login successful, redirect to homepage
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    // Show registration form
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Return the register page
    }

    // Handle new user registration
    @PostMapping("/register")
    public String registerUser(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               Model model) {
        // Check if email already exists
        boolean emailExists = userList.stream()
                .anyMatch(user -> user.getEmail().equals(email));

        if (emailExists) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }

        // Register new user
        User newUser = new User(email, password);
        userList.add(newUser);

        return "redirect:/login"; // After successful registration, redirect to login page
    }

    // Show home page after successful login
    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }
}
