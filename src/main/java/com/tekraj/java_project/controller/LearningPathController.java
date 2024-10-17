package com.tekraj.java_project.controller;

import com.tekraj.java_project.entity.LearningPath;
import com.tekraj.java_project.service.LearningPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/learning-paths")
public class LearningPathController {

    @Autowired
    private LearningPathService learningPathService;

    @GetMapping
    public String getAllLearningPaths(Model model) {
        model.addAttribute("learningPaths", learningPathService.getAllLearningPaths());
        return "learning/listLearningPaths";
    }

    @GetMapping("/add")
    public String createLearningPathForm(Model model) {
        model.addAttribute("learningPath", new LearningPath());
        return "learning/addLearningPath";
    }

    @PostMapping("/add")
    public String createLearningPath(@jakarta.validation.Valid @ModelAttribute("learningPath") LearningPath learningPath, BindingResult result) {
        if (result.hasErrors()) {
            return "learning/addLearningPath";
        }
        learningPathService.createLearningPath(learningPath);
        return "redirect:/learning-paths";
    }

    @GetMapping("/{id}")
    public String getLearningPathById(@PathVariable Long id, Model model) {
        learningPathService.getLearningPathById(id).ifPresent(learningPath -> model.addAttribute("learningPath", learningPath));
        return "learning/viewLearningPath";
    }

    @GetMapping("/edit/{id}")
    public String editLearningPathForm(@PathVariable Long id, Model model) {
        learningPathService.getLearningPathById(id).ifPresent(learningPath -> model.addAttribute("learningPath", learningPath));
        return "learning/editLearningPath";
    }

    @PostMapping("/edit/{id}")
    public String updateLearningPath(@PathVariable Long id, @ModelAttribute LearningPath learningPath) {
        learningPathService.updateLearningPath(id, learningPath);
        return "redirect:/learning-paths";
    }

    @GetMapping("/delete/{id}")
    public String deleteLearningPath(@PathVariable Long id) {
        learningPathService.deleteLearningPath(id);
        return "redirect:/learning-paths";
    }
}
