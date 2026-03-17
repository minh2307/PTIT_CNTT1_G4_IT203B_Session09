package org.example.entity;

import org.example.engine.Intersection;

public class PriorityVehicle extends Vehicle {

    public PriorityVehicle(String id, int speed, Intersection intersection) {
        super(id, speed, intersection);
    }

    // xe ưu tiên luôn được đi (vượt đèn đỏ)
    @Override
    protected boolean canGo() {
        return true;
    }

    @Override
    public String getName() {
        return "🚑 Xe cứu thương #" + id;
    }
}