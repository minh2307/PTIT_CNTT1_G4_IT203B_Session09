package org.example.pattern.state;

import org.example.engine.Intersection;

public interface TrafficLightState {
    void handle(Intersection intersection);  // xử lý logic state
    String getColor();                      // trả về màu (Green/Yellow/Red)
}
