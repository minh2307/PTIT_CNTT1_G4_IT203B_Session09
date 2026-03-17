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

        int type = random.nextInt(2); // 0 hoặc 1
        int speed = 1000 + random.nextInt(2000); // 1s - 3s

        String id = String.valueOf(counter++);

        if (type == 0) {
            System.out.println("Tạo xe thường #" + id);
            return new StandardVehicle(id, speed, intersection);
        } else {
            System.out.println("Tạo xe cứu thương #" + id);
            return new PriorityVehicle(id, speed, intersection);
        }
    }
}