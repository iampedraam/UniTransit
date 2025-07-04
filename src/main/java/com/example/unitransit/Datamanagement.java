package com.example.unitransit;

import com.example.unitransit.model.Student;
import com.example.unitransit.model.University;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Datamanagement {
    private static final String USERS_FILE = "users.json";
    private static final String UNI_FILE = "universities.json";

    private final Gson gson;
    public Datamanagement() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public List<Student> loadStudents() {
        try (FileReader reader = new FileReader(USERS_FILE)) {
            Type studentListType=new TypeToken<List<Student>>() {}.getType();
            return gson.fromJson(reader, studentListType);
        } catch (IOException e) {
            System.out.println("Error loading users file");
            return new ArrayList<>();
        }
    }
    public  void saveStudents(List<Student> students) {
        try (FileWriter writer = new FileWriter(USERS_FILE)) {
            gson.toJson(students, writer);
            System.out.println("Saving users to " + USERS_FILE);
        } catch (IOException e) {
            System.out.println("Error saving users to " + USERS_FILE);
        }
    }

    public List<University> loadUniversity() {
        try (FileReader reader = new FileReader(UNI_FILE)) {
            Type universityListType=new TypeToken<List<Student>>() {}.getType();
            return gson.fromJson(reader, universityListType);
        } catch (IOException e) {
            System.out.println("Error loading universities file");
            return new ArrayList<>();
        }
    }
    public void saveUniversity(List<University> university) {
        try (FileWriter writer = new FileWriter(UNI_FILE)) {
            gson.toJson(university, writer);
            System.out.println("Saving users to " + UNI_FILE);
        } catch (IOException e) {
            System.out.println("Error saving users to " + UNI_FILE);
        }
    }
}