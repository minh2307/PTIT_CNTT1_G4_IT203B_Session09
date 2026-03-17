package org.example.entity;


import java.util.UUID;

public abstract class Vehicle implements Runnable {

    protected String id;
    protected double speed;
    protected String type;
    protected int priority;

    public Vehicle(double speed, String type, int priority) {
        this.id = UUID.randomUUID().toString();
        this.speed = speed;
        this.type = type;
        this.priority = priority;
    }

    public abstract void move();

    public String getId() {
        return id;
    }

    public double getSpeed() {
        return speed;
    }

    public String getType() {
        return type;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public void run() {
        move();
    }

    @Override
    public String toString() {
        return "[" + type + " #" + id.substring(0, 5) + "]";
    }
}
