package org.example.engine;

import org.example.pattern.observer.Observer;
import org.example.pattern.observer.Subject;
import org.example.pattern.state.TrafficLightState;

import java.util.List;
import java.util.ArrayList;

public class TrafficLight implements Subject {

    private TrafficLightState currentState;
    private final List<Observer> observers;

    public TrafficLight(TrafficLightState initialState) {
        this.currentState = initialState;
        this.observers = new ArrayList<>();
    }

    public TrafficLightState getCurrentState() {
        return currentState;
    }

    public void setState(TrafficLightState newState) {
        this.currentState = newState;
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(currentState);
        }
    }
}