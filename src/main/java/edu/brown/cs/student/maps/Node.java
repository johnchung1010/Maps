package edu.brown.cs.student.maps;

import edu.brown.cs.student.maps.commands.Map;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This class extends GenericNode and represents a node within a map.
 * It contains an id and some coordinates
 */
public class Node extends GenericNode {

  /**
   * Constructor.
   *
   * @param nodeId     Id
   * @param nodeCoords Coordinate
   */
  public Node(String nodeId, double[] nodeCoords) {
    super(nodeId, nodeCoords);
  }

  /**
   * Return all the edges that contain the node.
   *
   * @return Set of edges that contain the node
   * @throws Exception No map loaded or error querying the database
   */
  @Override
  Set<GenericEdge> getEdges() throws Exception {
    Map mapCommand = Map.getInstance();
    Database map = mapCommand.getMap();
    Cache cache = Cache.getInstance();
    HashMap<String, Set<GenericEdge>> nodes = cache.getNodes();

    if (map == null) {
      throw new Exception("ERROR: No map loaded.");
    } else {
      if (nodes.containsKey(this.getId())) {
        return nodes.get(this.getId());
      } else {
        // Get all traversable ways that start or end at the currNode.
        String query =
            "SELECT * FROM 'way' as w, 'node' as n INNER JOIN 'node' as n2 ON n2.id == w.end WHERE "
            + "w.start = n.id AND (w.type != 'unclassified' OR w.type != '') AND (w.start = "
            + "'" + this.getId() + "'" + " OR w.end = " + "'" + this.getId() + "'"
            + ") UNION SELECT * FROM 'way' as w, 'node' as n INNER JOIN 'node' as n2 ON "
            + "n2.id == w.end WHERE w.end = n.id AND (w.type != 'unclassified' OR w.type != '')"
            + " AND (w.start = "
            + "'" + this.getId() + "'" + " OR w.end = " + "'" + this.getId() + "'"
            + ") AND n.id != w.end";

        PreparedStatement prep = map.getConn().prepareStatement(query);
        ResultSet rs = prep.executeQuery();

        Set<GenericEdge> neighborWays = new HashSet<>(Collections.emptySet());

        while (rs.next()) {
          String wayId = rs.getString(1);
          String name = rs.getString(2);
          String type = rs.getString(3);

          String startId = rs.getString(4);
          double startLat = Double.parseDouble(rs.getString(7));
          double startLon = Double.parseDouble(rs.getString(8));
          Node startNode = new Node(startId, new double[] {startLat, startLon});

          String endId = rs.getString(5);
          double endLat = Double.parseDouble(rs.getString(10));
          double endLon = Double.parseDouble(rs.getString(11));
          Node endNode = new Node(endId, new double[] {endLat, endLon});

          Way way = new Way(wayId, name, type, startNode, endNode);
          neighborWays.add(way);
        }
        nodes.put(this.getId(), neighborWays);
        return neighborWays;
      }
    }
  }

  @Override
  public double setHeuristic(GenericNode target) {
    //The weight is the haversine distance between the start and end points
    final double earthRadius = 6371; //Earth's radius in km

    double latDiff = this.getCoordinate()[0] - target.getCoordinate()[0];
    double longDiff = this.getCoordinate()[1] - target.getCoordinate()[1];

    double a = Math.pow(Math.sin(latDiff / 2), 2)
        + (Math.cos(this.getCoordinate()[0]) * Math.cos(target.getCoordinate()[0])
        * Math.pow(Math.sin(longDiff / 2), 2));

    return 2 * earthRadius * Math.asin(Math.sqrt(a));
  }
}
