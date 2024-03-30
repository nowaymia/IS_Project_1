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

import com.khm.reactivepostgres.entity.Professor;
import com.khm.reactivepostgres.repository.ProfessorRepository;
import com.khm.reactivepostgres.repository.StudentProfessorRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProfessorController {
    private final ProfessorRepository professorRepository;
    private final StudentProfessorRepository studentProfessorRepository;
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    @GetMapping("/professors")
    public Flux<Professor> getAll() {
        System.out.println("[" + formatter.format(new Date()) + "] ALL PROFESSORS");
        return professorRepository.findAll();
    }

    @GetMapping("/professor/{id}")
    public Mono<Professor> getProfessor(@PathVariable int id) {
        System.out.println("[" + formatter.format(new Date()) + "] GET PROFESSOR ID="+id);
        return professorRepository.findById(id);
    }

    @PostMapping("/professor")
    public Mono<Professor> createProfessor(@RequestBody Professor professor) {
        System.out.println("[" + formatter.format(new Date()) + "] PROFESSOR CREATED");
        return professorRepository.save(professor);
    }

    @DeleteMapping("/professor/{id}")
    public Mono<Void> deleteProfessor(@PathVariable int id) {
        System.out.println("[" + formatter.format(new Date()) + "] PROFESSOR " + id + " DELETED");
        return studentProfessorRepository.countByProfessorId(id).flatMap(
                c -> {
                    if (c == 0) {

                        return professorRepository.deleteById(id);
                    }

                    return Mono.empty();
                });

    }

    @PutMapping("/professor/{id}")
    public Mono<Professor> updateProfessor(@PathVariable int id, @RequestBody Professor professor) {
        System.out.println("[" + formatter.format(new Date()) + "] PROFESSOR " + id +" UPDATED");
        professor.setId(id);
        return professorRepository
                .findById(id)
                .flatMap(professorResult -> professorRepository.save(professor));
    }

}
