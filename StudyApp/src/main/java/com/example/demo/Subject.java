package com.example.demo;

import java.util.List;

public class Subject {
    String name;
    int mark1;
    int mark2;
    int mark3;
    int mark4;

    public String getName() {
        return name;
    }

    public int getMark1() {
        return mark1;
    }

    public int getMark2() {
        return mark2;
    }

    public int getMark3() {
        return mark3;
    }

    public int getMark4() {
        return mark4;
    }

    public int getMark5() {
        return mark5;
    }

    public double getAverage() {
        return average;
    }

    int mark5;
    double average;

    public Subject(String name, int mark1, int mark2, int mark3, int mark4, int mark5, double average) {
        this.name = name;
        this.mark1 = mark1;
        this.mark2 = mark2;
        this.mark3 = mark3;
        this.mark4 = mark4;
        this.mark5 = mark5;
        this.average = average;
    }
}
