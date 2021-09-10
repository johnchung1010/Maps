package edu.brown.cs.student.maps;

import java.util.HashMap;
import java.util.Set;

/**
 * Class representing the cache.
 */
public final class Cache {

  private HashMap<String, Set<GenericEdge>> nodes = new HashMap<>();
  private static final Cache INSTANCE = new Cache();

  /**
   * Getter.
   *
   * @return Hashmap whose keys are Nodes' ids and values are nodes' edges.
   */
  public HashMap<String, Set<GenericEdge>> getNodes() {
    return nodes;
  }

  /**
   * Getter.
   *
   * @return The only instance of the Cache object.
   */
  public static Cache getInstance() {
    return INSTANCE;
  }

  /**
   * Reset the cache.
   */
  public void resetNodes() {
    nodes = new HashMap<>();
  }
}
