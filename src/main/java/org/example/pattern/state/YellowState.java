package org.example.pattern.state;

import org.example.engine.Intersection;


public class YellowState implements TrafficLightState {

    @Override
    public void handle(Intersection intersection) {
        System.out.println("Đèn VÀNG - Chuẩn bị dừng");

        try {
            Thread.sleep(2000); // 2 giây
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // chuyển sang đỏ
        intersection.setState(new RedState());
    }

    @Override
    public String getColor() {
        return "YELLOW";
    }
}