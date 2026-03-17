package org.example.pattern.observer;

import org.example.pattern.state.TrafficLightState;

public interface Observer {
    void update(TrafficLightState newState);
}