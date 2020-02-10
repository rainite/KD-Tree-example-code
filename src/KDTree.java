import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class KDTree<T extends Point> {
  private List<T> points;
  private KDNode root;
  private double bestDistance = Double.MAX_VALUE;

  KDTree(List<T> points) {
    this.points = points;
    root = createKDTree(new ArrayList<>(points), new KDNode(), 0);
  }

  private KDNode createKDTree(List<T> pointList, KDNode node, int curDimension) {
    // base
    if (pointList.size() == 0) {
      return node;
    }
    List<T> leftList;
    List<T> rightList;
    int randomIndex = (int)(Math.random() * pointList.size());
    T curPoint = pointList.get(randomIndex);

    node.point = curPoint;
    pointList.remove(randomIndex);

    if (curDimension % 2 == 0) {
      leftList = pointList.stream().filter(a -> a.x() <= curPoint.x()).collect(Collectors.toList());
      rightList = pointList.stream().filter(a -> a.x() > curPoint.x()).collect(Collectors.toList());
    } else {
      leftList = pointList.stream().filter(a -> a.y() <= curPoint.y()).collect(Collectors.toList());
      rightList = pointList.stream().filter(a -> a.y() > curPoint.y()).collect(Collectors.toList());
    }
    if (!leftList.isEmpty()) {
      node.left = createKDTree(leftList, new KDNode() , (curDimension + 1) % 2 );
    }
    if (!rightList.isEmpty()) {
      node.right = createKDTree(rightList, new KDNode(), (curDimension + 1) % 2 );
    }

    return node;
  }

  private KDNode findNearestNode(T target, KDNode bestNode, KDNode node, int curDimension) {
    if (node == null || node.visited) {
      return bestNode;
    }

    // update best distance

    if (bestDistance > Math.sqrt(target.distanceSquaredTo(node.point))) {
      bestDistance = Math.sqrt(target.distanceSquaredTo(node.point));
      bestNode = node;
    }
    node.visited = true;
    if (curDimension % 2 == 0) {
      if (target.x()< node.point.x()) {
        bestNode = findNearestNode(target, bestNode, node.left, (curDimension + 1) % 2);
      } else {
        bestNode = findNearestNode(target, bestNode, node.right, (curDimension + 1) % 2);
      }
      // consider about the bad side
      if (bestDistance > Math.abs(node.point.x() - target.x())) {
        bestNode = findNearestNode(target, bestNode, node.left, (curDimension + 1) % 2);
        bestNode = findNearestNode(target, bestNode, node.right, (curDimension + 1) % 2);
      }
    } else {
      if (target.y() < node.point.y()) {
        bestNode = findNearestNode(target, bestNode, node.left, (curDimension + 1) % 2);
      } else {
        bestNode = findNearestNode(target, bestNode, node.right, (curDimension + 1) % 2);
      }
      if (bestDistance > Math.abs(node.point.y() - target.y())) {
        bestNode = findNearestNode(target, bestNode, node.left, (curDimension + 1) % 2);
        bestNode = findNearestNode(target, bestNode, node.right, (curDimension + 1) % 2);
      }
    }
    return bestNode;

  }

  T getNearestNode(T target) {
    KDNode nearestNode = findNearestNode(target, null, root, 0);
    return (T) nearestNode.point;
  }

  KDNode getRoot() {
    return root;
  }

}