package com.khm.reactivepostgres.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import com.khm.reactivepostgres.entity.StudentProfessor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentProfessorRepository extends R2dbcRepository<StudentProfessor, Integer> {

    @Query("SELECT COUNT(*) FROM student_professor WHERE student_id=:studId AND professor_id=:profId")
    public Mono<Integer> countByStudentIdAndProfessorId(int studentId, int professorId);

    @Query("SELECT COUNT(*) FROM student_professor WHERE student_id=:id")
    public Mono<Integer> countByStudentId(int id);

    @Query("SELECT COUNT(*) FROM student_professor WHERE professor_id=:id")
    public Mono<Integer> countByProfessorId(int id);

    @Query("SELECT * FROM student_professor WHERE student_id=:studId AND professor_id=:profId")
    public Mono<StudentProfessor> findRelation(int studId, int profId);
}
