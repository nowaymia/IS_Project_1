package com.is.client.models;

import java.time.LocalDate;

public class Student {

    private int id;
    private String name;
    private LocalDate birthDate;
    private int completedCredits;
    private float averageGrade;

    public Student() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public int getCompletedCredits() {
        return completedCredits;
    }

    public void setCompletedCredits(int completedCredits) {
        this.completedCredits = completedCredits;
    }

    public float getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(float averageGrade) {
        this.averageGrade = averageGrade;
    }

    @Override
    public String toString() {
        return "Student [id=" + id + ", name=" + name + ", birthDate=" + birthDate + ", completedCredits="
                + completedCredits + ", averageGrade=" + averageGrade + "]";
    }

    
}
