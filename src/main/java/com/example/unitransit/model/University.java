package com.example.unitransit.model;

public class University {
    int university_id;
    String university_name;
    String name;
    public University(String name, int university_id) {
        this.name = name;
        this.university_id = university_id;
    }
    public String getName() {
        return name;
    }
    public int getUniversity_id() {
        return university_id;
    }
}
