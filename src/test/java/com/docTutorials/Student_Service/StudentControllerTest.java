package com.docTutorials.Student_Service;

import com.docTutorials.Student_Service.Controller.StudentController;
import com.docTutorials.Student_Service.Dto.StudentDto;
import com.docTutorials.Student_Service.Service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class StudentControllerTest {


    @Mock
    private StudentService service;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        StudentController controller = new StudentController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

   @Test
    void getAllStudents_ok() throws Exception {
        StudentDto student1 = new StudentDto(1, "Alice", "Delhi");
        StudentDto student2 = new StudentDto(2, "Bob", "Mumbai");
        when(service.getAllStudents()).thenReturn(List.of(student1, student2));

        mockMvc.perform(get("/api/students/getAllStudents")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$[0].name").value("Alice"))
                .andExpect((ResultMatcher) jsonPath("$[1].city").value("Mumbai"));
    }

    @Test
    void getStudentById_notFound() throws Exception {
        int studentId = 1;
        when(service.getStudentById(studentId)).thenReturn(null);

        mockMvc.perform(get("/api/students/getStudent/{id}", studentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getStudentById_ok() throws Exception {
        int studentId = 1;
        StudentDto student = new StudentDto(studentId, "Alice", "Delhi");
        when(service.getStudentById(studentId)).thenReturn(student);

        mockMvc.perform(get("/api/students/getStudent/{id}", studentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.name").value("Alice"))
                .andExpect((ResultMatcher) jsonPath("$.city").value("Delhi"));
    }

    @Test
    @Disabled //TODO: Need to fix the test
    void addStudent_created() throws Exception {
        StudentDto studentDto = new StudentDto(1, "Alice", "Delhi");
        doNothing().when(service).addStudent(any(StudentDto.class));

        mockMvc.perform(post("/api/students/addStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{name:Alice, city:Delhi}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Student added successfully"));
    }



}
