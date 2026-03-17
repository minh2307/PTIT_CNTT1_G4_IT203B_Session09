package org.example;

import org.example.engine.Intersection;
import org.example.entity.Vehicle;
import org.example.pattern.factory.VehicleFactory;

public class Main {

    private static final int MAX_CYCLES = 10; // 🔥 số lần lặp đèn

    public static void main(String[] args) {

        System.out.println("===== MÔ PHỎNG GIAO THÔNG (CÓ GIỚI HẠN) =====");

        Intersection intersection = new Intersection();

        // ================= 🚦 TRAFFIC LIGHT =================
        Thread trafficThread = new Thread(() -> {
            System.out.println("🚦 Đèn giao thông bắt đầu...");

            for (int i = 1; i <= MAX_CYCLES; i++) {
                System.out.println("\n🔄 Chu kỳ đèn #" + i);

                intersection.start(); // 1 vòng state (GREEN → YELLOW → RED)
            }

            System.out.println("🛑 Đèn giao thông đã dừng!");
        });

        trafficThread.start();

        // ================= 🚗 VEHICLE GENERATOR =================
        Thread generator = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {

                    Thread.sleep(1000);

                    Vehicle v = VehicleFactory.createVehicle(intersection);
                    intersection.registerObserver(v);

                    new Thread(v).start();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
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

        // ================= ⏳ CHỜ ĐÈN CHẠY XONG =================
        try {
            trafficThread.join(); // đợi đèn chạy xong
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // ================= 🛑 DỪNG HỆ THỐNG =================
        System.out.println("🚨 Dừng tạo xe...");

        generator.interrupt(); // dừng sinh xe

        System.out.println("===== KẾT THÚC MÔ PHỎNG =====");
    }
}