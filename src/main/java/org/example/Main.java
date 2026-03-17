package org.example;

import org.example.engine.Intersection;
import org.example.entity.PriorityVehicle;
import org.example.entity.Vehicle;
import org.example.pattern.factory.VehicleFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("===== MÔ PHỎNG GIAO THÔNG REAL-TIME =====");

        Intersection intersection = new Intersection();

        // 🚦 chạy đèn
        Thread trafficThread = new Thread(intersection::start);
        trafficThread.setDaemon(true);
        trafficThread.start();

        // 🚗 sinh xe liên tục
        Thread generator = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000); // mỗi 1s tạo 1 xe

                    Vehicle v = VehicleFactory.createVehicle(intersection);
                    intersection.registerObserver(v);

                    new Thread(v).start();

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        generator.start();

        // 📊 monitor hệ thống
        while (true) {
            try {
                Thread.sleep(3000);

                System.out.println("\n===== TRẠNG THÁI HỆ THỐNG =====");
                System.out.println("Đèn: " + intersection.getStateColor());
                System.out.println("Xe đang chờ: " + intersection.getWaitingSize());
                System.out.println("================================\n");

            } catch (InterruptedException e) {
                break;
            }
        }
    }
}