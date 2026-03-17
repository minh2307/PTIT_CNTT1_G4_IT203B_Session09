package org.example.entity;

public class PriorityVehicle extends Vehicle {

    private static final int PRIORITY_LEVEL = 10;
    private final String sirenSound;

    public PriorityVehicle(String type, int speed, String sirenSound) {
        super(type, speed, PRIORITY_LEVEL);
        this.sirenSound = sirenSound;
    }

    @Override
    public boolean canRunRedLight() {
        return true;
    }

    @Override
    public void onEnterIntersection() {
        System.out.printf("  🚨 %s phát tín hiệu \"%s\" — các xe khác nhường đường!%n",
                this, sirenSound);
    }

    @Override
    public void onExitIntersection() {
        setPassed(true);
        System.out.printf("  ✓ %s đã qua ngã tư (ưu tiên).%n", this);
    }

    public String getSirenSound() { return sirenSound; }

    @Override
    public void run() {
        Thread.currentThread().setName(type + "-" + id);
    }
}
