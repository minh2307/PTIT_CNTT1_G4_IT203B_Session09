package org.example.pattern.state;

import org.example.engine.Intersection;

public class GreenState implements TrafficLightState {

    @Override
    public void handle(Intersection intersection) {
        System.out.println("Đèn XANH - Xe được phép đi");

        try {
            Thread.sleep(5000); // 5 giây
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // chuyển sang vàng
        intersection.setState(new YellowState());
    }

    @Override
    public String getColor() {
        return "GREEN";
    }
}