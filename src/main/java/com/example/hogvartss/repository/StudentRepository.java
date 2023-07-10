package com.example.hogvartss.repository;

import com.example.hogvartss.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long > {
    List<Student> findAllByAge(int age);
    List<Student> findAllByAgeBetween(int ageFrom, int ageTo);
    List<Student> findAllByFaculty_Id(long facultyId);


}
