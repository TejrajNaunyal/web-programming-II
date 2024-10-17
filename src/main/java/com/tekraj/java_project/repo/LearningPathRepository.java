// LearningPathRepository.java
package com.tekraj.java_project.repo;

import com.tekraj.java_project.entity.LearningPath;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningPathRepository extends JpaRepository<LearningPath, Long> {
}
