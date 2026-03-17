package org.example.util;


public class Logger {

    public static synchronized void log(String message) {
        long time = SimulationClock.getTime();
        System.out.println("[Time: " + time + "s] " + message);
    }
}
