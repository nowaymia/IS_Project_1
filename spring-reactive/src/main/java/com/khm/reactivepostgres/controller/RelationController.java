package com.khm.reactivepostgres.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.khm.reactivepostgres.entity.StudentProfessor;
import com.khm.reactivepostgres.repository.ProfessorRepository;
import com.khm.reactivepostgres.repository.StudentProfessorRepository;
import com.khm.reactivepostgres.repository.StudentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RelationController {

    private final StudentProfessorRepository studentProfessorRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    @GetMapping("/relations")
    public Flux<StudentProfessor> getAll() {
        System.out.println("[" + formatter.format(new Date()) + "] GET ALL RELATIONS");
        return studentProfessorRepository.findAll();
    }

    @DeleteMapping("/relation")
    public Mono<Void> deleteRelation(@RequestBody StudentProfessor relation) {
        System.out.println("[" + formatter.format(new Date()) + "] DELETED RELATION");
        return studentProfessorRepository.findRelation(relation.getStudentId(), relation.getProfessorId())
                .flatMap(r -> studentProfessorRepository.delete(r));
    }

    @PostMapping("/relation")
    public Mono<StudentProfessor> createRelation(@RequestBody StudentProfessor relation) {
        return studentProfessorRepository.findRelation(relation.getStudentId(), relation.getProfessorId()).hasElement()
                .flatMap(x -> {
                    if (!x) {
                        System.out.println("[" + formatter.format(new Date()) + "] RELATION CREATED");
                        return studentProfessorRepository.save(relation);
                    }
                    System.out.println("[" + formatter.format(new Date()) + "] RELATION ALREADY EXISTS");
                    return Mono.empty();
                });
    }
}
