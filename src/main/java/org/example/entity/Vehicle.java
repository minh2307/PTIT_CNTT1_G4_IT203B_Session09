package org.example.entity;


import org.example.engine.Intersection;
import org.example.pattern.observer.Observer;

public abstract class Vehicle implements Observer, Runnable {

    protected String id;
    protected int speed;
    protected Intersection intersection;
    protected volatile String trafficLightState = "RED";

    public Vehicle(String id, int speed, Intersection intersection) {
        this.id = id;
        this.speed = speed;
        this.intersection = intersection;
    }

    // Observer: nhận tín hiệu đèn
    @Override
    public void update(String state) {
        this.trafficLightState = state;
    }

    // hành vi chung
    public void move() {
        System.out.println(getName() + " đang di chuyển...");
    }

    public void stop() {
        System.out.println(getName() + " đang dừng lại...");
    }

    // mỗi xe chạy như 1 thread
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(speed);

                if (canGo()) {
                    move();
                    intersection.enterIntersection(getName());
                } else {
                    stop();
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    // logic quyết định đi/dừng
    protected boolean canGo() {
        return "GREEN".equals(trafficLightState);
    }

    public abstract String getName();
}
