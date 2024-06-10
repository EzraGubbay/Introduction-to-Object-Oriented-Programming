import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class LineTest {

    @Test
    void intersectionWithTest() {
        Line trajectory = new Line(new Point( 318.3 , 10.96 ), new Point( 304.58 , 4.87 ));
        Line side = new Line(new Point( 790 , 10 ) , new Point( 10 , 10 ));
        Point intersectionTrajectory = trajectory.intersectionWith(side);
        Point intersectionSide = side.intersectionWith(trajectory);
        System.out.println(intersectionTrajectory.toString());
        System.out.println(intersectionSide.toString());
    }

    @Test
    void thresholdTest(){
        assertTrue(Point.threshold(10.0, 9.999999999999977));
        assertTrue(Point.threshold(9.999999999999977, 10.0));
    }

    @Test
    void pointInSegmentTest(){
        Point intersection = new Point(316.1372413793103, 9.999999999999977);
        //assertTrue(316.1372413793103 <= 318.3 && 316.1372413793103 >= 304.58);
        Line trajectory = new Line(new Point( 318.3 , 10.96 ), new Point( 304.58 , 4.87 ));
        Line side = new Line(new Point( 790 , 10 ) , new Point( 10 , 10 ));
        assertTrue(trajectory.pointInSegment(intersection));
        assertTrue(side.pointInSegment(intersection));
    }
}