package com.docTutorials.Student_Service.Service;

import com.docTutorials.Student_Service.Dto.StudentDto;
import com.docTutorials.Student_Service.Model.Student;
import com.docTutorials.Student_Service.Repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService{

    private final Logger logger = Logger.getLogger(StudentServiceImpl.class.getName());

    private StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository){
        this.repository = repository;
    }


    @Override
    public List<StudentDto> getAllStudents() {
        StudentDto studentDto = new StudentDto();
        return repository.findAll().stream()
                .map(student -> new StudentDto(student.getId(), student.getName(), student.getCity()))
                .collect(Collectors.toList());
    }


    @Override
    public StudentDto getStudentById(int id) {
        return repository.findById(id).stream()
                .map(student -> new StudentDto(student.getId(), student.getName(), student.getCity()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    @Override
    public void addStudent(StudentDto studentDto) {
        logger.info("Adding new student: " + studentDto.getName() + ", " + studentDto.getCity());
        if (studentDto.getName() == null || studentDto.getCity() == null) {
            logger.warning("Invalid student data provided");
            throw new RuntimeException("Invalid student data");
        }
        Student student = new Student(studentDto.getId(), studentDto.getName(), studentDto.getCity());
        repository.save(student);
        logger.info("Student added successfully: " + studentDto.getName());
        logger.info("Student added successfully with ID: " + student.getId());
        logger.info("Student added successfully with City: " + student.getCity());
    }

    @Override
    public void updateStudent(int id, StudentDto studentDto) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        student.setName(studentDto.getName());
        student.setCity(studentDto.getCity());
        repository.save(student);
    }

    @Override
    public void deleteStudent(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
