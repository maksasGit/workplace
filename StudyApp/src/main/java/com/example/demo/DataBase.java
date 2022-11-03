package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
    String url = "jdbc:sqlite:D:\\UMCS\\oop\\rozwiazania\\demo\\dane";

    public static List<Student> readFromDB(){
        List<Student> students = new ArrayList<>();
        List<Subject> subjectsMaksas = new ArrayList<>();
        List<Subject> subjectsOleg = new ArrayList<>();
        Subject pr1 = new Subject("matesza" , 1, 2, 3, 4, 5 , 0.0);
        Subject pr2 = new Subject("angl" , 2, 2, 2, 2, 2 , 1.0);
        Subject pr3 = new Subject("algosy" , 3, 3, 3, -1, 5 , 5.0);
        Subject pr4 = new Subject("knopowiedienie" , 3, 0, 4, -1, 2 , 3.0);
        subjectsMaksas.add(pr1);
        subjectsMaksas.add(pr2);
        subjectsMaksas.add(pr3);
        Student maksas = new Student("Maksas" , "kek@gamil.com" , 12 , 01 , subjectsMaksas);
        subjectsOleg.add(pr4);
        subjectsOleg.add(pr3);
        subjectsOleg.add(pr1);
        Student oleg = new Student("Oleg" , "kefor@gamil.com" , 21 , 02 , subjectsOleg);
        students.add(maksas);
        students.add(oleg);
        return students;
    }

    public static String LogIn(String nickname, String password){
        String openStudent = "student.fxml";
        String openTeacher = "teacher.fxml";
        if (nickname.equals("maksas") && password.equals("knopa123")) return openStudent;
        else if (nickname.equals("sasha") && password.equals("kastus")) return openTeacher;
        else return "null";
    }

    public static Student getOneStudent(){
        List<Subject> subjectsMaksas = new ArrayList<>();
        Subject pr1 = new Subject("matesza" , 1, 2, 3, 4, 5 , 0.0);
        Subject pr2 = new Subject("angl" , 2, 2, 2, 2, 2 , 1.0);
        Subject pr3 = new Subject("algosy" , 3, 3, 3, -1, 5 , 5.0);
        Subject pr4 = new Subject("knopowiedienie" , 3, 0, 4, -1, 2 , 3.0);
        subjectsMaksas.add(pr1);
        subjectsMaksas.add(pr2);
        subjectsMaksas.add(pr3);
        subjectsMaksas.add(pr4);
        Student maksas = new Student("Maksas" , "kek@gamil.com" , 12 , 01 , subjectsMaksas);
        return maksas;
    }
}
