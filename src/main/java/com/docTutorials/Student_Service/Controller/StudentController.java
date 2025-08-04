package com.docTutorials.Student_Service.Controller;

import com.docTutorials.Student_Service.Dto.StudentDto;
import com.docTutorials.Student_Service.Service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;
    private final Logger logger = Logger.getLogger(StudentController.class.getName());


    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/getAllStudents")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        logger.info("Fetching all students");
        List<StudentDto> students = service.getAllStudents();
        if (students.isEmpty()) {
            logger.warning("No students found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.info("Students fetched successfully");
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

     @GetMapping("/getStudent/{id}")
        public ResponseEntity<StudentDto> getStudentById(@PathVariable int id ) {
        logger.info("Fetching student with ID: " + id);
        StudentDto student = service.getStudentById(id);
        if (student == null) {
            logger.warning("Student with ID " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Student with ID " + id + " fetched successfully");
        return new ResponseEntity<>(student, HttpStatus.OK);
     }

    @PostMapping("/addStudent")
    public ResponseEntity<String> addStudent(@Valid @RequestBody StudentDto studentDto) {
        logger.info("Adding new student: " + studentDto.getName() + ", " + studentDto.getCity());

        if (studentDto.getName() == null || studentDto.getCity() == null) {
            logger.warning("Invalid student data provided");
            return new ResponseEntity<>("Invalid student data", HttpStatus.BAD_REQUEST);
        }

        service.addStudent(studentDto);
        logger.info("Student added successfully");
        return new ResponseEntity<>("Student added successfully", HttpStatus.CREATED);
    }
        @PutMapping("/updateStudent/{id}")
        public ResponseEntity<String> updateStudent(@PathVariable int id, @Valid @RequestBody StudentDto studentDto) {
            logger.info("Updating student with ID: " + id);
            service.updateStudent(id, studentDto);
            logger.info("Student with ID " + id + " updated successfully");
            return new ResponseEntity<>("Student updated successfully", HttpStatus.OK);
        }
        @DeleteMapping("/deleteStudent/{id}")
        public ResponseEntity<String> deleteStudent(@PathVariable int id) {
            logger.info("Deleting student with ID: " + id);
            service.deleteStudent(id);
            logger.info("Student with ID " + id + " deleted successfully");
            return new ResponseEntity<>("Student deleted successfully", HttpStatus.OK);
        }





}









