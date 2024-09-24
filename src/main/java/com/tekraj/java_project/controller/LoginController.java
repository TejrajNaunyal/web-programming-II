package com.tekraj.java_project.controller;

import com.tekraj.java_project.entity.User;
import com.tekraj.java_project.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Show login form  
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    // Handle login
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model) {
        User user = userRepository.findByUserName(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return "redirect:/home"; // Login successful
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    // Show registration form
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Handle new user registration
    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model) {
        if (userRepository.findByUserName(username) != null) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
        User newUser = new User();
        newUser.setUserName(username);
        newUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(newUser);
        return "redirect:/login";
    }

    // Show home page
    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }
}