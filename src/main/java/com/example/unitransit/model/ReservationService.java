package com.example.unitransit.model;

import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private final Graph graph;
    private final List<Reservation> reservations = new ArrayList<>();

    public ReservationService(List<University> universities, List<Road> roads) {
        this.graph = new Graph(universities, roads);
    }

    public Reservation reserve(Student student, int from, int to) {
        List<Road> path = graph.bestRoute(from, to);

        if (path.isEmpty()) {
            System.out.println("No good path found!!");
            return null;
        }

        for (Road r : path) {
            if (!r.hasCapacity()) {
                System.out.println("Route Full!!");
                return null;
            }
        }

        for (Road r : path) {
            r.decreaseCapacity();
        }

        int totalCost = path.stream().mapToInt(Road::getPrice).sum();
        Reservation reservation = new Reservation(student, from, to, path, totalCost);
        reservations.add(reservation);
        return reservation;
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }
}