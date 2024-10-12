package com.tekraj.java_project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tekraj.java_project.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}