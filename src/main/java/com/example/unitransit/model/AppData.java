package com.example.unitransit.model;

import java.util.List;

public class AppData {
    //For Access inside the program

    private static List<Road> roads;
    private static List<University> universities;

    public static List<Road> getRoads() {
        return roads;
    }

    public static void setRoads(List<Road> roads) {
        AppData.roads = roads;
    }

    public static List<University> getUniversities() {
        return universities;
    }

    public static void setUniversities(List<University> universities) {
        AppData.universities = universities;
    }
}
