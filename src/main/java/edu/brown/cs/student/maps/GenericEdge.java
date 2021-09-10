package edu.brown.cs.student.maps;

import java.util.Objects;

/**
 * Abstract class which outlines an edge in a graph.
 * Takes in an id, a start node, and an end node
 */
public abstract class GenericEdge {

  private String id;
  private GenericNode start;
  private GenericNode end;

  GenericEdge(String edgeId, GenericNode startNode, GenericNode endNode) {
    id = edgeId;
    start = startNode;
    end = endNode;
  }

  /**
   * Get method for id.
   * @return - id;
   */
  public String getId() {
    return id;
  }

  /**
   * Get method for start node.
   * @return - start;
   */
  public GenericNode getStart() {
    return start;
  }

  /**
   * Get method for end node.
   * @return - end;
   */
  public GenericNode getEnd() {
    return end;
  }

  /**
   * abstract method which computes and returns the weight of this edge.
   * @return - the weight of this edge
   */
  abstract double getWeight();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GenericEdge that = (GenericEdge) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
