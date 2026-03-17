package org.example.entity;

import org.example.engine.Intersection;

public class StandardVehicle extends Vehicle {

    public StandardVehicle(String id, int speed, Intersection intersection) {
        super(id, speed, intersection);
    }

    @Override
    protected boolean canGo() {
        // xe thường chỉ đi khi đèn xanh
        return "GREEN".equals(trafficLightState);
    }

    @Override
    public String getName() {
        return "🚗 Xe thường #" + id;
    }
}
