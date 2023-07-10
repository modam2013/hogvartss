package com.example.hogvartss.controller;

import com.example.hogvartss.dto.CreateStudent;
import com.example.hogvartss.dto.FacultyDto;
import com.example.hogvartss.dto.StudentDto;
import com.example.hogvartss.service.StudentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public StudentDto create(@RequestBody CreateStudent createStudent) {
        return studentService.create(createStudent);
    }

    @PutMapping("/{id}")
    public StudentDto update(@PathVariable("id") long id, @RequestBody CreateStudent createStudent) {
        return studentService.update(id, createStudent);
    }

    @GetMapping("/{id}")
    public StudentDto get(@PathVariable("id") long id) {
        return studentService.get(id);
    }

    @DeleteMapping("/{id}")
    public StudentDto delete(@PathVariable("id") long id) {
        return studentService.delete(id);
    }

    @GetMapping
    public List<StudentDto> findAll(@RequestParam(required = false) Integer age) {
        return studentService.findAll(age);
    }
    @GetMapping("/filter")
    public List<StudentDto> findByAgeBetween(@RequestParam int ageFrom, @RequestParam int ageTo) {
        return studentService.findByAgeBetween(ageFrom, ageTo);
    }
    @GetMapping("/{id}/faculty")
    public FacultyDto findFaculty(@PathVariable("id") long id) {
        return studentService.findFaculty(id);
    }

    @PatchMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public StudentDto uploadAvatar(@PathVariable long id,
                                   @RequestPart("avatar") MultipartFile multipartFile) {
        return studentService.uploadAvatar(id, multipartFile);
    }

}
