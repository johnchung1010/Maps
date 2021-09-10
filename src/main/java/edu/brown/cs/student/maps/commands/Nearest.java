package edu.brown.cs.student.maps.commands;

import edu.brown.cs.student.common.Commands;
import edu.brown.cs.student.common.KDTree;
import edu.brown.cs.student.maps.Database;
import edu.brown.cs.student.maps.Node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the "nearest" command.
 */
public class Nearest implements Commands {

  private double latitude;
  private double longitude;

  /**
   * Setter.
   *
   * @param lat Latitude
   */
  public void setLatitude(double lat) {
    latitude = lat;
  }

  /**
   * Setter.
   *
   * @param lon Longitude
   */
  public void setLongitude(double lon) {
    longitude = lon;
  }

  /**
   * Execute the "nearest" command.
   *
   * @param command User input
   */
  @Override
  public void execute(String command) {
    List<String> commandArgs = Commands.getCommandArguments(command);

    if (commandArgs.size() != 3) {
      System.out.print("ERROR: Incorrect number of arguments.");
    } else {
      try {
        latitude = Double.parseDouble(commandArgs.get(1));
        longitude = Double.parseDouble(commandArgs.get(2));

        try {
          Node nearest = getNearestTraversableNode();
          System.out.println(nearest.getId());
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      } catch (Exception e) {
        System.out.println("ERROR: Inputs for latitude and longitude must be numbers.");
      }
    }
  }

  /**
   * Return the nearest traversable node to the input point.
   *
   * @return Nearest traversable node
   * @throws Exception No map loaded or error querying the database
   */
  public Node getNearestTraversableNode() throws Exception {
    Map mapCommand = Map.getInstance();
    Database map = mapCommand.getMap();

    if (map == null) {
      throw new Exception("ERROR: No map loaded.");
    } else {
      try {
        Connection conn = map.getConn();
        String query = "SELECT n.id, n.latitude, n.longitude FROM 'node' as n, 'way' as w"
            + " WHERE w.start = n.id AND (w.type != 'unclassified' OR w.type != '')"
            + " UNION SELECT n.id, n.latitude, n.longitude FROM 'node' as n, 'way' as w"
            + " WHERE w.end = n.id AND (w.type != 'unclassified' OR w.type != '')";

        PreparedStatement prep = conn.prepareStatement(query);
        ResultSet rs = prep.executeQuery();

        List<Node> nodes = new ArrayList<>();

        while (rs.next()) {
          String id = rs.getString(1);
          double lat = Double.parseDouble(rs.getString(2));
          double lon = Double.parseDouble(rs.getString(3));

          Node node = new Node(id, new double[] {lat, lon});
          nodes.add(node);
        }
        rs.close();

        KDTree<Node> nodesTree = new KDTree<>(2, nodes);
        return nodesTree.getKNearestNeighbors(1, new double[] {latitude, longitude})
            .get(0).getDatum();
      } catch (Exception e) {
        throw new Exception("ERROR: Could not query the database.");
      }
    }
  }
}
