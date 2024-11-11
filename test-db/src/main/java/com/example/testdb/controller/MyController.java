package com.example.testdb.controller;

import com.example.testdb.model.MyDto;
import com.example.testdb.service.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MyController {

    private final ServiceImpl service;

    @PostMapping("/create-data")
    public String create(@RequestBody MyDto myDto) {
        service.save(myDto);
        return "Data saved";
    }

}
