package com.nextop.nextoptaxi.server.controller;

import com.nextop.nextoptaxi.server.dto.SchoolDto;
import com.nextop.nextoptaxi.server.dto.SchoolListDto;
import com.nextop.nextoptaxi.server.dto.TaxiDto;
import com.nextop.nextoptaxi.server.dto.TaxiListDto;
import com.nextop.nextoptaxi.server.model.SchoolEntity;
import com.nextop.nextoptaxi.server.model.TaxiEntity;
import com.nextop.nextoptaxi.server.service.SchoolService;
import com.nextop.nextoptaxi.server.service.TaxiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController()
@RequestMapping(path = "/taxis")
public class TaxiController {

    private TaxiService taxiService;

    @Autowired
    public TaxiController(TaxiService TaxiService) {

        this.taxiService = TaxiService;
    }

    @GetMapping()
    public TaxiListDto getTaxiList(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        ArrayList<TaxiEntity> taxiList = this.taxiService.findTaxiList();
        ArrayList<TaxiDto> list = new ArrayList<>();
        for (TaxiEntity taxi : taxiList) {
            TaxiDto dto = new TaxiDto();
            dto.driver = taxi.driver;
            dto.taxiNumber = taxi.taxiNumber;
            dto.latitude =  taxi.latitude;
            dto.longitude = taxi.longitude;
            list.add(dto);
        }
        TaxiListDto result = new TaxiListDto();
        result.taxis = list;
        return result;
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void addTaxi(@RequestBody() TaxiDto dto){
        TaxiEntity taxi = new TaxiEntity();
        taxi.driver = dto.driver;
        taxi.taxiNumber = dto.taxiNumber;
        taxi.latitude =  dto.latitude;
        taxi.longitude = dto.longitude;
        this.taxiService.addTaxiPosition(taxi);

    }
    @GetMapping("/{taxiId}")
    public TaxiDto getTaxi(@PathVariable("taxiId")long taxiId) {
        TaxiEntity taxi = this.taxiService.getTaxi(taxiId);
        if (taxi == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        TaxiDto dto = new TaxiDto();
        dto.driver = taxi.driver;
        dto.taxiNumber = taxi.taxiNumber;
        dto.latitude =  taxi.latitude;
        dto.longitude = taxi.longitude;
        return dto;
    }
}
