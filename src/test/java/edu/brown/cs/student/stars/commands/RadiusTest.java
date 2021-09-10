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
 * Class that tests the "radius" command.
 */
public class RadiusTest {

  private final Radius command = new Radius();

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
   * Test the "radius" command that takes in the name of a star.
   *
   * @throws Exception Name of star does not match any of the stars in the file
   */
  @Test
  public void testRadiusName() throws Exception {
    List<Star> oneStar = oneStar();
    List<Star> threeStars = threeStars();

    // Test when input name is the name of the only star in the file.
    command.setRadius(10);
    command.setStarsTree(new KDTree<>(3, oneStar));
    List<Star> isOnlyStar = command.radiusName("Lonely Star", nameToStar(oneStar))
      .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());
    assertEquals(0, isOnlyStar.size());

    // Test when radius is 0.
    command.setRadius(0);
    command.setStarsTree(new KDTree<>(3, threeStars));
    List<Star> rIs0 = command.radiusName("Star One", nameToStar(threeStars))
      .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());
    assertEquals(0, rIs0.size());

    // Test when some stars within radius.
    command.setRadius(1);
    command.setStarsTree(new KDTree<>(3, threeStars));
    List<Star> someInRange = command.radiusName("Star One", nameToStar(threeStars))
      .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());
    assertEquals(1, someInRange.size());
    assertEquals("2", someInRange.get(0).getId());

    // Test when all stars within radius.
    command.setRadius(10);
    command.setStarsTree(new KDTree<>(3, threeStars));
    List<Star> allInRange = command.radiusName("Star One", nameToStar(threeStars))
      .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());
    assertEquals(2, allInRange.size());
    assertEquals("2", allInRange.get(0).getId());
    assertEquals("3", allInRange.get(1).getId());
  }

  /**
   * Test the "radius" command that takes in an x,y,z-coordinate.
   */
  @Test
  public void testRadiusPosition() {
    KDTree<Star> noStars = new KDTree<>(3, new ArrayList<>());
    KDTree<Star> oneStar = new KDTree<>(3, oneStar());
    KDTree<Star> threeStars = new KDTree<>(3, threeStars());

    // Test when K-d tree is empty.
    command.setRadius(1);
    command.setTargetPosition(new double[] {0, 0, 0});
    command.setStarsTree(noStars);
    List<Star> emptyInput = command.radiusPosition()
      .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());
    assertEquals(0, emptyInput.size());

    // Test file that only contains one star.
    command.setRadius(50);
    command.setTargetPosition(new double[] {0, 0, 0});
    command.setStarsTree(oneStar);
    List<Star> singleStar = command.radiusPosition()
      .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());
    assertEquals(1, singleStar.size());
    assertEquals("1", singleStar.get(0).getId());

    // Test when radius is 0.
    command.setRadius(0);
    command.setTargetPosition(new double[] {0, 0, 0});
    command.setStarsTree(threeStars);
    List<Star> rIs0 = command.radiusPosition()
      .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());
    assertEquals(0, rIs0.size());

    // Test when some stars within radius.
    command.setRadius(1);
    command.setTargetPosition(new double[] {0, 0, 0});
    command.setStarsTree(threeStars);
    List<Star> someInRange = command.radiusPosition()
      .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());
    assertEquals(1, someInRange.size());
    assertEquals("1", someInRange.get(0).getId());

    // Test when all stars within radius.
    command.setRadius(10);
    command.setTargetPosition(new double[] {0, 0, 0});
    command.setStarsTree(threeStars);
    List<Star> allInRange = command.radiusPosition()
      .stream().map(KDTree.KDNode::getDatum).collect(Collectors.toList());
    assertEquals(3, allInRange.size());
    assertEquals("1", allInRange.get(0).getId());
    assertEquals("2", allInRange.get(1).getId());
    assertEquals("3", allInRange.get(2).getId());
  }
}
