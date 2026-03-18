package util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static Logger instance;
    private static final Object INSTANCE_LOCK = new Object();

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    // private constructor (Singleton)
    private Logger() {}

    public static Logger getInstance() {
        synchronized (INSTANCE_LOCK) {
            if (instance == null) {
                instance = new Logger();
            }
            return instance;
        }
    }

    // Core log method (thread-safe)
    public void log(String message) {
        synchronized (System.out) {
            String time = LocalTime.now().format(formatter);
            String threadName = Thread.currentThread().getName();

            System.out.println("[Time: " + time + "] [" + threadName + "] " + message);
        }
    }

    // Helper method cho vehicle
    public void logVehicle(String vehicleType, int id, String action) {
        log(vehicleType + " #" + id + " " + action);
    }

    // Helper method cho system event
    public void logSystem(String message) {
        log("[SYSTEM] " + message);
    }
}