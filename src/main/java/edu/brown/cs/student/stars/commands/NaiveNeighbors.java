package edu.brown.cs.student.stars.commands;

import edu.brown.cs.student.common.Commands;
import edu.brown.cs.student.stars.Star;
import edu.brown.cs.student.stars.StarDistanceComparator;

import java.util.Collections;
import java.util.List;

/**
 * Class representing the "naive_neighbors" command.
 */
public class NaiveNeighbors implements Commands {

  /**
   * Execute the "naive_neighbors" command.
   *
   * @param command User input
   */
  @Override
  public void execute(String command) {
    Stars starsCommand = Stars.getInstance();
    List<Star> starsListCopy = starsCommand.getStarsList();
    List<String> commandArgs = Commands.getCommandArguments(command);

    try {
      int k = Integer.parseInt(commandArgs.get(1));
      if (k < 0) {
        System.out.println("ERROR: Cannot find negative number of neighbors.");
      } else {
        List<Star> nearestStars;

        // Location specified as star name.
        if (commandArgs.size() == 3) {
          String quotedName = commandArgs.get(2);
          if (quotedName.charAt(0) != '"' && quotedName.charAt(quotedName.length() - 1) != '"') {
            System.out.println("ERROR: The name of a star must be given in quotes.");
            return;
          } else {
            // Name of star without quotes.
            String starName = quotedName.substring(1, quotedName.length() - 1);
            try {
              nearestStars = naiveNeighborsName(starName, k, starsListCopy);
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

            nearestStars = naiveNeighborsPosition(origin, k, starsListCopy);
          } catch (Exception e) {
            System.out.println("ERROR: The x, y, and z coordinates must be numbers.");
            return;
          }
        } else {
          System.out.println("ERROR: Incorrect number of arguments.");
          return;
        }
        // Print IDs of k-nearest stars.
        for (Star star : nearestStars) {
          System.out.println(star.getId());
        }
      }
    } catch (Exception e) {
      System.out.println("ERROR: \"k\" must be a non-negative integer.");
    }
  }

  /**
   * Find the k-nearest neighbors to a location specified as the name of a star.
   *
   * @param starName  Name of a star
   * @param k         Desired number of closest stars
   * @param starsList Stars data loaded from CSV file
   * @return ArrayList of closest stars
   * @throws Exception Name of star is not provided
   * or does not match any of the stars in the file
   */
  public List<Star> naiveNeighborsName(String starName, int k, List<Star> starsList)
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
        return naiveNeighborsPosition(origin.getCoordinate(), k, starsList);
      }
    }
  }

  /**
   * Find the k-nearest neighbors to a location specified as an x,y,z-coordinate.
   *
   * @param origin    Given location specified as an x,y,z-coordinate
   * @param k         Desired number of closest stars
   * @param starsList Stars data loaded from CSV file
   * @return ArrayList of closest stars
   */
  public List<Star> naiveNeighborsPosition(double[] origin, int k, List<Star> starsList) {
    // Update each star's distance.
    for (Star star : starsList) {
      star.setDistance(star.calcDistance(origin));
    }
    Collections.shuffle(starsList);
    starsList.sort(new StarDistanceComparator());

    if (starsList.size() == 0 || starsList.size() < k) {
      return starsList;
    } else {
      return starsList.subList(0, k);
    }
  }
}
