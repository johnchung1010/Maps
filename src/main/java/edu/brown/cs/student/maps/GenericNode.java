package edu.brown.cs.student.maps;

import edu.brown.cs.student.common.HasCoordinate;

import java.util.Objects;
import java.util.Set;

/**
 * Abstract class which represents a node in a graph.
 * Takes in an id and coordinates
 */
public abstract class GenericNode
    implements HasCoordinate, Comparable<GenericNode> {

  private String id;
  private double[] coordinates;
  private double minDistance;
  private GenericEdge edgeTo;
  private double heuristic;

  GenericNode(String nodeId, double[] nodeCoords) {
    id = nodeId;
    coordinates = nodeCoords;
    minDistance = Integer.MAX_VALUE;
    edgeTo = null;
  }

  /**
   * Gets the heuristic for this node.
   * @return heuristic
   */
  public double getHeuristic() {
    return heuristic;
  }

  /**
   * Abstract method which sets the heuristic for this node.
   * @param target - the target node of the search the heuristic is for
   * @return the newly calculated heuristic
   */
  public abstract double setHeuristic(GenericNode target);

  /**
   * Gets the edgeTo for this node.
   * In a graph algorithm, the edgeTo represents the previous node on the path to this one
   * @return edgeTo
   */
  public GenericEdge getEdgeTo() {
    return edgeTo;
  }

  /**
   * Setter method for edgeTo.
   * @param edgeTo - new edgeTo
   */
  public void setEdgeTo(GenericEdge edgeTo) {
    this.edgeTo = edgeTo;
  }

  /**
   * Gets the minDistance for this node.
   * In a graph algorithm, the minDistance represents the shortest
   * distance from the start node to this node
   * @return minDistance
   */
  public double getMinDistance() {
    return minDistance;
  }

  /**
   * Setter method for minDistance.
   * @param minDistance - new minDistance
   */
  public void setMinDistance(double minDistance) {
    this.minDistance = minDistance;
  }

  /**
   * Get method for id.
   * @return - id;
   */
  public String getId() {
    return id;
  }

  @Override
  public double[] getCoordinate() {
    return coordinates;
  }

  /**
   * abstract method which locates all edges which connect to this node.
   * @return - a set containing the edges which connect to this node
   * @throws Exception if an error occurs retrieving the edges (ex. from a database)
   */
  abstract Set<GenericEdge> getEdges() throws Exception;

  @Override
  public int compareTo(GenericNode o) {
    if (this.minDistance < o.minDistance) {
      return 1;
    } else {
      return -1;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Node node = (Node) o;
    return getId().equals(node.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
