package org.example;

import org.example.engine.Intersection;
import org.example.engine.SimulationEngine;
import org.example.entity.Vehicle;
import org.example.pattern.factory.VehicleFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("===== HỆ THỐNG MÔ PHỎNG GIAO THÔNG THÔNG MINH =====");

        // ===== KHỞI TẠO NGÃ TƯ =====
        Intersection intersection = new Intersection();

        // ===== DANH SÁCH XE =====
        List<Vehicle> vehicles = new ArrayList<>();

        System.out.println("===== TẠO PHƯƠNG TIỆN =====");

        // Tạo ngẫu nhiên nhiều xe
        for (int i = 0; i < 10; i++) {
            Vehicle v = VehicleFactory.createVehicle(intersection);

            vehicles.add(v);

            // đăng ký observer
            intersection.registerObserver(v);

            // chạy thread cho xe
            new Thread(v).start();
        }

        // ===== ENGINE =====
        SimulationEngine engine = new SimulationEngine();

        // chạy đèn giao thông (thread riêng)
        Thread trafficThread = new Thread(() -> {
            System.out.println("🚦 Đèn giao thông bắt đầu hoạt động...");
            intersection.start();
        });

        trafficThread.setDaemon(true);
        trafficThread.start();

        // ===== MONITORING =====
        long startTime = System.currentTimeMillis();
        int passedVehicles = 0;

        System.out.println("===== BẮT ĐẦU GIÁM SÁT =====");

        while (true) {
            try {
                Thread.sleep(3000);

                long currentTime = (System.currentTimeMillis() - startTime) / 1000;

                System.out.println("[Time: " + currentTime + "s] Trạng thái đèn: "
                        + intersection.getStateColor());

                // thống kê đơn giản (giả lập)
                passedVehicles += (int) (Math.random() * 3);

                System.out.println("[Time: " + currentTime + "s] Số xe đã qua: "
                        + passedVehicles);

                // giả lập kẹt xe
                if (Math.random() < 0.1) {
                    System.out.println("[CẢNH BÁO] Có dấu hiệu kẹt xe tại ngã tư!");
                }

                System.out.println("------------------------------------------------");

            } catch (InterruptedException e) {
                System.out.println("Hệ thống bị dừng!");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}