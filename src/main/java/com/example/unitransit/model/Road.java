package com.example.unitransit.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Road {
    private int from, to, id, capacity, price, open, close;
    // Open-Close Format: 12 or 16 or 21 etc
    // Price in HezarToman

    public Road(int from, int to, int capacity, int price, int open, int close) {
        this.from = from;
        this.to = to;
        this.id = from * 10 + to;  // Unique ID based on from/to
        this.capacity = capacity;
        this.price = price;
        this.open = open;
        this.close = close;
    }

    public int getFrom() { return from; }
    public int getTo() { return to; }
    public void setId(int id) { this.id = id; }
    public int getId() { return id; }
    public int getCapacity() { return capacity; }
    public boolean hasCapacity() { return capacity > 0; }
    public void decreaseCapacity() {
        capacity--;
    }
    public boolean canReserve(){
        if (capacity == 0)
            return false;
        capacity--;
        return true;
    }
    public int getPrice() { return price; }
    public int getOpen() { return open; }
    public int getClose() { return close; }


    public static List<Road> randomRoads(List<University> universities, double density, Random rng) {
        final int n = universities.size();
        List<Road> roads = new ArrayList<>();

        for (int i = 1; i < n; i++) {
            int parent = rng.nextInt(i);
            roads.addAll(makeBidirectional(universities.get(parent).getUniversityId(),
                    universities.get(i).getUniversityId(), rng));
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (rng.nextDouble() < density) {
                    roads.addAll(makeBidirectional(
                            universities.get(i).getUniversityId(),
                            universities.get(j).getUniversityId(), rng));
                }
            }
        }
        return roads;
    }
    private static List<Road> makeBidirectional(int a, int b, Random rng) {
        int capacity = 1 + rng.nextInt(11);        // 1‑11
        int price    = 100  + rng.nextInt(51);
        int open     = 7  + rng.nextInt(6);
        int close    = 15 + rng.nextInt(6);

        List<Road> pair = new ArrayList<>(2);
        pair.add(new Road(a, b, capacity, price, open, close));
        pair.add(new Road(b, a, capacity, price, open, close));
        return pair;
    }
}