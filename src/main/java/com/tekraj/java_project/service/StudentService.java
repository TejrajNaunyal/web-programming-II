package com.tekraj.java_project.service;

import java.util.List;
import java.util.Optional;

import com.tekraj.java_project.entity.Student;


public interface StudentService {
    List<Student> getAllStudents();
    Optional<Student> getStudentById(Long id);
    Student updateStudent(Long id, Student student);
    Student createStudent(Student student);
    void deleteStudent(Long id);
}