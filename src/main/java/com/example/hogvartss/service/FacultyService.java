package com.example.hogvartss.service;

import com.example.hogvartss.dto.CreateFaculty;
import com.example.hogvartss.dto.FacultyDto;
import com.example.hogvartss.dto.StudentDto;
import com.example.hogvartss.entity.Faculty;
import com.example.hogvartss.exceotion.FacultyNotFoundException;
import com.example.hogvartss.mapper.FacultyMapping;
import com.example.hogvartss.mapper.StudentMapper;
import com.example.hogvartss.repository.FacultyRepository;
import com.example.hogvartss.repository.StudentRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final FacultyMapping facultyMapping;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;


    public FacultyService(FacultyRepository facultyRepository, FacultyMapping facultyMapping, StudentRepository studentRepository, StudentMapper studentMapper) {
        this.facultyRepository = facultyRepository;
        this.facultyMapping = facultyMapping;
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }


    public FacultyDto create(CreateFaculty createFaculty){
        return facultyMapping.toDto(facultyRepository.save(facultyMapping.toEntity(createFaculty)));
    }
    public FacultyDto update(long id, CreateFaculty createFaculty ){
        return facultyRepository.findById(id)
                .map(oldFaculty -> {
                    oldFaculty.setColor(createFaculty.getColor());
                    oldFaculty.setName(createFaculty.getName());
                    return facultyMapping.toDto(facultyRepository.save(oldFaculty));
                })
                .orElseThrow(() -> new FacultyNotFoundException(id));
    }

    public FacultyDto delete(long id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException(id));
        facultyRepository.delete(faculty);
        return facultyMapping.toDto(faculty);
    }
    public FacultyDto get(long id) {
        return facultyRepository.findById(id)
                .map(facultyMapping::toDto)
                .orElseThrow(() -> new FacultyNotFoundException(id));
    }
    public List<FacultyDto> findAll(@Nullable String color) {
        return Optional.ofNullable(color)
                .map(facultyRepository::findAllByColor)
                .orElseGet(facultyRepository::findAll).stream()
                .map(facultyMapping::toDto)
                .collect(Collectors.toList());
    }
    public List<FacultyDto> findByColorOrName(String colorOrName) {
        return facultyRepository.findAllByColorContainingIgnoreCaseOrNameContainingIgnoreCase(
                        colorOrName,
                        colorOrName
                ).stream()
                .map(facultyMapping::toDto)
                .collect(Collectors.toList());
    }
    public List<StudentDto> findStudents(long id) {
        return studentRepository.findAllByFaculty_Id(id).stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }


}
