package com.example.unitransit.model;

public class Road {
    University from, to;
    int id, capacity, price, open, close;
    //Open-Close Format: 12 or 16 or 21 etc

    public Road(University from, University to, int capacity, int price, int open, int close) {
        this.id = from.university_id + to.university_id;
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.price = price;
        this.open = open;
        this.close = close;
    }
}
