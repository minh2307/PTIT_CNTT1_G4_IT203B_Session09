package org.example.entity;


import org.example.engine.Intersection;
import org.example.pattern.observer.Observer;
import org.example.util.Logger;

public abstract class Vehicle implements Observer, Runnable {

    protected String id;
    protected int speed;
    protected Intersection intersection;
    protected volatile String trafficLightState = "RED";

    private boolean wasStopped = false;

    public Vehicle(String id, int speed, Intersection intersection) {
        this.id = id;
        this.speed = speed;
        this.intersection = intersection;
    }

    @Override
    public void update(String state) {
        this.trafficLightState = state;
    }

    public void move() {
        Logger.log(getName() + " đang di chuyển...");
        wasStopped = false;
    }

    public void stop() {
        if (!wasStopped) {
            Logger.log(getName() + " đang dừng lại...");
            wasStopped = true;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(speed);

                try {
                    intersection.requestEnter(this);

                    move();

                    Thread.sleep(500);

                    intersection.leaveIntersection();

                    System.out.println(getName() + " đã rời hệ thống");

                    break; // 🔥 xe biến mất

                } catch (RuntimeException e) {

                    // 🚑 xe cứu thương KHÔNG bị stop
                    if (!(this instanceof PriorityVehicle)) {
                        stop();
                    }
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    protected boolean canGo() {
        return "GREEN".equals(trafficLightState);
    }

    public abstract String getName();
}