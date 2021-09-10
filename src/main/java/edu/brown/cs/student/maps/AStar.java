package edu.brown.cs.student.maps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Class representing the A* Search Algorithm.
 */
public class AStar {

  private GenericNode start;
  private GenericNode end;

  /**
   * Constructor.
   *
   * @param startIn Start node
   * @param endIn End node
   */
  public AStar(GenericNode startIn, GenericNode endIn) {
    start = startIn;
    end = endIn;
  }

  /**
   * Setter.
   *
   * @param startIn Start node
   */
  public void setStart(GenericNode startIn) {
    start = startIn;
  }

  /**
   * Setter.
   * @param endIn End node
   */
  public void setEnd(GenericNode endIn) {
    end = endIn;
  }

  /**
   * Function which gets the shortest path form the start Node to the end Node.
   * This algorithm follows te A* shortest path algorithm
   * @return - an ArrayList of GenericEdges representing the shortest path
   */
  public ArrayList<GenericEdge> getShortestPath() {
    // Create fringe heap.
    PriorityQueue<GenericNode> fringe = new PriorityQueue<>();

    // The graph HashMap serves as a central storage space for visited nodes
    // all updates to nodes will be done on nodes in the graph
    HashMap<String, GenericNode> graph = new HashMap<>();

    // The solution represents the nodes that we ave already determined the shortest path for
    List<GenericNode> solution = new ArrayList<>();

    // Set start node's min distance to 0 and add start node to Heap.
    start.setHeuristic(end);
    start.setMinDistance(start.getHeuristic());
    fringe.add(start);
    graph.put(start.getId(), start);

    while (!fringe.isEmpty()) {
      GenericNode currNode = fringe.peek();
      solution.add(currNode);

      if (currNode.getId().equals(end.getId())) {
        break;
      } else {

        Set<GenericEdge> currNodeNeighbors;

        try {
          currNodeNeighbors = currNode.getEdges();
        } catch (Exception e) {
          return null;
        }

        for (GenericEdge edge : currNodeNeighbors) {

          GenericNode neighbor;
          if (currNode.equals(edge.getStart())) {
            neighbor = edge.getEnd();
          } else {
            neighbor = edge.getStart();
          }

          neighbor.setHeuristic(end);

          double newDistance;

          if (currNode.getMinDistance() == currNode.getHeuristic()) {
            newDistance = edge.getWeight();
          } else {
            newDistance = edge.getWeight()
                + graph.get(currNode.getId()).getHeuristic();
          }

          // adds other end of current way to the fringe
          if (!currNode.equals(neighbor) && !solution.contains(graph.get(neighbor.getId()))) {
            if (!fringe.contains(neighbor)) {
              fringe.add(neighbor);
              graph.put(neighbor.getId(), neighbor);
            }
            if (newDistance < graph.get(neighbor.getId()).getMinDistance()) {
              GenericNode toUpdate = graph.get(neighbor.getId());
              // update minDistance of way's end node
              toUpdate.setMinDistance(newDistance);
              toUpdate.setEdgeTo(edge);
            }
          }
        }
        fringe.remove(currNode);
      }
    }

    GenericNode current = solution.get(solution.size() - 1);

    ArrayList<GenericEdge> pathBackToStart = new ArrayList<>();

    while (!(current.getId().equals(start.getId()))) {
      GenericEdge currentWayTo = current.getEdgeTo();

      pathBackToStart.add(currentWayTo);
      if (currentWayTo.getStart().equals(current)) {
        current = graph.get(currentWayTo.getEnd().getId());
      } else {
        current = graph.get(currentWayTo.getStart().getId());
      }
    }
    Collections.reverse(pathBackToStart);
    return pathBackToStart;
  }
}
