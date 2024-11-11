package com.example.testdb.service;

import com.example.testdb.entity.MyEntity;
import com.example.testdb.model.MyDto;
import com.example.testdb.repository.MyRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceImpl {

    private final MyRepository repository;

    public ServiceImpl(MyRepository repository) {
        this.repository = repository;
    }

    public String save(MyDto myDto) {
        extracted(myDto);
        return "Data saved";
    }

    private void extracted(MyDto myDto) {
        var myEntity = new MyEntity();
        myEntity.setId(myDto.getId());
        myEntity.setMessage(myDto.getMessage());
        repository.save(myEntity);
    }
}
