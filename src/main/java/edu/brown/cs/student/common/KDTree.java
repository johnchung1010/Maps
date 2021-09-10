package edu.brown.cs.student.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class representing a K-d tree.
 *
 * @param <T> Object that implements the HasCoordinate interface
 */
public class KDTree<T extends HasCoordinate> {

  private final int dimension;
  private final KDNode<T> root;
  private int numNeighbors;
  private double radius;
  private double[] target;

  /**
   * Constructor.
   *
   * @param dimensionIn Number of dimensions
   * @param data        List of objects that will make up the K-d tree
   */
  public KDTree(int dimensionIn, List<T> data) {
    dimension = dimensionIn;

    if (data.size() == 0) {
      root = null;
    } else {
      root = new KDNode<>(data, 0);
    }
  }

  /**
   * Return the k-nearest neighbors to a given location.
   *
   * @param k              Desired number of neighbors
   * @param targetPosition Target position
   * @return List of k-nearest neighbors
   */
  public List<KDNode<T>> getKNearestNeighbors(int k, double[] targetPosition) {
    List<KDNode<T>> nearestNeighbors = new ArrayList<>();
    if (k == 0) {
      return nearestNeighbors;
    } else {
      numNeighbors = k;
      target = targetPosition;
      return neighborsHelper(root, nearestNeighbors, 0);
    }
  }

  /**
   * Helper function for getKNearestNeighbors which handles recurring over the K-d tree.
   *
   * @param currNode Current node of K-d tree
   * @param nearest  K-nearest neighbors
   * @param depth    Level of K-d tree
   * @return List of k-nearest neighbors
   */
  public List<KDNode<T>> neighborsHelper(KDNode<T> currNode, List<KDNode<T>> nearest, int depth) {
    if (currNode == null) {
      return nearest;
    }

    T datum = currNode.getDatum();
    double distance = calcEuclideanDistance(datum.getCoordinate(), target);
    currNode.setDistance(distance);

    updateNearest(currNode, nearest);

    int axis = depth % dimension;
    double axisDist = Math.abs(target[axis] - datum.getCoordinate()[axis]);
    double farthestNeighborDist = nearest.get(nearest.size() - 1)
        .getDistance();

    if (nearest.size() < numNeighbors || axisDist < farthestNeighborDist) {
      List<KDNode<T>> nearestFromLeft = neighborsHelper(
          currNode.getLeftChild(), nearest, depth + 1);
      return neighborsHelper(currNode.getRightChild(), nearestFromLeft, depth + 1);
    } else if (target[axis] <= datum.getCoordinate()[axis]) {
      return neighborsHelper(currNode.getLeftChild(), nearest, depth + 1);
    } else {
      return neighborsHelper(currNode.getRightChild(), nearest, depth + 1);
    }
  }

  /**
   * Update List of nearest neighbors.
   *
   * @param neighbor Nearest neighbor candidate
   * @param nearest  List of nearest neighbors
   */
  private void updateNearest(KDNode<T> neighbor, List<KDNode<T>> nearest) {
    if (nearest.size() == 0) {
      nearest.add(neighbor);
      return;
    }

    Random rand = new Random();
    for (int i = 0; i < nearest.size(); i++) {
      if (nearest.get(i).getDistance() > neighbor.getDistance()) {
        KDNode<T> temp = nearest.get(i);
        nearest.set(i, neighbor);
        neighbor = temp;
        // Neighbors with equal distances chosen randomly.
      } else if (nearest.get(i).getDistance() == neighbor.getDistance()) {
        int n = rand.nextInt(2);
        if (n == 0) {
          KDNode<T> temp = nearest.get(i);
          nearest.set(i, neighbor);
          neighbor = temp;
        }
      }
    }
    // If k neighbors have not been found, insert the neighbor to the list.
    if (nearest.size() < numNeighbors) {
      nearest.add(neighbor);
    }
  }

  /**
   * Return all KDNodes within a radius to a given location.
   *
   * @param r              Search radius
   * @param targetPosition The position around which to search
   * @return List of KDNodes within the search radius
   */
  public List<KDNode<T>> getWithinRadius(double r, double[] targetPosition) {
    radius = r;
    target = targetPosition;
    return radiusHelper(root, new ArrayList<>(), 0);
  }

  /**
   * Helper function for getWithinRadius which handles recurring over the K-d tree.
   *
   * @param currNode Current node of K-d tree
   * @param inRange  KDNodes within the search radius
   * @param depth    Level of K-d tree
   * @return List of KDNodes within the search radius
   */
  public List<KDNode<T>> radiusHelper(KDNode<T> currNode, List<KDNode<T>> inRange, int depth) {
    if (currNode == null) {
      return inRange;
    }

    T datum = currNode.getDatum();
    double distance = calcEuclideanDistance(datum.getCoordinate(), target);
    currNode.setDistance(distance);

    if (distance <= radius) {
      updateWithinRadius(currNode, inRange);
    }

    int axis = depth % dimension;
    double axisDist = Math.abs(target[axis] - datum.getCoordinate()[axis]);

    if (inRange.size() == 0 || axisDist < radius) {
      List<KDNode<T>> nearestFromLeft = radiusHelper(currNode.getLeftChild(), inRange, depth + 1);
      return radiusHelper(currNode.getRightChild(), nearestFromLeft, depth + 1);
    } else if (target[axis] <= datum.getCoordinate()[axis]) {
      return radiusHelper(currNode.getLeftChild(), inRange, depth + 1);
    } else {
      return radiusHelper(currNode.getRightChild(), inRange, depth + 1);
    }
  }

  /**
   * Update List of KDNodes within the search radius.
   *
   * @param node    KDNode within the search radius.
   * @param inRange List of KDNodes within the search radius.
   */
  private void updateWithinRadius(KDNode<T> node, List<KDNode<T>> inRange) {
    if (inRange.size() == 0) {
      inRange.add(node);
      return;
    }

    for (int i = 0; i < inRange.size(); i++) {
      if (inRange.get(i).getDistance() > node.getDistance()) {
        KDNode<T> temp = inRange.get(i);
        inRange.set(i, node);
        node = temp;
      }
    }
    inRange.add(node);
  }

  /**
   * Calculate the euclidean distance between two coordinates.
   *
   * @param start Start coordinate
   * @param end   End coordinate
   * @return Calculated euclidean distance
   */
  public double calcEuclideanDistance(double[] start, double[] end) {
    double sum = 0;
    for (int i = 0; i < start.length; i++) {
      sum += Math.pow(start[i] - end[i], 2);
    }
    return Math.sqrt(sum);
  }

  /**
   * Class representing a node within a KDTree.
   *
   * @param <t> Object that implements the HasCoordinate interface
   */
  public class KDNode<t extends HasCoordinate> {

    private final t datum;
    private KDNode<t> leftChild = null;
    private KDNode<t> rightChild = null;
    private double distance;

    /**
     * Constructor.
     *
     * @param data  List of objects that will make up the K-d tree
     * @param depth Level of K-d tree
     */
    public KDNode(List<t> data, int depth) {
      int axis = depth % dimension;
      int mid = data.size() / 2;

      data.sort(new HasCoordinateComparator(axis, true));
      List<t> leftSubtree = data.subList(0, mid);
      List<t> rightSubtree = data.subList(mid + 1, data.size());

      datum = data.get(mid);
      if (leftSubtree.size() != 0) {
        leftChild = new KDNode<>(leftSubtree, depth + 1);
      }
      if (rightSubtree.size() != 0) {
        rightChild = new KDNode<>(rightSubtree, depth + 1);
      }
    }

    /**
     * Getter.
     *
     * @return Datum stored in node
     */
    public t getDatum() {
      return datum;
    }

    /**
     * Getter.
     *
     * @return Node's left child
     */
    public KDNode<t> getLeftChild() {
      return leftChild;
    }

    /**
     * Getter.
     *
     * @return Node's right child
     */
    public KDNode<t> getRightChild() {
      return rightChild;
    }

    /**
     * Getter.
     *
     * @return Node's distance from target
     */
    public double getDistance() {
      return distance;
    }

    /**
     * Setter.
     *
     * @param distanceIn Distance from target
     */
    public void setDistance(double distanceIn) {
      distance = distanceIn;
    }
  }
}
