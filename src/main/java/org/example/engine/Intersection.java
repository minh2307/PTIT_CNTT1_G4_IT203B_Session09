package org.example.engine;


import org.example.pattern.observer.Observer;
import org.example.pattern.observer.Subject;
import org.example.pattern.state.GreenState;
import org.example.pattern.state.TrafficLightState;

import java.util.ArrayList;
import java.util.List;

public class Intersection implements Subject {

    private TrafficLightState currentState;
    private final List<Observer> observers = new ArrayList<>();

    public Intersection() {
        // trạng thái ban đầu
        this.currentState = new GreenState();
    }

    // ================= STATE =================
    public void setState(TrafficLightState state) {
        this.currentState = state;
        notifyObservers(); // thông báo khi đổi đèn
    }

    public String getStateColor() {
        return currentState.getColor();
    }

    // ================= OBSERVER =================
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(currentState.getColor());
        }
    }

    public void enterIntersection(String vehicleName) {
        System.out.println(vehicleName + " đang đi qua ngã tư");
    }

    // ================= RUN SIMULATION =================
    public void start() {
        while (true) {
            currentState.handle(this);
        }
    }
}