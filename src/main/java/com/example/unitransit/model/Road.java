package com.example.unitransit.model;

public class Road {
    int from, to, id, capacity, price, open, close;
    //Open-Close Format: 12 or 16 or 21 etc
    //Price in HezarToman

    public Road(int from, int to, int capacity, int price, int open, int close) {
        this.id = from + to;
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.price = price;
        this.open = open;
        this.close = close;
    }
}
