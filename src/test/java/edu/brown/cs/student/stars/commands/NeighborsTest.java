package edu.brown.cs.student.stars.commands;

import edu.brown.cs.student.common.KDTree;
import edu.brown.cs.student.stars.Star;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Class that tests the "neighbors" command.
 */
public class NeighborsTest {

  private final Neighbors command = new Neighbors();

  /**
   * Create an ArrayList of one star.
   *
   * @return ArrayList of one star
   */
  public List<Star> oneStar() {
    List<Star> starsList = new ArrayList<>();
    starsList.add(new Star("1", "Lonely Star",5,-2.24,10.04));

    return starsList;
  }

  /**
   * Create an ArrayList of three stars.
   *
   * @return ArrayList of three stars
   */
  public List<Star> threeStars() {
    List<Star> starsList = new ArrayList<>();
    starsList.add(new Star("1", "Star One",1,0,0));
    starsList.add(new Star("2", "Star Two",2,0,0));
    starsList.add(new Star("3", "Star Three",3,0,0));

    return starsList;
  }

  /**
   * Create a Hashtable that maps star names to stars.
   *
   * @return Hashtable that maps star names to stars
   */
  public Hashtable<String, Star> nameToStar(List<Star> starsList) {
    Hashtable<String, Star> nameToStar = new Hashtable<>();
    for (Star star : starsList) {
      nameToStar.put(star.getName(), star);
    }
    return nameToStar;
  }

  /**
   * Test the "neighbors" command that takes in the name of a star.
   *
   * @throws Exception Name of star does not match any of the stars in the file
   */
  @Test
  public void testNeighborsName() throws Exception {
    List<Star> oneStar = oneStar();
    List<Star> threeStars = threeStars();

    // Test when input name is the name of the only star in the file.
    command.setNumNeighbors(1);
    command.setStarsTree(new KDTree<>(3, oneStar));
    List<Star> isOnlyStar = command.neighborsName("Lonely Star", nameToStar(oneStar))
      .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());
    assertEquals(0, isOnlyStar.size());

    // Test when k = 0.
    command.setNumNeighbors(0);
    command.setStarsTree(new KDTree<>(3, threeStars));
    List<Star> kIs0 = command.neighborsName("Star One", nameToStar(threeStars))
      .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());
    assertEquals(0, kIs0.size());

    // Test when 0 < k < number of stars in the file.
    command.setNumNeighbors(2);
    command.setStarsTree(new KDTree(3, threeStars));
    List<Star> kBetween0AndTotal = command.neighborsName("Star One", nameToStar(threeStars))
      .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());
    assertEquals(2, kBetween0AndTotal.size());
    assertEquals("2", kBetween0AndTotal.get(0).getId());
    assertEquals("3", kBetween0AndTotal.get(1).getId());

    // Test when k = number of stars in the file.
    command.setNumNeighbors(3);
    command.setStarsTree(new KDTree(3, threeStars));
    List<Star> kIsTotal = command.neighborsName("Star One", nameToStar(threeStars))
      .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());
    assertEquals(kIsTotal.size(), 2);
    assertEquals("2", kIsTotal.get(0).getId());
    assertEquals("3", kIsTotal.get(1).getId());

    // Test when k > number of stars in the file.
    command.setNumNeighbors(4);
    command.setStarsTree(new KDTree(3, threeStars));
    List<Star> kGreaterThanTotal = command.neighborsName("Star One", nameToStar(threeStars))
      .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());
    assertEquals(2, kGreaterThanTotal.size(), 2);
    assertEquals("2", kGreaterThanTotal.get(0).getId());
    assertEquals("3", kGreaterThanTotal.get(1).getId());
  }

  /**
   * Test the "neighbors" command that takes in an x,y,z-coordinate.
   */
  @Test
  public void testNeighborsPosition() {
    KDTree<Star> noStars = new KDTree(3, new ArrayList<>());
    KDTree<Star> oneStar = new KDTree(3, oneStar());
    KDTree<Star> threeStars = new KDTree(3, threeStars());

    // Test when K-d tree is empty.
    command.setNumNeighbors(1);
    command.setTargetPosition(new double[] {0, 0, 0});
    command.setStarsTree(noStars);
    List<Star> emptyInput = command.neighborsPosition()
      .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());;
    assertEquals(0, emptyInput.size());

    // Test file that only contains one star.
    command.setNumNeighbors(1);
    command.setTargetPosition(new double[] {0, 0, 0});
    command.setStarsTree(oneStar);
    List<Star> nearestStar = command.neighborsPosition()
      .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());;
    assertEquals(1, nearestStar.size());
    assertEquals("1", nearestStar.get(0).getId());

    // Test when k = 0.
    command.setNumNeighbors(0);
    command.setTargetPosition(new double[] {0, 0, 0});
    command.setStarsTree(threeStars);
    List<Star> kIs0 = command.neighborsPosition()
      .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());;
    assertEquals(0, kIs0.size());

    // Test when 0 < k < number of stars in the file.
    command.setNumNeighbors(2);
    command.setTargetPosition(new double[] {0, 0, 0});
    command.setStarsTree(threeStars);
    List<Star> kBetween0AndTotal = command.neighborsPosition()
      .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());;
    assertEquals(2, kBetween0AndTotal.size());
    assertEquals("1", kBetween0AndTotal.get(0).getId());
    assertEquals("2", kBetween0AndTotal.get(1).getId());

    // Test when k = number of stars in the file.
    command.setNumNeighbors(3);
    command.setTargetPosition(new double[] {0, 0, 0});
    command.setStarsTree(threeStars);
    List<Star> kIsTotal = command.neighborsPosition()
      .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());;
    assertEquals(3, kIsTotal.size());
    assertEquals("1", kIsTotal.get(0).getId());
    assertEquals("2", kIsTotal.get(1).getId());
    assertEquals("3", kIsTotal.get(2).getId());

    // Test when k > number of stars in the file.
    command.setNumNeighbors(4);
    command.setTargetPosition(new double[] {0, 0, 0});
    command.setStarsTree(threeStars);
    List<Star> kGreaterThanTotal = command.neighborsPosition()
      .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());;
    assertEquals(3, kGreaterThanTotal.size());
    assertEquals("1", kGreaterThanTotal.get(0).getId());
    assertEquals("2", kGreaterThanTotal.get(1).getId());
    assertEquals("3", kGreaterThanTotal.get(2).getId());
  }
}
