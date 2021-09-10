package edu.brown.cs.student.maps.commands;

import edu.brown.cs.student.common.Commands;
import edu.brown.cs.student.maps.Database;
import edu.brown.cs.student.maps.AStar;
import edu.brown.cs.student.maps.GenericEdge;
import edu.brown.cs.student.maps.GenericNode;
import edu.brown.cs.student.maps.Node;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Class representing the "route" command.
 */
public class Route implements Commands {

  private Node start;
  private Node end;

  /**
   * Setter.
   *
   * @param startIn Start node
   */
  public void setStart(Node startIn) {
    start = startIn;
  }

  /**
   * Setter.
   *
   * @param endIn End node
   */
  public void setEnd(Node endIn) {
    end = endIn;
  }

  /**
   * Execute the "route" command.
   *
   * @param command User input
   */
  @Override
  public void execute(String command) {
    List<String> commandArgs = Commands.getCommandArguments(command);

    if (commandArgs.size() != 5) {
      System.out.println("ERROR: Incorrect number of arguments.");
    } else {
      // Check if the inputs are enclosed in quotes.
      if (commandArgs.get(1).startsWith("\"") && commandArgs.get(1).endsWith("\"")
          && commandArgs.get(2).startsWith("\"") && commandArgs.get(2).endsWith("\"")
          && commandArgs.get(3).startsWith("\"") && commandArgs.get(3).endsWith("\"")
          && commandArgs.get(4).startsWith("\"") && commandArgs.get(4).endsWith("\"")) {

        String street1 = commandArgs.get(1);
        String crossStreet1 = commandArgs.get(2);
        String street2 = commandArgs.get(3);
        String crossStreet2 = commandArgs.get(4);

        try {
          start = findIntersection(street1, crossStreet1);
          end = findIntersection(street2, crossStreet2);
        } catch (Exception e) {
          System.out.println(e.getMessage());
          return;
        }
      } else {
        // Otherwise, check if the inputs can be parsed as doubles.
        double lat1;
        double lon1;
        double lat2;
        double lon2;

        try {
          lat1 = Double.parseDouble(commandArgs.get(1));
          lon1 = Double.parseDouble(commandArgs.get(2));
          lat2 = Double.parseDouble(commandArgs.get(3));
          lon2 = Double.parseDouble(commandArgs.get(4));
        } catch (Exception e) {
          System.out.println("ERROR: Street names not enclosed in quotes"
              + " or inputs for latitude and longitude are not numbers.");
          return;
        }
        try {
          Nearest nearestCommand = new Nearest();
          // Find start node using the "nearest" command.
          nearestCommand.setLatitude(lat1);
          nearestCommand.setLongitude(lon1);
          start = nearestCommand.getNearestTraversableNode();

          // Find end node using the "nearest" command.
          nearestCommand.setLatitude(lat2);
          nearestCommand.setLongitude(lon2);
          end = nearestCommand.getNearestTraversableNode();
        } catch (Exception e2) {
          System.out.println(e2.getMessage());
          return;
        }
      }

      AStar aStar = new AStar(start, end);
      List<GenericEdge> path = aStar.getShortestPath();
      printRoute(path);
    }
  }

  /**
   * Find the node that corresponds to the intersection between two streets.
   *
   * @param street      Name of the street
   * @param crossStreet Name of the cross street
   * @return Node corresponding to the intersection between two streets
   * @throws Exception Map not loaded or streets are parallel
   */
  public Node findIntersection(String street, String crossStreet) throws Exception {
    Map mapCommand = Map.getInstance();
    Database map = mapCommand.getMap();

    if (map == null) {
      throw new Exception("ERROR: No map loaded.");
    } else {
      String query = "SELECT n.id, n.latitude, n.longitude FROM 'node' as n, 'way'"
          + " as w WHERE (w.name == " + street + " AND (n.id == w.start OR n.id == w.end))"
          + " INTERSECT SELECT n.id, " + "n.latitude, n.longitude FROM 'node' as n, 'way' as w"
          + " WHERE (w.name == " + crossStreet + " AND (n.id == w.start OR n.id == w.end))";

      PreparedStatement prep = map.getConn().prepareStatement(query);
      ResultSet rs = prep.executeQuery();

      if (rs.next()) {
        String id = rs.getString(1);
        double lat = Double.parseDouble(rs.getString(2));
        double lon = Double.parseDouble(rs.getString(3));

        return new Node(id, new double[]{lat, lon});
      } else {
        throw new Exception("ERROR: These streets do not intersect.");
      }
    }
  }

  /**
   * Print the route of the shortest path.
   *
   * @param route List of edges representing the shortest path
   */
  public void printRoute(List<GenericEdge> route) {
    if (route.isEmpty()) {
      System.out.println(start.getId() + " -/- " + end.getId());
    } else {
      GenericNode prev = start;

      for (GenericEdge w : route) {
        if (w.getStart().getId().equals(prev.getId())) {
          System.out.println(w.getStart().getId() + " -> " + w.getEnd().getId()
              + " : " + w.getId());
          prev = w.getEnd();
        } else {
          System.out.println(w.getEnd().getId() + " -> " + w.getStart().getId()
              + " : " + w.getId());
          prev = w.getStart();
        }
      }
    }
  }
}
