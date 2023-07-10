package com.example.hogvartss.service;

import com.example.hogvartss.dto.CreateStudent;
import com.example.hogvartss.dto.FacultyDto;
import com.example.hogvartss.dto.StudentDto;
import com.example.hogvartss.entity.Avatar;
import com.example.hogvartss.entity.Student;
import com.example.hogvartss.exceotion.FacultyNotFoundException;
import com.example.hogvartss.exceotion.StudentNotFoundException;
import com.example.hogvartss.mapper.FacultyMapping;
import com.example.hogvartss.mapper.StudentMapper;
import com.example.hogvartss.repository.FacultyRepository;
import com.example.hogvartss.repository.StudentRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final FacultyRepository facultyRepository;
    private final FacultyMapping facultyMapping;
    private final AvatarService avatarService;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper, FacultyRepository facultyRepository, FacultyMapping facultyMapping, AvatarService avatarService) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.facultyRepository = facultyRepository;
        this.facultyMapping = facultyMapping;
        this.avatarService = avatarService;
    }


    public StudentDto create(CreateStudent createStudent){
        return studentMapper.toDto(studentRepository.save(studentMapper.toEntity(createStudent)));
    }
    public StudentDto update(long id, CreateStudent studentDtoIn) {
        return studentRepository.findById(id)
                .map(oldStudent -> {
                    oldStudent.setAge(studentDtoIn.getAge());
                    oldStudent.setName(studentDtoIn.getName());
                    Optional.ofNullable(studentDtoIn.getFacultyId()).ifPresent(facultyId ->
                            oldStudent.setFaculty(
                                    facultyRepository.findById(facultyId)
                                            .orElseThrow(() -> new FacultyNotFoundException(facultyId))
                            )
                    );
                    return studentMapper.toDto(studentRepository.save(oldStudent));
                })
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public StudentDto delete(long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        studentRepository.delete(student);
        return studentMapper.toDto(student);
    }

    public StudentDto get(long id) {
        return studentRepository.findById(id)
                .map(studentMapper::toDto)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }
    public List<StudentDto> findAll(@Nullable Integer age) {
        return Optional.ofNullable(age)
                .map(studentRepository::findAllByAge)
                .orElseGet(studentRepository::findAll).stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }
    public List<StudentDto> findByAgeBetween(int ageFrom, int ageTo) {
        return studentRepository.findAllByAgeBetween(ageFrom, ageTo).stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }
    public FacultyDto findFaculty(long id) {
        return studentRepository.findById(id)
                .map(Student::getFaculty)
                .map(facultyMapping::toDto)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }
    public StudentDto uploadAvatar(long id, MultipartFile multipartFile) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        Avatar avatar = avatarService.create(student, multipartFile);
        StudentDto studentDto = studentMapper.toDto(student);
        studentDto.setAvatarUrl("http://localhost:8080/avatars/" + avatar.getId() + "/from-db");
        return studentDto;
    }

}
