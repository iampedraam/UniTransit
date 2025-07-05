package com.example.unitransit.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Reservation {
    private static final AtomicInteger GEN = new AtomicInteger(1);

    private final int id = GEN.getAndIncrement();
    private final Student student;
    private final int from, to;
    private final List<Road> path;
    private final int totalCost;
    private final LocalDateTime reserveTime;

    public Reservation(Student student, int from, int to,
                       List<Road> path, int totalCost) {
        this.student   = student;
        this.from      = from;
        this.to        = to;
        this.path      = path;
        this.totalCost = totalCost;
        this.reserveTime = LocalDateTime.now();
    }

    public int        getId()        { return id;        }
    public Student    getStudent()   { return student;   }
    public List<Road> getPath()      { return path;      }
    public int        getTotalCost() { return totalCost; }
}