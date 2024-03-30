package com.khm.reactivepostgres.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.khm.reactivepostgres.entity.Student;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ErrorController {
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    @GetMapping("/error")
    public Mono<Student> getAll() {
        System.out.println("[" + formatter.format(new Date()) + "] GET ERROR");
        return Mono.error(new Exception());
    }
}
