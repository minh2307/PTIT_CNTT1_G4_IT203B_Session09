package org.example.engine;

public class SimulationEngine {

    private Intersection intersection;

    public SimulationEngine() {
        this.intersection = new Intersection();
    }

    public void startSimulation() {
        System.out.println("===== BẮT ĐẦU MÔ PHỎNG GIAO THÔNG =====");

        // chạy đèn giao thông (thread riêng)
        Thread trafficLightThread = new Thread(() -> {
            System.out.println("Đèn giao thông bắt đầu hoạt động...");
            intersection.start();
        });

        trafficLightThread.setDaemon(true); // thread nền
        trafficLightThread.start();

        // giả lập hệ thống đang chạy
        while (true) {
            try {
                Thread.sleep(3000);
                System.out.println("Hệ thống đang vận hành bình thường...");
                System.out.println("Trạng thái đèn hiện tại: " + intersection.getStateColor());
                System.out.println("--------------------------------------");
            } catch (InterruptedException e) {
                System.out.println("Hệ thống bị gián đoạn!");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}