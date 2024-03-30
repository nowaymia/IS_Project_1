package com.khm.reactivepostgres.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Student {

    @Id 
    private int id;
    private String name;
    private LocalDate birthDate;
    private int completedCredits;
    private float averageGrade;

    public Student() {
    }
}
