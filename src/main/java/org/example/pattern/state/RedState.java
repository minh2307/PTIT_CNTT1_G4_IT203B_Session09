package org.example.pattern.state;

import org.example.engine.Intersection;

public class RedState implements TrafficLightState {

    @Override
    public void handle(Intersection intersection) {
        System.out.println("Đèn ĐỎ - Xe phải dừng");

        try {
            Thread.sleep(5000); // 5 giây
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // chuyển sang xanh
        intersection.setState(new GreenState());
    }

    @Override
    public String getColor() {
        return "RED";
    }
}