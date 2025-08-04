package com.docTutorials.Student_Service.Service;

import com.docTutorials.Student_Service.Dto.StudentDto;
import jakarta.validation.Valid;

import java.util.List;

public interface StudentService {

    List<StudentDto> getAllStudents();

    StudentDto getStudentById(int id);

    void addStudent(@Valid StudentDto studentDto);

    void updateStudent(int id, @Valid StudentDto studentDto);

    void deleteStudent(int id);


}
