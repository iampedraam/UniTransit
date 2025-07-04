package com.example.unitransit;

import com.example.unitransit.model.Road;
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
    private static final String ROADS_FILE = "roads.json";

    private final Gson gson;

    public Datamanagement() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public List<Student> loadStudents() {
        try (FileReader reader = new FileReader(USERS_FILE)) {
            Type studentListType = new TypeToken<List<Student>>() {
            }.getType();
            return gson.fromJson(reader, studentListType);
        } catch (IOException e) {
            System.out.println("Error loading users file");
            return new ArrayList<>();
        }
    }

    public void saveStudents(List<Student> students) {
        try (FileWriter writer = new FileWriter(USERS_FILE)) {
            gson.toJson(students, writer);
            System.out.println("Saving users to " + USERS_FILE);
        } catch (IOException e) {
            System.out.println("Error saving users to " + USERS_FILE);
        }
    }

    public List<University> loadUniversity() {
        try (FileReader reader = new FileReader(UNI_FILE)) {
            Type universityListType = new TypeToken<List<University>>() {
            }.getType();
            return gson.fromJson(reader, universityListType);
        } catch (IOException e) {
            System.out.println("Error loading universities file");
            return new ArrayList<>();
        }
    }

    public List<Road> loadRoads() {
        try (FileReader reader = new FileReader(ROADS_FILE)) {
            Type roadListType = new TypeToken<List<Road>>() {}.getType();
            List<Road> roads = gson.fromJson(reader, roadListType);
            //Generate IDs
            if (roads != null) {
                for (Road road : roads) {
                    // assuming Road has setId(int) method
                    road.setId(road.getFrom() * 10 + road.getTo());
                }
            }
            return roads != null ? roads : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error loading roads file");
            return new ArrayList<>();
        }
    }

}