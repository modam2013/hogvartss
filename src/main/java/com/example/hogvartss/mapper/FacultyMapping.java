package com.example.hogvartss.mapper;

import com.example.hogvartss.dto.CreateFaculty;
import com.example.hogvartss.dto.FacultyDto;
import com.example.hogvartss.entity.Faculty;
import org.springframework.stereotype.Component;

@Component
public class FacultyMapping {

        public FacultyDto toDto(Faculty faculty){
            FacultyDto facultyDto=new FacultyDto();
            faculty.setId(faculty.getId());
            faculty.setName(faculty.getName());
            faculty.setColor(faculty.getColor());
            return facultyDto;
        }
        public Faculty toEntity(CreateFaculty createFaculty){
            Faculty faculty =new Faculty();
            faculty.setColor(createFaculty.getColor());
            faculty.setName(createFaculty.getName());
            return faculty;
        }
}
