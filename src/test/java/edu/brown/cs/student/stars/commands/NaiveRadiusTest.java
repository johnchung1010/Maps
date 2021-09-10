package edu.brown.cs.student.stars.commands;

import edu.brown.cs.student.stars.Star;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Class that tests the "naive_radius" command.
 */
public class NaiveRadiusTest {

  private final NaiveRadius command = new NaiveRadius();

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
   * Test the "naive_radius" command that takes in the name of a star.
   */
  @Test
  public void testNaiveRadiusName() throws Exception {
    // Test when input name is the name of the only star in the file.
    List<Star> isOnlyStar = command.naiveRadiusName("Lonely Star", 10, oneStar());
    assertEquals(0, isOnlyStar.size());

    // Test when radius is 0.
    List<Star> rIs0 = command.naiveRadiusName("Star One", 0, threeStars());
    assertEquals(0, rIs0.size());

    // Test when some stars within radius.
    List<Star> someInRange = command.naiveRadiusName("Star One", 1, threeStars());
    assertEquals(1, someInRange.size());
    assertEquals("2", someInRange.get(0).getId());

    // Test when all stars within radius.
    List<Star> allInRange = command.naiveRadiusName("Star One", 10, threeStars());
    assertEquals(2, allInRange.size());
    assertEquals("2", allInRange.get(0).getId());
    assertEquals("3", allInRange.get(1).getId());
  }

  /**
   * Test the "naive_radius" command that takes in an x,y,z-coordinate.
   */
  @Test
  public void testNaiveRadiusPosition() {
    // Test when ArrayList is empty.
    List<Star> emptyInput = command.naiveRadiusPosition(new double[] {0, 0, 0}, 1, new ArrayList<>());
    assertEquals(0, emptyInput.size());

    // Test file that only contains one star.
    List<Star> singleStar = command.naiveRadiusPosition(new double[] {0, 0, 0}, 50, oneStar());
    assertEquals(1, singleStar.size());
    assertEquals("1", singleStar.get(0).getId());

    // Test when radius is 0.
    List<Star> rIs0 = command.naiveRadiusPosition(new double[] {0, 0, 0}, 0, threeStars());
    assertEquals(0, rIs0.size());

    // Test when some stars within radius.
    List<Star> someInRange = command.naiveRadiusPosition(new double[] {0, 0, 0}, 1, threeStars());
    assertEquals(1, someInRange.size());
    assertEquals("1", someInRange.get(0).getId());

    // Test when all stars within radius.
    List<Star> allInRange = command.naiveRadiusPosition(new double[] {0, 0, 0}, 10, threeStars());
    assertEquals(3, allInRange.size());
    assertEquals("1", allInRange.get(0).getId());
    assertEquals("2", allInRange.get(1).getId());
    assertEquals("3", allInRange.get(2).getId());
  }
}
