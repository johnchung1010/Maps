package edu.brown.cs.student.maps;

/**
 * This class, which extends GenericEdge, represents a way in a map.
 * Takes in a name, type, start node, and end node
 */
public class Way extends GenericEdge {

  private String name;
  private String type;
  private GenericNode start;
  private GenericNode end;

  private double weight;

  /**
   * Constructor.
   *
   * @param edgeId    Id
   * @param wayName   Name
   * @param wayType   Type
   * @param startNode Start node
   * @param endNode   End node
   */
  public Way(String edgeId, String wayName, String wayType, GenericNode startNode,
             GenericNode endNode) {
    super(edgeId, startNode, endNode);
    name = wayName;
    type = wayType;
    start = startNode;
    end = endNode;
  }

  /**
   * Return edge's weight.
   *
   * @return Weight
   */
  @Override
  double getWeight() {
    //The weight is the haversine distance between the start and end points
    final double earthRadius = 6371; //Earth's radius in km

    double latDiff = start.getCoordinate()[0] - end.getCoordinate()[0];
    double longDiff = start.getCoordinate()[1] - end.getCoordinate()[1];

    double a = Math.pow(Math.sin(latDiff / 2), 2)
        + (Math.cos(start.getCoordinate()[0]) * Math.cos(end.getCoordinate()[0])
        * Math.pow(Math.sin(longDiff / 2), 2));

    weight = 2 * earthRadius * Math.asin(Math.sqrt(a));

    return weight;
  }
}
