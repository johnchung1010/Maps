package edu.brown.cs.student.stars.commands;

import edu.brown.cs.student.common.Commands;
import edu.brown.cs.student.stars.Star;
import edu.brown.cs.student.stars.StarDistanceComparator;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the "naive_radius" command.
 */
public class NaiveRadius implements Commands {

  /**
   * Execute the "naive_radius" command.
   *
   * @param command User input
   */
  @Override
  public void execute(String command) {
    Stars starsCommand = Stars.getInstance();
    List<Star> starsListCopy = starsCommand.getStarsList();
    List<String> commandArgs = Commands.getCommandArguments(command);

    try {
      double r = Double.parseDouble(commandArgs.get(1));
      if (r < 0) {
        System.out.println("ERROR: Radius must be non-negative.");
      } else {
        List<Star> starsInRange;

        // Location specified as star name.
        if (commandArgs.size() == 3) {
          String quotedName = commandArgs.get(2);
          if (quotedName.charAt(0) != '"' && quotedName.charAt(quotedName.length() - 1) != '"') {
            System.out.println("ERROR: Name of star must be given in quotes.");
            return;
          } else {
            // Name of star without quotes.
            String starName = quotedName.substring(1, quotedName.length() - 1);
            try {
              starsInRange = naiveRadiusName(starName, r, starsListCopy);
            } catch (Exception e) {
              System.out.println(e.getMessage());
              return;
            }
          }
          // Location specified as x,y,z-coordinate.
        } else if (commandArgs.size() == 5) {
          try {
            double x = Double.parseDouble(commandArgs.get(2));
            double y = Double.parseDouble(commandArgs.get(3));
            double z = Double.parseDouble(commandArgs.get(4));
            double[] origin = new double[] {x, y, z};

            starsInRange = naiveRadiusPosition(origin, r, starsListCopy);
          } catch (Exception e) {
            System.out.println("ERROR: The x, y, and z coordinates must be numbers.");
            return;
          }
        } else {
          System.out.println("ERROR: Incorrect number of arguments.");
          return;
        }
        // Print IDs of stars within radius.
        for (Star star : starsInRange) {
          System.out.println(star.getId());
        }
      }
    } catch (Exception e) {
      System.out.println("ERROR: Radius must be a non-negative number.");
    }
  }

  /**
   * Find all of the stars within a given distance
   * to a location specified as the name of a star.
   *
   * @param starName  Name of star enclosed in quotes
   * @param r         Radius
   * @param starsList Stars data loaded from CSV file
   * @return ArrayList of stars within radius
   * @throws Exception Name of star is not provided
   * or does not match any of the stars in the file
   */
  public List<Star> naiveRadiusName(String starName, double r, List<Star> starsList)
    throws Exception {
    if (starName.equals("")) {
      throw new Exception("ERROR: Must provide the name of a star.");
    } else {
      Star origin = null;
      // Search for star.
      for (int i = 0; i < starsList.size(); i++) {
        Star star = starsList.get(i);
        if (starName.equals(star.getName())) {
          origin = star;
          starsList.remove(i);
          break;
        }
      }
      if (origin == null) {
        throw new Exception("ERROR: Name of star doesn't match any stars in the file.");
      } else {
        return naiveRadiusPosition(origin.getCoordinate(), r, starsList);
      }
    }
  }

  /**
   * Find all of the stars within a given distance
   * to a location specified as an x,y,z-coordinate.
   *
   * @param origin    Given location specified as an x,y,z-coordinate
   * @param r         Radius
   * @param starsList Stars data loaded from CSV file
   * @return ArrayList of stars within radius
   */
  public List<Star> naiveRadiusPosition(double[] origin, double r, List<Star> starsList) {
    List<Star> starsInRange = new ArrayList<>();
    for (Star star : starsList) {
      star.setDistance(star.calcDistance(origin));
      if (star.getDistance() <= r) {
        starsInRange.add(star);
      }
    }
    starsInRange.sort(new StarDistanceComparator());
    return starsInRange;
  }
}
