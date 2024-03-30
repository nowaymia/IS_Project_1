package com.khm.reactivepostgres.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.khm.reactivepostgres.entity.Student;
import com.khm.reactivepostgres.repository.StudentProfessorRepository;
import com.khm.reactivepostgres.repository.StudentRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentRepository studentRepository;
    private final StudentProfessorRepository studentProfessorRepository;
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    @GetMapping("/students")
    public Flux<Student> getAll() {
        System.out.println("[" + formatter.format(new Date()) + "] GET ALL STUDENTS");
        return studentRepository.findAll();
    }

    @GetMapping("/student/{id}")
    public Mono<Student> getStudent(@PathVariable int id) {
        System.out.println("[" + formatter.format(new Date()) + "] GET STUDENT ID="+id);
        return studentRepository.findById(id);
    }

    @PostMapping("/student")
    public Mono<Student> createStudent(@RequestBody Student student) {
        System.out.println("[" + formatter.format(new Date()) + "] STUDENT CREATED");
        return studentRepository.save(student);
    }

    @PutMapping("/student/{id}")
    public Mono<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
        System.out.println("[" + formatter.format(new Date()) + "] STUDENT " + id + " UPDATED");
        student.setId(id);
        return studentRepository
                .findById(id)
                .flatMap(studentResult -> studentRepository.save(student));
    }

    @DeleteMapping("/student/{id}")
    public Mono<Object> deleteStudent(@PathVariable int id) {
        System.out.println("[" + formatter.format(new Date()) + "] STUDENT " + id + " DELETED");
        return studentProfessorRepository.countByStudentId(id).flatMap(
                c -> {
                    if (c == 0) {
                        return studentRepository.deleteById(id);
                    }
                    return Mono.just("User cannot be delete, currently has " + c + " relationships");
                });

    }

}
