package com.example.testdb.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyDto {

    private Long id;
    private String message;

}
