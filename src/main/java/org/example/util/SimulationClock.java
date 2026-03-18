package org.example.util;

public class SimulationClock {

    private static final long startTime = System.currentTimeMillis();

    public static long getTime() {
        return (System.currentTimeMillis() - startTime) / 1000;
    }
}