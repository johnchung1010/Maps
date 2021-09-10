package edu.brown.cs.student.maps.commands;

import edu.brown.cs.student.common.Commands;
import edu.brown.cs.student.maps.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the "ways" command.
 */
public class Ways implements Commands {

  private double lat1;
  private double lon1;
  private double lat2;
  private double lon2;

  /**
   * Setter.
   *
   * @param lat1In Latitude of first coordinate
   * @param lon1In Longitude of first coordinate
   * @param lat2In Latitude of second coordinate
   * @param lon2In Longitude of second coordinate
   */
  public void setCoords(double lat1In, double lon1In, double lat2In, double lon2In) {
    lat1 = lat1In;
    lon1 = lon1In;
    lat2 = lat2In;
    lon2 = lon2In;
  }

  /**
   * Execute the "ways" command.
   *
   * @param command User input
   */
  @Override
  public void execute(String command) {
    List<String> commandArgs = Commands.getCommandArguments(command);

    if (commandArgs.size() != 5) {
      System.out.print("ERROR: Incorrect number of arguments.");
    } else {
      try {
        lat1 = Double.parseDouble(commandArgs.get(1));
        lon1 = Double.parseDouble(commandArgs.get(2));
        lat2 = Double.parseDouble(commandArgs.get(3));
        lon2 = Double.parseDouble(commandArgs.get(4));

        try {
          List<String> wayIds = getWays();
          for (String id : wayIds) {
            System.out.println(id);
          }
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      } catch (Exception e) {
        System.out.println("ERROR: Inputs for latitude and longitude must be numbers.");
      }
    }
  }

  /**
   * Get all the ways which either start or end within the bounding box
   * defined by the two coordinate pairs assigned above.
   *
   * @return List of the ids of the ways in the bounding box
   * @throws Exception No map loaded or error querying the database
   */
  public List<String> getWays() throws Exception {
    Map mapCommand = Map.getInstance();
    Database map = mapCommand.getMap();
    List<String> wayIds = new ArrayList<>();

    if (map == null) {
      throw new Exception("ERROR: No map loaded.");
    } else {
      Connection conn = map.getConn();
      String query = "SELECT w.id FROM 'way' AS w, 'node' as n"
          + " WHERE w.start = n.id AND n.latitude BETWEEN " + lat2 + " AND " + lat1
          + " AND n.longitude BETWEEN " + lon1 + " AND " + lon2 + " UNION SELECT w.id"
          + " FROM 'way' AS w, 'node' as n WHERE w.end = n.id AND n.latitude BETWEEN " + lat2
          + " AND " + lat1 + " AND n.longitude BETWEEN " + lon1 + " AND " + lon2 + " ORDER BY w.id";

      PreparedStatement prep = conn.prepareStatement(query);
      ResultSet rs = prep.executeQuery();

      while (rs.next()) {
        String id = rs.getString(1);
        wayIds.add(id);
      }
      rs.close();
      return wayIds;
    }
  }
}
