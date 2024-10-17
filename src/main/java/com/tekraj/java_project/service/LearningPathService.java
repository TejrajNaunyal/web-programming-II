package com.tekraj.java_project.service;

import com.tekraj.java_project.entity.LearningPath;
import com.tekraj.java_project.repo.LearningPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LearningPathService {

    @Autowired
    private LearningPathRepository learningPathRepository;

    public List<LearningPath> getAllLearningPaths() {
        return learningPathRepository.findAll();
    }

    public Optional<LearningPath> getLearningPathById(Long id) {
        return learningPathRepository.findById(id);
    }

    public LearningPath createLearningPath(LearningPath learningPath) {
        return learningPathRepository.save(learningPath);
    }

    public void updateLearningPath(Long id, LearningPath learningPath) {
        Optional<LearningPath> existingPath = learningPathRepository.findById(id);
        existingPath.ifPresent(updatedPath -> {
            updatedPath.setTitle(learningPath.getTitle());
            updatedPath.setDescription(learningPath.getDescription());
            learningPathRepository.save(updatedPath);
        });
    }

    public void deleteLearningPath(Long id) {
        learningPathRepository.deleteById(id);
    }
}
