import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    Point[] testPoints = new Point[7];
    testPoints[0] = new Point(3,3);
    testPoints[1] = new Point(2,7);
    testPoints[2] = new Point(1,1);
    testPoints[3] = new Point(4,2);
    testPoints[4] = new Point(7,5);
    testPoints[5] = new Point(6,8);
    testPoints[6] = new Point(8,0.66666);
    List<Point> testList = new ArrayList<>();
    testList.addAll(Arrays.asList(testPoints));
    PointSet testNaiveSet = new NaivePointSet(testList);
    PointSet testKDTreeSet = new KDTreePointSet(testList);
    System.out.println(testNaiveSet.nearest(2.9,1.4));
    System.out.println(testKDTreeSet.nearest(2.9,1.4));
  }
}
