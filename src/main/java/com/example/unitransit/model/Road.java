package com.example.unitransit.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Road {
    private int from, to, id, capacity, price, open, close;
    // Open-Close Format: 12 or 16 or 21 or ...
    // Price in HezarToman
    private static final int[][] FIXED_ROADS = {
            {1, 2}, {2, 3}, {2, 4}, {3, 5}, {2, 6}, {2, 7}, {3, 4}, {4, 7}, {1, 7}, {1, 6}, {6, 5}, {5, 4}
    };

    public Road(int from, int to, int capacity, int price, int open, int close) {
        this.from = from;
        this.to = to;
        this.id = from * 10 + to;
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


    public static List<Road> generateFixedRoads(Random rng) {
        List<Road> roads = new ArrayList<>();
        for (int[] edge : FIXED_ROADS) {
            int from = edge[0];
            int to = edge[1];
            roads.addAll(makeBidirectional(from, to, rng));
        }
        AppData.setRoads(roads); //For Accessibility inside app
        return roads;
    }
    private static List<Road> makeBidirectional(int a, int b, Random rng) {
        int capacity = 1 + rng.nextInt(10);
        int price = 100 + rng.nextInt(151);
        int open = 6 + rng.nextInt(10);
        int close = 23 - rng.nextInt(6);

        List<Road> pair = new ArrayList<>(2);
        pair.add(new Road(a, b, capacity, price, open, close));
        pair.add(new Road(b, a, capacity, price, open, close));
        return pair;
    }
}