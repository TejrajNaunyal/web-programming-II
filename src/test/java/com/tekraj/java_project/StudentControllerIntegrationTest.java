package com.tekraj.java_project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.tekraj.java_project.entity.Student;
import com.tekraj.java_project.repo.StudentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JavaProjectApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class StudentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepo;

    @BeforeEach
    public void setUp() {
        Student student = new Student();
        student.setName("John Doe");
        student.setEmail("johndoe@example.com");
        studentRepo.save(student);
    }

    @AfterEach
    public void tearDown() {
        studentRepo.deleteAll();
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testGetStudentByIdSuccess() throws Exception {
        Student student = studentRepo.findAll().get(0);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/students/" + student.getId()))
                .andReturn();

        assertEquals(200, result.getResponse().getStatus());
        Student fetchedStudent = (Student) result.getModelAndView().getModel().get("student");
        assertNotNull(fetchedStudent);
        assertEquals("John Doe", fetchedStudent.getName());
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testGetStudentByIdFailureNotFound() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/students/99"))
                .andReturn();

        assertEquals(404, result.getResponse().getStatus());

        String errorMessage = result.getResponse().getErrorMessage();
        assertEquals("Student not found", errorMessage);
    }
}
