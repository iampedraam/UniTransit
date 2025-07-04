package com.example.unitransit.model;

public class Student {
    String username, password, firstName, lastName, studentID, uni, phone;

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public Student (String username, String password,
                    String firstName, String lastName, String studentID,
                    String uni, String phone) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = studentID;
        this.uni = uni;
        this.phone = phone;
    }
}
