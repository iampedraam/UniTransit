package com.example.unitransit.model;

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
    public int increaseCapacity() {
        capacity += 1;
        return capacity;
    }
    public int decreaseCapacity() {
        capacity -= 1;
        return capacity;
    }
    public int getPrice() { return price; }
    public int getOpen() { return open; }
    public int getClose() { return close; }
}