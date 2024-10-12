package com.tekraj.java_project;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.tekraj.java_project.entity.Student;
import com.tekraj.java_project.repo.StudentRepository;
import com.tekraj.java_project.service.impl.StudentServiceImpl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StudentServiceUnitTest {

    @Mock
    private StudentRepository studentRepo;

    @InjectMocks
    private StudentServiceImpl studentServiceImpl;

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        List<Student> studentList = new ArrayList<>();
        Student student = new Student();
        student.setName("John Doe");
        studentList.add(student);

        Mockito.when(studentRepo.save(any(Student.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(studentRepo.findAll()).thenReturn(studentList);
        Mockito.when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        Mockito.when(studentRepo.findById(99L)).thenReturn(Optional.empty()); // for failure case
        Mockito.doNothing().when(studentRepo).deleteById(any());
    }

    @Test
    void testCreateStudentSuccess() {
        Student student = new Student();
        student.setName("Jane Doe");
        student.setEmail("History 101");
        Student createdStudent = studentServiceImpl.createStudent(student);
        assertEquals("Jane Doe", createdStudent.getName());
        assertThatNoException();
    }

    @Test
    void testCreateStudentFailureValidation() {
        Student student = new Student();
        student.setName(null); 

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertFalse(violations.isEmpty());
    }
    @Test
    void testGetAllStudentsSuccess() {
        List<Student> studentList = studentServiceImpl.getAllStudents();

        assertEquals(1, studentList.size());
        assertEquals("John Doe", studentList.get(0).getName());
    }

    @Test
    void testGetAllStudentsEmptyList() {
        Mockito.when(studentRepo.findAll()).thenReturn(new ArrayList<>());
        List<Student> studentList = studentServiceImpl.getAllStudents();
        assertTrue(studentList.isEmpty());
    }

    @Test
    void testGetStudentByIdSuccess() {
        Optional<Student> student = studentServiceImpl.getStudentById(1L);
        assertTrue(student.isPresent());
        assertEquals("John Doe", student.get().getName());
    }
    @Test
    void testGetStudentByIdFailureNotFound() {
        Optional<Student> student = studentServiceImpl.getStudentById(99L);
        assertTrue(student.isEmpty());
    }
    @Test
    void testUpdateStudentSuccess() {
        Student student = new Student();
        student.setName("Updated Name");
        student.setEmail("Updated Email");
        Student updatedStudent = studentServiceImpl.updateStudent(1L, student);
        assertEquals("Updated Name", updatedStudent.getName());
    }
    @Test
    void testUpdateStudentFailureInvalidData() {
        Student student = new Student();
        student.setName(null); 
        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        assertFalse(violations.isEmpty());
    }
    @Test
    void testDeleteStudentSuccess() {
        studentServiceImpl.deleteStudent(1L);
        assertThatNoException();
    }
    @Test
    void testDeleteStudentFailureStudentNotFound() {
        Mockito.doThrow(new RuntimeException("Student not found")).when(studentRepo).deleteById(99L);
        assertThrows(RuntimeException.class, () -> studentServiceImpl.deleteStudent(99L));
    }
}