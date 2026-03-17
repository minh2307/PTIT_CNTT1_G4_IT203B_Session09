package org.example;

import org.example.engine.Intersection;
import org.example.entity.PriorityVehicle;
import org.example.entity.Vehicle;
import org.example.pattern.factory.VehicleFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("===== MÔ PHỎNG GIAO THÔNG =====");

        Intersection intersection = new Intersection();
        List<Vehicle> vehicles = new ArrayList<>();

        int maxPriority = 2; // 👈 CHỈ cho tối đa 2 xe cứu thương
        int currentPriority = 0;

        System.out.println("===== TẠO PHƯƠNG TIỆN =====");

        for (int i = 0; i < 10; i++) {

            Vehicle v = VehicleFactory.createVehicle(intersection);

            // kiểm soát số xe cứu thương
            if (v instanceof PriorityVehicle) {
                if (currentPriority >= maxPriority) {
                    // nếu vượt quá → tạo lại xe thường
                    v = VehicleFactory.createVehicle(intersection);

                    // ép lại nếu vẫn ra xe cứu thương
                    while (v instanceof PriorityVehicle) {
                        v = VehicleFactory.createVehicle(intersection);
                    }
                } else {
                    currentPriority++;
                }
            }

            vehicles.add(v);
            intersection.registerObserver(v);
            new Thread(v).start();
        }

        // chạy đèn giao thông
        Thread trafficThread = new Thread(intersection::start);
        trafficThread.setDaemon(true);
        trafficThread.start();

        System.out.println("===== HỆ THỐNG ĐANG CHẠY =====");
    }
}