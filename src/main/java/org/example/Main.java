package org.example;

import org.example.engine.Intersection;
import org.example.entity.Vehicle;
import org.example.pattern.factory.VehicleFactory;

public class Main {

    public static void main(String[] args) {

        System.out.println("===== MÔ PHỎNG GIAO THÔNG =====");

        Intersection intersection = new Intersection();

        // ================= 🚦 TRAFFIC LIGHT =================
        Thread trafficThread = new Thread(() -> {
            System.out.println("🚦 Đèn giao thông bắt đầu...");

            try {
                while (!Thread.currentThread().isInterrupted()) {
                    intersection.start(); // mỗi lần = 1 state (GREEN → YELLOW → RED)
                }
            } catch (Exception e) {
                System.out.println("🚦 Đèn giao thông dừng!");
            }
        });

        trafficThread.setDaemon(true);
        trafficThread.start();

        // ================= 🚗 VEHICLE GENERATOR =================
        Thread generator = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {

                    Thread.sleep(2000);

                    Vehicle v = VehicleFactory.createVehicle(intersection);

                    System.out.println("🚘 Tạo " + v.getName());

                    intersection.registerObserver(v);

                    new Thread(v).start();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("🚘 Dừng tạo xe");
            }
        });

        generator.start();

        // ================= 📊 MONITOR =================
        Thread monitor = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {

                    Thread.sleep(3000);

                    System.out.println("\n===== TRẠNG THÁI HỆ THỐNG =====");
                    System.out.println("Đèn: " + intersection.getStateColor());
                    System.out.println("Xe đang chờ: " + intersection.getWaitingSize());
                    System.out.println("Xe đã qua: " + intersection.getPassedVehicles());
                    System.out.println("================================\n");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        monitor.setDaemon(true);
        monitor.start();

        // ================= ⏳ CHẠY 30 GIÂY =================
        try {
            Thread.sleep(30000); // chạy 30s
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // ================= 🛑 STOP =================
        System.out.println("🚨 Dừng hệ thống...");

        generator.interrupt();
        trafficThread.interrupt();

        System.out.println("===== KẾT THÚC MÔ PHỎNG =====");
    }
}