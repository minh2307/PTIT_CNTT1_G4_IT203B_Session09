package org.example.engine;


import org.example.entity.PriorityVehicle;
import org.example.entity.Vehicle;
import org.example.pattern.observer.Observer;
import org.example.pattern.observer.Subject;
import org.example.pattern.state.GreenState;
import org.example.pattern.state.TrafficLightState;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Intersection implements Subject {

    private TrafficLightState currentState;
    private final List<Observer> observers = new ArrayList<>();

    // hàng đợi xe
    private final Queue<Vehicle> waitingQueue = new LinkedList<>();

    // trạng thái ngã tư
    private boolean isOccupied = false;

    public Intersection() {
        this.currentState = new GreenState();
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

    // ================= CORE LOGIC =================
    public synchronized void requestEnter(Vehicle vehicle) {

        // thêm vào hàng đợi nếu chưa có
        if (!waitingQueue.contains(vehicle)) {
            waitingQueue.add(vehicle);
        }

        // 🚑 ===== XE CỨU THƯƠNG =====
        if (vehicle instanceof PriorityVehicle) {

            System.out.println(vehicle.getName() + " 🚨 xin ưu tiên!");

            if (!isOccupied) {
                isOccupied = true;

                // 🔥 XOÁ KHỎI HÀNG ĐỢI
                waitingQueue.remove(vehicle);

                System.out.println("🚑 " + vehicle.getName() + " VƯỢT ĐÈN 🚨 và đi ngay!");
                return;
            }
        }

        // 🚗 ===== XE THƯỜNG =====
        boolean isGreen = "GREEN".equals(currentState.getColor());

        if (waitingQueue.peek() == vehicle && !isOccupied) {

            if (isGreen || vehicle instanceof PriorityVehicle) {

                isOccupied = true;

                // 🔥 XOÁ KHỎI HÀNG ĐỢI (đúng chuẩn FIFO)
                waitingQueue.poll();

                System.out.println("🚗 " + vehicle.getName() + " ĐANG QUA NGÃ TƯ");
                return;
            }
        }

        // ❌ không được đi
        throw new RuntimeException("WAIT");
    }

    // xe rời ngã tư
    public synchronized void leaveIntersection() {
        isOccupied = false;
    }

    // ================= RUN =================
    public void start() {
        while (true) {
            currentState.handle(this);
        }
    }

    public synchronized int getWaitingSize() {
        return waitingQueue.size();
    }
}