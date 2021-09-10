package edu.brown.cs.student.common;

import edu.brown.cs.student.maps.commands.Map;
import edu.brown.cs.student.stars.commands.Mock;
import edu.brown.cs.student.stars.commands.NaiveNeighbors;
import edu.brown.cs.student.stars.commands.NaiveRadius;
import edu.brown.cs.student.maps.commands.Nearest;
import edu.brown.cs.student.stars.commands.Neighbors;
import edu.brown.cs.student.stars.commands.Radius;
import edu.brown.cs.student.maps.commands.Route;
import edu.brown.cs.student.stars.commands.Stars;
import edu.brown.cs.student.maps.commands.Ways;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Class representing the REPL.
 */
public class REPL {

  private final HashMap<String, Commands> registered = new HashMap<>();

  /**
   * Run REPL.
   */
  public void run() {
    registerCommands();

    // For each line of input from user, print results of each command.
    try (BufferedReader br = new BufferedReader(
        new InputStreamReader(System.in))) {
      String input;
      while ((input = br.readLine()) != null) {
        String command = input.split(" ")[0];

        if (registered.containsKey(command)) {
          registered.get(command).execute(input);
        } else {
          System.out.println("ERROR: \"" + command + "\" is an invalid command.");
        }
      }
    } catch (Exception e) {
      System.out.print("ERROR: Invalid input for REPL.");
    }
  }

  /**
   * Register commands in a HashMap.
   */
  public void registerCommands() {
    registered.put("stars", Stars.getInstance());
    registered.put("naive_neighbors", new NaiveNeighbors());
    registered.put("naive_radius", new NaiveRadius());
    registered.put("mock", new Mock());
    registered.put("neighbors", new Neighbors());
    registered.put("radius", new Radius());
    registered.put("map", Map.getInstance());
    registered.put("ways", new Ways());
    registered.put("nearest", new Nearest());
    registered.put("route", new Route());
  }
}
