package org.example.engine;


import org.example.entity.PriorityVehicle;
import org.example.entity.Vehicle;
import org.example.pattern.observer.Observer;
import org.example.pattern.observer.Subject;
import org.example.pattern.state.GreenState;
import org.example.pattern.state.TrafficLightState;
import org.example.util.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Intersection implements Subject {
    private int passedVehicles = 0;
    private int trafficJamCount = 0;
    private final int JAM_THRESHOLD = 7; // >7 xe = kẹt
    private boolean isTrafficJam = false;

    private TrafficLightState currentState;
    private final List<Observer> observers = new ArrayList<>();

    // hàng đợi xe
    private final Queue<Vehicle> waitingQueue = new LinkedList<>();

    // trạng thái ngã tư
    private boolean isOccupied = false;

    public Intersection() {
        this.currentState = new GreenState();
    }

    public synchronized int getPassedVehicles() {
        return passedVehicles;
    }

    public synchronized int getTrafficJamCount() {
        return trafficJamCount;
    }

    // ================= STATE =================
    public synchronized void setState(TrafficLightState state) {
        this.currentState = state;
        notifyObservers();
    }

    public synchronized String getStateColor() {
        return currentState.getColor();
    }

    // ================= OBSERVER =================
    @Override
    public synchronized void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public synchronized void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public synchronized void notifyObservers() {
        for (Observer o : observers) {
            o.update(currentState.getColor());
        }
    }

    // ================= CORE =================
    public synchronized void requestEnter(Vehicle vehicle) {

        if (!waitingQueue.contains(vehicle)) {
            waitingQueue.add(vehicle);
        }

        // 🔥 CHECK KẸT XE
        if (waitingQueue.size() >= JAM_THRESHOLD) {

            if (!isTrafficJam) {
                isTrafficJam = true;
                Logger.log("⚠️ KẸT XE!");
            }

        } else {

            if (isTrafficJam) {
                isTrafficJam = false;
                Logger.log("✅ HẾT KẸT XE");
            }
        }

        // 🚑 XE CỨU THƯƠNG
        if (vehicle instanceof PriorityVehicle) {

            Logger.log(vehicle.getName() + " 🚨 xin ưu tiên!");

            if (!isOccupied) {
                isOccupied = true;
                waitingQueue.remove(vehicle);

                Logger.log("🚑 " + vehicle.getName() + " VƯỢT ĐÈN 🚨");

                passedVehicles++;
                return;
            }
        }

        // 🚗 XE THƯỜNG
        boolean isGreen = "GREEN".equals(currentState.getColor());

        if (waitingQueue.peek() == vehicle && !isOccupied) {

            if (isGreen || vehicle instanceof PriorityVehicle) {
                isOccupied = true;
                waitingQueue.poll();

                Logger.log(vehicle.getName() + " đang đi qua ngã tư");

                passedVehicles++;
                return;
            }
        }

        throw new RuntimeException("WAIT");
    }

    public synchronized void leaveIntersection() {
        isOccupied = false;
    }

    // 📊 dùng để monitor
    public synchronized int getWaitingSize() {
        return waitingQueue.size();
    }

    // ================= RUN =================
    public void start() {
        while (true) {
            currentState.handle(this);
        }
    }
}