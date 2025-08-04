package com.docTutorials.Student_Service.Repository;

import com.docTutorials.Student_Service.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {
}
