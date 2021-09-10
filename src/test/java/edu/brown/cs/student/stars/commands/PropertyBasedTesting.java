package edu.brown.cs.student.stars.commands;

import edu.brown.cs.student.common.KDTree;
import edu.brown.cs.student.stars.Star;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

/**
 * Class representing Model Based Testing.
 */
public class PropertyBasedTesting {

  /**
   * Generate a List of random stars.
   *
   * @return List of random stars
   */
  public List<Star> generateStarsList() {
    List<Star> starsList = new ArrayList<>();
    Hashtable<String, Star> idToStar = new Hashtable<>();

    int len = ThreadLocalRandom.current().nextInt(1, 100);
    for (int i = 0; i < len; i++) {
      Star newStar = generateStar(idToStar);
      starsList.add(newStar);
      idToStar.put(newStar.getName(), newStar);
    }
    return starsList;
  }

  /**
   * Generate a random star.
   *
   * @param idToStar Hashtable that maps star id's to stars
   * @return Random star
   */
  public Star generateStar(Hashtable<String, Star> idToStar) {
    String name = generateName();

    // Create a unique star id.
    String id = "";
    while (id.equals("")) {
        id = String.valueOf(ThreadLocalRandom.current().nextInt(1000));
      if (idToStar.containsKey(id)) {
        id = "";
      }
    }
    double x = ThreadLocalRandom.current().nextDouble(-1000, 1000);
    double y = ThreadLocalRandom.current().nextDouble(-1000, 1000);
    double z = ThreadLocalRandom.current().nextDouble(-1000, 1000);

    return new Star(id, name, x, y, z);
  }

  /**
   * Generate a random star name.
   *
   * @return Random star name
   */
  // Structure copied from: https://www.programiz.com/java-programming/examples/generate-random-string
  public String generateName() {
    String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
    String numbers = "0123456789";
    String space = " ";

    String alphaNumeric = upperAlphabet + lowerAlphabet + numbers + space;
    StringBuilder name = new StringBuilder();
    int len = ThreadLocalRandom.current().nextInt(1, 100);

    for (int i = 0; i < len; i++) {
      int index = ThreadLocalRandom.current().nextInt(alphaNumeric.length());
      char randomChar = alphaNumeric.charAt(index);
      name.append(randomChar);
    }
    return name.toString();
  }

  /**
   * Generate a Hashtable that maps star names to stars.
   *
   * @param starsList List of stars
   * @return Hashtable that maps star names to stars
   */
  public Hashtable<String, Star> generateNameToStar (List<Star> starsList) {
    Hashtable<String, Star> nameToStar = new Hashtable<>();
    for (Star star : starsList) {
      nameToStar.put(star.getName(), star);
    }
    return nameToStar;
  }

  /**
   * Determine if two implementations produce the same output.
   *
   * @param naiveImp  Output of naive implementation
   * @param kDImp     Output of k-d tree implementation
   * @param starsList Original List of stars
   * @return Boolean value
   */
  public boolean sameOutput(
    List<Star> naiveImp, List<Star> kDImp, List<Star> starsList) {
    for (int i = 0; i < naiveImp.size(); i++) {
      // Check if stars are from generated list and have the same distances.
      if (!starsList.contains(naiveImp.get(i))
        || !starsList.contains(kDImp.get(i))
        || naiveImp.get(i).getDistance() != kDImp.get(i).getDistance()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Test if the k-nearest neighbors search by name
   * implementations produce the same output.
   *
   * @throws Exception Name of star does not match any of the stars in the file
   */
  @Test
  public void testNeighborsName() throws Exception {
    int i = 0;
    while (i < 1000) {
      NaiveNeighbors naiveNeighborsCommand = new NaiveNeighbors();
      Neighbors neighborsCommand = new Neighbors();

      List<Star> starsList = generateStarsList();
      KDTree<Star> starsTree = new KDTree<>(3, starsList);

      neighborsCommand.setStarsTree(starsTree);
      Hashtable<String, Star> nameToStar = generateNameToStar(starsList);

      int index = ThreadLocalRandom.current().nextInt(starsList.size());
      String starName = starsList.get(index).getName();

      int k = ThreadLocalRandom.current().nextInt(100);
      neighborsCommand.setNumNeighbors(k);

      List<Star> naiveImp = naiveNeighborsCommand.naiveNeighborsName(starName, k, starsList);
      List<Star> kDImp = neighborsCommand.neighborsName(starName, nameToStar)
        .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());

      assertTrue(sameOutput(naiveImp, kDImp, starsList));

      i++;
    }
  }

  /**
   * Test if the k-nearest neighbors search by position
   * implementations produce the same output.
   */
  @Test
  public void testNeighborsPosition() {
    int i = 0;
    while (i < 1000) {
      NaiveNeighbors naiveNeighborsCommand = new NaiveNeighbors();
      Neighbors neighborsCommand = new Neighbors();

      List<Star> starsList = generateStarsList();
      KDTree<Star> starsTree = new KDTree<>(3, starsList);
      neighborsCommand.setStarsTree(starsTree);

      double x = ThreadLocalRandom.current().nextDouble(-1000, 1000);
      double y = ThreadLocalRandom.current().nextDouble(-1000, 1000);
      double z = ThreadLocalRandom.current().nextDouble(-1000, 1000);

      double[] targetPosition = new double[] {x, y, z};
      neighborsCommand.setTargetPosition(targetPosition);

      int k = ThreadLocalRandom.current().nextInt(100);
      neighborsCommand.setNumNeighbors(k);

      List<Star> naiveImp = naiveNeighborsCommand.naiveNeighborsPosition(targetPosition, k, starsList);
      List<Star> kDImp = neighborsCommand.neighborsPosition()
        .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());

      assertTrue(sameOutput(naiveImp, kDImp, starsList));

      i++;
    }
  }

  /**
   * Test if the radius search by name
   * implementations produce the same output.
   *
   * @throws Exception Name of star does not match any of the stars in the file
   */
  @Test
  public void testRadiusName() throws Exception {
    int i = 0;
    while (i < 1000) {
      NaiveRadius naiveRadiusCommand = new NaiveRadius();
      Radius radiusCommand = new Radius();

      List<Star> starsList = generateStarsList();
      KDTree<Star> starsTree = new KDTree<>(3, starsList);
      radiusCommand.setStarsTree(starsTree);
      Hashtable<String, Star> nameToStar = generateNameToStar(starsList);

      int index = ThreadLocalRandom.current().nextInt(starsList.size());
      String starName = starsList.get(index).getName();

      double radius = ThreadLocalRandom.current().nextDouble(4000);
      radiusCommand.setRadius(radius);

      List<Star> naiveImp = naiveRadiusCommand.naiveRadiusName(starName, radius, starsList);
      List<Star> kDImp = radiusCommand.radiusName(starName, nameToStar)
        .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());

      assertTrue(sameOutput(naiveImp, kDImp, starsList));

      i++;
    }
  }

  /**
   * Test if the radius search by position
   * implementations produce the same output.
   */
  @Test
  public void testRadiusPosition() {
    int i = 0;
    while (i < 1000) {
      NaiveRadius naiveRadiusCommand = new NaiveRadius();
      Radius radiusCommand = new Radius();

      List<Star> starsList = generateStarsList();
      KDTree<Star> starsTree = new KDTree<>(3, starsList);
      radiusCommand.setStarsTree(starsTree);

      double x = ThreadLocalRandom.current().nextDouble(-1000, 1000);
      double y = ThreadLocalRandom.current().nextDouble(-1000, 1000);
      double z = ThreadLocalRandom.current().nextDouble(-1000, 1000);

      double[] targetPosition = new double[] {x, y, z};
      radiusCommand.setTargetPosition(targetPosition);

      double radius = ThreadLocalRandom.current().nextDouble(4000);
      radiusCommand.setRadius(radius);

      List<Star> naiveImp = naiveRadiusCommand.naiveRadiusPosition(targetPosition, radius, starsList);
      List<Star> kDImp = radiusCommand.radiusPosition()
        .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());

      assertTrue(sameOutput(naiveImp, kDImp, starsList));

      i++;
    }
  }
}
