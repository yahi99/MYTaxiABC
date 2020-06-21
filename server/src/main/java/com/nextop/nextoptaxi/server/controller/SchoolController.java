package com.nextop.nextoptaxi.server.controller;

import com.nextop.nextoptaxi.server.dto.SchoolDto;
import com.nextop.nextoptaxi.server.dto.SchoolListDto;
import com.nextop.nextoptaxi.server.model.SchoolEntity;
import com.nextop.nextoptaxi.server.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController()
@RequestMapping("/schools")
public class SchoolController {
    private SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {

        this.schoolService = schoolService;
    }

    @GetMapping()
    public SchoolListDto getSchoolList(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        ArrayList<SchoolEntity> schoolList = this.schoolService.findSchool();
        ArrayList<SchoolDto> list = new ArrayList<>();
        for (SchoolEntity entity : schoolList) {
            SchoolDto dto = new SchoolDto();
            dto.id = entity.id;
            dto.name = entity.name;
            dto.totalStudent = entity.totalStudent;
            dto.totalTeacher = entity.totalTeacher;
            list.add(dto);
        }
        SchoolListDto dto = new SchoolListDto();
        dto.schools = list;
        return dto;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void addSchool(@RequestBody() SchoolDto newSchool) {
        SchoolEntity entity = new SchoolEntity();
        entity.name = newSchool.name;
        entity.totalStudent = newSchool.totalStudent;
        entity.totalTeacher = newSchool.totalTeacher;
        this.schoolService.addSchool(entity);
    }
    @GetMapping("/{schoolId}")
    public SchoolDto getSchool(@PathVariable("schoolId")long schoolId) {
        SchoolEntity entity = this.schoolService.getSchool(schoolId);
        if (entity == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        SchoolDto dto = new SchoolDto();
        dto.id = entity.id;
        dto.name = entity.name;
        dto.totalStudent = entity.totalStudent;
        dto.totalTeacher = entity.totalTeacher;
        return dto;
    }
}
