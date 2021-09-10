package edu.brown.cs.student.stars.commands;

import edu.brown.cs.student.common.Commands;
import edu.brown.cs.student.common.KDTree;
import edu.brown.cs.student.stars.Star;

import java.util.Hashtable;
import java.util.List;

/**
 * Class representing the "radius" command.
 */
public class Radius implements Commands {

  private double radius;
  private double[] targetPosition;
  private KDTree<Star> starsTree;

  /**
   * Setter.
   *
   * @param radiusIn Radius
   */
  public void setRadius(double radiusIn) {
    radius = radiusIn;
  }

  /**
   * Setter.
   *
   * @param targetPositionIn Target position.
   */
  public void setTargetPosition(double[] targetPositionIn) {
    targetPosition = targetPositionIn;
  }

  /**
   * Setter.
   *
   * @param starsTreeIn KDTree
   */
  public void setStarsTree(KDTree<Star> starsTreeIn) {
    starsTree = starsTreeIn;
  }

  /**
   * Execute the "radius" command.
   *
   * @param command User input
   */
  @Override
  public void execute(String command) {
    Stars starsCommand = Stars.getInstance();
    Hashtable<String, Star> namesToStars = starsCommand.getNameToStar();
    starsTree = starsCommand.getStarsTree();
    List<String> commandArgs = Commands.getCommandArguments(command);

    try {
      radius = Double.parseDouble(commandArgs.get(1));
      if (radius < 0) {
        System.out.println("ERROR: Radius must be non-negative.");
      } else {
        List<KDTree<Star>.KDNode<Star>> inRange;

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
              inRange = radiusName(starName, namesToStars);
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
            targetPosition = new double[] {x, y, z};

            inRange = radiusPosition();
          } catch (Exception e) {
            System.out.println("ERROR: The x, y, and z coordinates must be numbers.");
            return;
          }
        } else {
          System.out.println("ERROR: Incorrect number of arguments.");
          return;
        }
        // Print IDs of stars within radius.
        for (KDTree<Star>.KDNode<Star> node : inRange) {
          System.out.println(node.getDatum().getId());
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
   * @param starName   Name of a star
   * @param nameToStar Stars data loaded from CSV file
   * @return List of KDNodes of stars within radius
   * @throws Exception Name of star is not provided
   * or does not match an of the stars in the file
   */
  public List<KDTree<Star>.KDNode<Star>> radiusName(
      String starName, Hashtable<String, Star> nameToStar)
    throws Exception {
    if (starName.equals("")) {
      throw new Exception("ERROR: Must provide the name of a star.");
    } else if (nameToStar.containsKey(starName)) {
      Star targetStar = nameToStar.get(starName);
      targetPosition = targetStar.getCoordinate();

      List<KDTree<Star>.KDNode<Star>> inRange = radiusPosition();

      for (int i = 0; i < inRange.size(); i++) {
        if (inRange.get(i).getDatum().getId().equals(targetStar.getId())) {
          inRange.remove(i);
          break;
        }
      }
      return inRange;
    } else {
      throw new Exception("ERROR: Name of star doesn't match any stars in the file.");
    }
  }

  /**
   * Find all of the stars within a given distance
   * to a location specified as an x,y,z-coordinate.
   *
   * @return List of KDNodes of stars within radius
   */
  public List<KDTree<Star>.KDNode<Star>> radiusPosition() {
    return starsTree.getWithinRadius(radius, targetPosition);
  }
}
