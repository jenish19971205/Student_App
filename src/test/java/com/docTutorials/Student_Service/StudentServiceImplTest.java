package com.docTutorials.Student_Service;

import com.docTutorials.Student_Service.Dto.StudentDto;
import com.docTutorials.Student_Service.Model.Student;
import com.docTutorials.Student_Service.Repository.StudentRepository;
import com.docTutorials.Student_Service.Service.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.List;
import java.util.logging.Logger;

public class StudentServiceImplTest {
    @ Mock
    private StudentRepository repository;

    private final Logger logger = Logger.getLogger(StudentServiceImplTest.class.getName());

    private StudentServiceImpl service;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new StudentServiceImpl(repository);
    }
    @Test
    void getAllStudents_returnsList() {
        Student s1 = new Student(1, "Alice", "Delhi");
        Student s2 = new Student(2, "Bob", "Mumbai");
        when(repository.findAll()).thenReturn(List.of(s1, s2));

        List<StudentDto> result = service.getAllStudents();

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getName());
        assertEquals("Mumbai", result.get(1).getCity());
        verify(repository, times(1)).findAll();
    }
    @Test
    void getStudentById_returnsStudent() {
        int studentId = 1;
        Student s = new Student(studentId, "Alice", "Delhi");
        when(repository.findById(studentId)).thenReturn(java.util.Optional.of(s));

        StudentDto result = service.getStudentById(studentId);

        assertNotNull(result);
        assertEquals("Alice", result.getName());
        assertEquals("Delhi", result.getCity());
        verify(repository, times(1)).findById(studentId);
    }
    @Test
    void getStudentById_notFound() {
        int studentId = 1;
        when(repository.findById(studentId)).thenReturn(java.util.Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.getStudentById(studentId);
        });

        assertEquals("Student not found with id: " + studentId, exception.getMessage());
        verify(repository, times(1)).findById(studentId);
    }

    @Test
    void addStudent_savesStudent() {
        StudentDto studentDto = new StudentDto(1, "Alice", "Delhi");
        service.addStudent(studentDto);
        logger. info("Adding new student: " + studentDto.getName() + ", " + studentDto.getCity());
        Student student = new Student(studentDto.getId(), studentDto.getName(), studentDto.getCity());
        when(repository.save(any(Student.class))).thenReturn(student);
        service.addStudent(studentDto);
        verify(repository, times(2)).save(any(Student.class));
    }


}
