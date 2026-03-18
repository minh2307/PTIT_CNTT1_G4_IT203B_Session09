package org.example.entity;


import org.example.engine.Intersection;
import org.example.exception.CollisionException;import org.example.exception.TrafficJamException;import org.example.pattern.observer.Observer;
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

    protected boolean canGo() {
        return "GREEN".equals(trafficLightState);
    }

    @Override
    public void run() {

        while (true) {
            try {
                intersection.requestEnter(this);

                move();

                Thread.sleep(1000);

                intersection.leaveIntersection();

                break;

            } catch (TrafficJamException e) {
                System.out.println(e.getMessage());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

            } catch (CollisionException e) {
                System.out.println(e.getMessage());
                break;

            } catch (RuntimeException e) {
                if ("WAIT".equals(e.getMessage())) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public abstract String getName();
}