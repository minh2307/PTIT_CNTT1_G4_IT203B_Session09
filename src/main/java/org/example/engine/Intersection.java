package org.example.engine;

import org.example.entity.PriorityVehicle;
import org.example.entity.Vehicle;
import org.example.exception.CollisionException;
import org.example.exception.TrafficJamException;
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

    private final int JAM_THRESHOLD = 7;
    private boolean isTrafficJam = false;

    private volatile TrafficLightState currentState;
    private final List<Observer> observers = new ArrayList<>();

    private final Queue<Vehicle> waitingQueue = new LinkedList<>();

    private volatile boolean isOccupied = false;

    public Intersection() {
        this.currentState = new GreenState();
    }

    public synchronized int getPassedVehicles() {
        return passedVehicles;
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
    public synchronized void requestEnter(Vehicle vehicle)
            throws TrafficJamException, CollisionException {

        // thêm vào queue nếu chưa có
        if (!waitingQueue.contains(vehicle)) {
            waitingQueue.add(vehicle);
        }

        // ================= 🚑 PRIORITY =================
        if (vehicle instanceof PriorityVehicle) {

            Logger.log(vehicle.getName() + " 🚨 xin ưu tiên!");

            if (isOccupied) {
                checkTrafficJam();
                throw new CollisionException("💥 Va chạm khi ưu tiên!");
            }

            isOccupied = true;
            waitingQueue.remove(vehicle);

            Logger.log("🚑 " + vehicle.getName() + " VƯỢT ĐÈN 🚨");

            passedVehicles++;
            return;
        }

        // ================= 🚗 NORMAL =================
        boolean isGreen = "GREEN".equals(currentState.getColor());

        boolean hasPriority = waitingQueue.stream()
                .anyMatch(v -> v instanceof PriorityVehicle);

        if (!hasPriority && waitingQueue.peek() == vehicle && isGreen) {

            if (isOccupied) {
                throw new CollisionException("💥 Va chạm do lock lỗi!");
            }

            isOccupied = true;
            waitingQueue.poll();

            Logger.log(vehicle.getName() + " đang đi qua ngã tư");

            passedVehicles++;
            return;
        }

        // ================= WAIT =================
        Logger.log(vehicle.getName() + " đang dừng lại...");
        checkTrafficJam();

        throw new RuntimeException("WAIT");
    }

    // ================= TRAFFIC JAM =================
    private void checkTrafficJam() throws TrafficJamException {

        boolean isRed = "RED".equals(currentState.getColor());

        if (waitingQueue.size() >= JAM_THRESHOLD && isRed) {

            if (!isTrafficJam) {
                isTrafficJam = true;
                throw new TrafficJamException("⚠️ KẸT XE NGHIÊM TRỌNG!");
            }

        } else {
            isTrafficJam = false;
        }
    }

    public synchronized void leaveIntersection() {
        isOccupied = false;
    }

    public synchronized int getWaitingSize() {
        return waitingQueue.size();
    }

    // ================= RUN =================
    public void start() {
        currentState.handle(this);
    }
}