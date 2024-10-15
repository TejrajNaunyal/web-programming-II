package com.tekraj.java_project.service.impl;

import com.tekraj.java_project.entity.Student;
import com.tekraj.java_project.repo.StudentRepository;
import com.tekraj.java_project.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

   @Override
public Student updateStudent(Long id, Student student) {
    Optional<Student> existingStudent = studentRepository.findById(id);
    if (existingStudent.isPresent()) {
        Student updatedStudent = existingStudent.get();
        updatedStudent.setName(student.getName());
        updatedStudent.setEmail(student.getEmail());
        updatedStudent.setAge(student.getAge());
        return studentRepository.save(updatedStudent);
    } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
    }
}

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
