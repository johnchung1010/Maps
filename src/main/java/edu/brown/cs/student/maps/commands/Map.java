package edu.brown.cs.student.maps.commands;

import edu.brown.cs.student.common.Commands;
import edu.brown.cs.student.maps.Cache;
import edu.brown.cs.student.maps.Database;

import java.util.List;

/**
 * Class representing the "map" command.
 */
public final class Map implements Commands {

  private Database map;
  private static final Map INSTANCE = new Map();

  /**
   * Getter.
   *
   * @return SQLite3 database representing map data
   */
  public Database getMap() {
    return map;
  }

  /**
   * Getter.
   *
   * @return The only instance of the Map object.
   */
  public static Map getInstance() {
    return INSTANCE;
  }

  /**
   * Execute the "map" command.
   *
   * @param command User input
   */
  @Override
  public void execute(String command) {
    List<String> commandArgs = Commands.getCommandArguments(command);
    if (commandArgs.size() == 2) {
      map(commandArgs);
    } else {
      System.out.println("ERROR: Incorrect number of arguments.");
    }
  }

  /**
   * Load map data.
   *
   * @param commandArgs List of command arguments
   */
  public void map(List<String> commandArgs) {
    String databasePath = commandArgs.get(1);
    map = new Database(databasePath);

    // Reset cache.
    Cache cache = Cache.getInstance();
    cache.resetNodes();

    try {
      map.setDatabase();
      System.out.println("map set to " + databasePath);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
