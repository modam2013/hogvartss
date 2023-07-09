package com.example.hogvartss.mapper;

import com.example.hogvartss.dto.CreateStudent;
import com.example.hogvartss.dto.StudentDto;
import com.example.hogvartss.entity.Student;
import com.example.hogvartss.exceotion.FacultyNotFoundException;
import com.example.hogvartss.repository.FacultyRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StudentMapper {
    private final FacultyMapping facultyMapping;
    private final FacultyRepository facultyRepository;

    public StudentMapper(FacultyMapping facultyMapping, FacultyRepository facultyRepository) {
        this.facultyMapping = facultyMapping;
        this.facultyRepository = facultyRepository;
    }


    public StudentDto toDto(Student student){
        StudentDto studentDto=new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setName(student.getName());
        studentDto.setAge(student.getAge());
        Optional.ofNullable(student.getFaculty()).ifPresent(faculty -> studentDto.setFaculty(facultyMapping.toDto(faculty)));
        return studentDto;
    }
    public Student toEntity(CreateStudent createStudent){
        Student student =new Student();
        student.setAge(createStudent.getAge());
        student.setName(createStudent.getName());
        Optional.ofNullable(createStudent.getFacultyId()).ifPresent(facultyId ->
                facultyRepository.findById(facultyId)
                        .orElseThrow(() -> new FacultyNotFoundException(facultyId))
        );
        return student;
    }
}
