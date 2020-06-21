package com.nextop.nextoptaxi.server.controller;

import com.nextop.nextoptaxi.server.dto.SampleDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
    @GetMapping("/")
    public SampleDto sampleRequest(){
        SampleDto dto = new SampleDto();
        dto.name="펭수";
        dto.age=20;
        return dto;
    }
}
