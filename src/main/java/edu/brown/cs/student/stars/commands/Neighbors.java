package edu.brown.cs.student.stars.commands;

import edu.brown.cs.student.common.Commands;
import edu.brown.cs.student.common.KDTree;
import edu.brown.cs.student.stars.Star;

import java.util.Hashtable;
import java.util.List;

/**
 * Class representing the "neighbors" command.
 */
public class Neighbors implements Commands {

  private KDTree<Star> starsTree;
  private int numNeighbors;
  private double[] targetPosition;

  /**
   * Setter.
   *
   * @param starsTreeIn KDTree
   */
  public void setStarsTree(KDTree<Star> starsTreeIn) {
    starsTree = starsTreeIn;
  }

  /**
   * Setter.
   *
   * @param numNeighborsIn Desired number of neighbors
   */
  public void setNumNeighbors(int numNeighborsIn) {
    numNeighbors = numNeighborsIn;
  }

  /**
   * Setter.
   *
   * @param targetPositionIn Target position
   */
  public void setTargetPosition(double[] targetPositionIn) {
    targetPosition = targetPositionIn;
  }

  /**
   * Execute the "neighbors" command.
   * @param command User input
   */
  @Override
  public void execute(String command) {
    Stars starsCommand = Stars.getInstance();
    Hashtable<String, Star> nameToStar = starsCommand.getNameToStar();
    starsTree = starsCommand.getStarsTree();
    List<String> commandArgs = Commands.getCommandArguments(command);

    try {
      numNeighbors = Integer.parseInt(commandArgs.get(1));
      if (numNeighbors > starsCommand.getStarsList().size()) {
        numNeighbors = starsCommand.getStarsList().size();
      }
      if (numNeighbors < 0) {
        System.out.println("ERROR: Cannot find negative number of neighbors.");
      } else {
        List<KDTree<Star>.KDNode<Star>> nearest;

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
              nearest = neighborsName(starName, nameToStar);
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

            nearest = neighborsPosition();
          } catch (Exception e) {
            System.out.println("ERROR: The x, y, and z coordinates must be numbers.");
            return;
          }
        } else {
          System.out.println("ERROR: Incorrect number of arguments.");
          return;
        }
        // Print IDs of k-nearest stars.
        for (KDTree<Star>.KDNode<Star> node : nearest) {
          System.out.println(node.getDatum().getId());
        }
      }
    } catch (Exception e) {
      System.out.println("ERROR: \"k\" must be a non-negative integer.");
    }
  }

  /**
   * Find the k-nearest neighbors to a location specified as the name of a star.
   *
   * @param starName Name of a star
   * @param nameToStar  Stars data loaded from CSV file
   * @return List of KDNodes of the nearest stars
   * @throws Exception Name of star is not provided
   * or does not match any of the stars in the file
   */
  public List<KDTree<Star>.KDNode<Star>> neighborsName(
      String starName, Hashtable<String, Star> nameToStar) throws Exception {
    if (starName.equals("")) {
      throw new Exception("ERROR: Must provide the name of a star.");
    } else if (nameToStar.containsKey(starName)) {
      Star targetStar = nameToStar.get(starName);
      targetPosition = targetStar.getCoordinate();
      numNeighbors += 1;
      List<KDTree<Star>.KDNode<Star>> nearest = neighborsPosition();

      for (int i = 0; i < nearest.size(); i++) {
        if (nearest.get(i).getDatum().getId().equals(targetStar.getId())) {
          nearest.remove(i);
          return nearest;
        }
      }
      nearest.remove(nearest.size() - 1);
      return nearest;
    } else {
      throw new Exception("ERROR: Name of star doesn't match any stars in the file.");
    }
  }

  /**
   * Find the k-nearest neighbors to a location specified as an x,y,z-coordinate.
   *
   * @return List of KDNodes of the nearest stars
   */
  public List<KDTree<Star>.KDNode<Star>> neighborsPosition() {
    return starsTree.getKNearestNeighbors(numNeighbors, targetPosition);
  }
}
