import org.example.engine.Intersection;
import org.example.pattern.state.GreenState;
import org.example.pattern.state.RedState;
import org.example.pattern.state.YellowState;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
//GREEN → YELLOW → RED → GREEN
class TrafficLightStateTest {

    @Test
    void testInitialState() {
        Intersection intersection = new Intersection();

        assertEquals("GREEN", intersection.getStateColor());
    }

    @Test
    void testGreenToYellow() {
        Intersection intersection = new Intersection();

        intersection.setState(new YellowState());

        assertEquals("YELLOW", intersection.getStateColor());
    }

    @Test
    void testYellowToRed() {
        Intersection intersection = new Intersection();

        intersection.setState(new YellowState());
        intersection.setState(new RedState());

        assertEquals("RED", intersection.getStateColor());
    }

    @Test
    void testFullCycle() {
        Intersection intersection = new Intersection();

        intersection.setState(new GreenState());
        assertEquals("GREEN", intersection.getStateColor());

        intersection.setState(new YellowState());
        assertEquals("YELLOW", intersection.getStateColor());

        intersection.setState(new RedState());
        assertEquals("RED", intersection.getStateColor());
    }
}