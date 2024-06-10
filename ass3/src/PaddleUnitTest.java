import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaddleUnitTest {

    @Test
    void testGreaterThanOrEqualThresholdResult() {
        int i = 3;
        double collisionX = 269.69664877423196;
        double minFactor = 265.2, maxFactor = 355.6;
        assertTrue(Point.greaterThanOrEqualThreshold(collisionX, minFactor));
    }

    @Test
    void testLessThanOrEqualThresholdResult() {
        int i = 3;
        double collisionX = 269.69664877423196;
        double minFactor = 345.2, maxFactor = 275.6;
        assertTrue(Point.lessThanOrEqualThreshold(collisionX, maxFactor));
    }

    @Test
    void testGreaterThanThreshold(){
        int i = 3;
        double collisionX = 346.6194842834482;
        double minFactor = 345.2, maxFactor = 355.6;
        assertTrue(Point.nGreaterThanMThreshold(collisionX, minFactor));
    }

    @Test
    void testLessThanThreshold(){
        int i = 3;
        double collisionX = 346.6194842834482;
        double minFactor = 345.2, maxFactor = 355.6;
        assertTrue(Point.nLessThanMThreshold(minFactor, collisionX));
    }

}