package org.example.pattern.factory;


import entity.PriorityVehicle;
import entity.StandardVehicle;
import entity.Vehicle;

import java.util.Random;

public class VehicleFactory {

    private static final Random RANDOM = new Random();

    private static final VehicleType[] WEIGHTED_POOL = buildWeightedPool();

    private VehicleFactory() {}

    public static Vehicle create(VehicleType type) {
        if (type.isPriority) {
            return new PriorityVehicle(type.displayName, type.speed, type.sirenSound);
        } else {
            return new StandardVehicle(type.displayName, type.speed);
        }
    }

    public static Vehicle createRandom() {
        VehicleType type = WEIGHTED_POOL[RANDOM.nextInt(WEIGHTED_POOL.length)];
        return create(type);
    }

    public static Vehicle[] createBatch(int count) {
        Vehicle[] batch = new Vehicle[count];
        for (int i = 0; i < count; i++) {
            batch[i] = createRandom();
        }
        return batch;
    }


    private static VehicleType[] buildWeightedPool() {
        return new VehicleType[]{
                VehicleType.MOTORBIKE, VehicleType.MOTORBIKE, VehicleType.MOTORBIKE,
                VehicleType.MOTORBIKE, VehicleType.MOTORBIKE,
                VehicleType.CAR,       VehicleType.CAR,
                VehicleType.CAR,
                VehicleType.TRUCK,
                VehicleType.AMBULANCE
        };
    }
}
