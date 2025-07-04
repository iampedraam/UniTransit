package com.example.unitransit.model;

public class University {
    private int university_id;
    private String name;

    public University() {
    }

    public University(String name, int university_id) {
        this.name = name;
        this.university_id = university_id;
    }

    public String getName() {
        return name;
    }

    public int getUniversityId() {
        return university_id;
    }

    @Override
    public String toString() {
        return name; // نمایش فقط نام در ComboBox
    }
}