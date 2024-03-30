package com.khm.reactivepostgres.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Builder
@Data
public class Professor {
    @Id
    private int id;
    private String name;

}
