import org.example.engine.Intersection;
import org.example.entity.Vehicle;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class ConcurrencyTest {

    @Test
    void stressTest100Vehicles() throws InterruptedException {

        Intersection intersection = new Intersection();

        int numVehicles = 100;
        CountDownLatch latch = new CountDownLatch(numVehicles);

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < numVehicles; i++) {

            Vehicle v = new Vehicle("V" + i, 60, intersection) {
                @Override
                public String getName() {
                    return "🚗 TestCar " + id;
                }

                @Override
                public void run() {
                    try {
                        intersection.requestEnter(this);

                        // giả lập đi qua
                        Thread.sleep(50);

                        intersection.leaveIntersection();

                    } catch (Exception e) {
                        fail("❌ Collision hoặc lỗi thread: " + e.getMessage());
                    } finally {
                        latch.countDown();
                    }
                }
            };

            Thread t = new Thread(v);
            threads.add(t);
        }

        // 🚀 start cùng lúc
        threads.forEach(Thread::start);

        // ⏳ chờ tất cả hoàn thành
        latch.await();

        // ✅ ASSERT KHÔNG MẤT XE
        assertEquals(numVehicles, intersection.getPassedVehicles());

        // ✅ KHÔNG CÒN XE CHỜ
        assertEquals(0, intersection.getWaitingSize());
    }
}