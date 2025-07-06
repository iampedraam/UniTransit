package com.example.unitransit.model;

import java.util.List;

public class AppData {
    //For Access inside the program

    private static List<Road> roads;
    private static List<University> universities;
    private static Graph graph;
    private static List<Road> shortestPath;
    private static ReservationService reservationService;

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

    public static Graph getGraph() {
        return graph;
    }
    public static void setGraph(Graph graph) {
        AppData.graph = graph;
    }

    public static List<Road> getShortestPath() {
        return shortestPath;
    }
    public static void setShortestPath(List<Road> shortestPath) {
        AppData.shortestPath = shortestPath;
    }

    public static ReservationService getReservationService() { return reservationService; }
    public static void setReservationService(ReservationService service) { reservationService = service; }
}
