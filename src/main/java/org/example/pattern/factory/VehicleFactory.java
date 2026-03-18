package org.example.pattern.factory;

import org.example.engine.Intersection;
import org.example.entity.PriorityVehicle;
import org.example.entity.StandardVehicle;
import org.example.entity.Vehicle;
import java.util.Random;

public class VehicleFactory {

    private static final Random random = new Random();
    private static int counter = 1;

    public static Vehicle createVehicle(Intersection intersection) {

        int chance = random.nextInt(100); // 0 -> 99
        int speed;
        String id = String.valueOf(counter++);

        // 🚑 20% xe cứu thương
        if (chance < 10) {
            speed = 500 + random.nextInt(500); // nhanh hơn
            System.out.println("🚑 Tạo xe cứu thương #" + id);
            return new PriorityVehicle(id, speed, intersection);
        }

        // 🚗 90% còn lại là xe thường (chia loại)
        int type = random.nextInt(3);

        switch (type) {
            case 0:
                speed = 500 + random.nextInt(500); // xe máy nhanh
                System.out.println("🏍️ Tạo xe máy #" + id);
                return new StandardVehicle(id, speed, intersection) {
                    @Override
                    public String getName() {
                        return "🏍️ Xe máy #" + id;
                    }
                };

            case 1:
                speed = 1000 + random.nextInt(1000); // ô tô trung bình
                System.out.println("🚗 Tạo ô tô #" + id);
                return new StandardVehicle(id, speed, intersection) {
                    @Override
                    public String getName() {
                        return "🚗 Ô tô #" + id;
                    }
                };

            default:
                speed = 1500 + random.nextInt(1500); // xe tải chậm
                System.out.println("🚛 Tạo xe tải #" + id);
                return new StandardVehicle(id, speed, intersection) {
                    @Override
                    public String getName() {
                        return "🚛 Xe tải #" + id;
                    }
                };
        }
    }
}