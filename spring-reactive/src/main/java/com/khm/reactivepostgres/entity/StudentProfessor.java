package com.khm.reactivepostgres.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class StudentProfessor {
    @Id 
    private int id;
    private int studentId;
    private int professorId;

}
