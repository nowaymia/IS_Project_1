package com.khm.reactivepostgres.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.khm.reactivepostgres.entity.Student;

public interface StudentRepository extends R2dbcRepository<Student,Integer> {  
}
