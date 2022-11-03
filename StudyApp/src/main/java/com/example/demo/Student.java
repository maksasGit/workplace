package com.example.demo;

import java.util.List;

public class Student {
    String name;
    String email;
    int age;
    int id;
    List<Subject> subjects;

    public Student(String name, String email, int age, int id, List<Subject> subjects) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.id = id;
        this.subjects = subjects;
    }




}
