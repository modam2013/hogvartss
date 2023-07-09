package com.example.hogvartss.controller;

import com.example.hogvartss.dto.CreateFaculty;
import com.example.hogvartss.dto.FacultyDto;
import com.example.hogvartss.dto.StudentDto;
import com.example.hogvartss.service.FacultyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculties")
@Tag(name = "Контроллер по работе с факультетами")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public FacultyDto create(@RequestBody CreateFaculty facultyDtoIn) {
        return facultyService.create(facultyDtoIn);
    }

    @PutMapping("/{id}")
    public FacultyDto update(@PathVariable("id") long id, @RequestBody CreateFaculty createFaculty) {
        return facultyService.update(id, createFaculty);
    }

    @GetMapping("/{id}")
    public FacultyDto get(@PathVariable("id") long id) {
        return facultyService.get(id);
    }

    @DeleteMapping("/{id}")
    public FacultyDto delete(@PathVariable("id") long id) {
        return facultyService.delete(id);
    }

    @GetMapping
    public List<FacultyDto> findAll(@RequestParam(required = false) String color) {
        return facultyService.findAll(color);
    }
    @GetMapping("/filter")
    public List<FacultyDto> findByColorOrName(@RequestParam String colorOrName) {
        return facultyService.findByColorOrName(colorOrName);
    }
    @GetMapping("/{id}/students")
    public List<StudentDto> findStudents(@PathVariable("id") long id) {
        return facultyService.findStudents(id);
    }

}
