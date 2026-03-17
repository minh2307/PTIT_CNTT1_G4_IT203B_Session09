package org.example.entity;


import org.example.util.Logger;

public class StandardVehicle extends Vehicle {

    public StandardVehicle(double speed, String type) {
        super(speed, type, 0);
    }

    @Override
    public void move() {
        try {
            Logger.log(this + " đang di chuyển với tốc độ " + speed);

            Thread.sleep((long) (1000 / speed));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Logger.log(this + " bị gián đoạn!");
        }
    }
}
