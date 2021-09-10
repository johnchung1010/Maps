package edu.brown.cs.student.stars.commands;

import edu.brown.cs.student.stars.Star;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Class that tests the "naive_neighbors" command.
 */
public class NaiveNeighborsTest {

  private final NaiveNeighbors command = new NaiveNeighbors();

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
   * Test the "naive_neighbors" command that takes in the name of a star.
   *
   * @throws Exception Name of star does not match any of the stars in the file
   */
  @Test
  public void testNaiveNeighborsName() throws Exception {
    // Test when input name is the name of the only star in the file.
    List<Star> isOnlyStar = command.naiveNeighborsName("Lonely Star", 1, oneStar());
    assertEquals(0, isOnlyStar.size());

    // Test when k = 0.
    List<Star> kIs0 = command.naiveNeighborsName("Star One", 0, threeStars());
    assertEquals(0, kIs0.size());

    // Test when 0 < k < number of stars in the file.
    List<Star> kBetween0AndTotal = command.naiveNeighborsName("Star One", 2, threeStars());
    assertEquals(2, kBetween0AndTotal.size());
    assertEquals("2", kBetween0AndTotal.get(0).getId());
    assertEquals("3", kBetween0AndTotal.get(1).getId());

    // Test when k = number of stars in the file.
    List<Star> kIsTotal = command.naiveNeighborsName("Star One", 3, threeStars());
    assertEquals(kIsTotal.size(), 2);
    assertEquals("2", kIsTotal.get(0).getId());
    assertEquals("3", kIsTotal.get(1).getId());

    // Test when k > number of stars in the file.
    List<Star> kGreaterThanTotal = command.naiveNeighborsName("Star One", 4, threeStars());
    assertEquals(2, kGreaterThanTotal.size(), 2);
    assertEquals("2", kGreaterThanTotal.get(0).getId());
    assertEquals("3", kGreaterThanTotal.get(1).getId());
  }

  /**
   * Test the "naive_neighbors" command that takes in an x,y,z-coordinate.
   */
  @Test
  public void testNaiveNeighborsPosition() {
    // Test when ArrayList is empty.
    List<Star> emptyInput = command.naiveNeighborsPosition(new double[] {0, 0, 0}, 1, new ArrayList<>());
    assertEquals(0, emptyInput.size());

    // Test file that only contains one star.
    List<Star> nearestStar = command.naiveNeighborsPosition(new double[] {0, 0, 0}, 1, oneStar());
    assertEquals(1, nearestStar.size());
    assertEquals("1", nearestStar.get(0).getId());

    // Test when k = 0.
    List<Star> kIs0 = command.naiveNeighborsPosition(new double[] {0, 0, 0}, 0, threeStars());
    assertEquals(0, kIs0.size());

    // Test when 0 < k < number of stars in the file.
    List<Star> kBetween0AndTotal = command.naiveNeighborsPosition(new double[] {0, 0, 0}, 2, threeStars());
    assertEquals(2, kBetween0AndTotal.size());
    assertEquals("1", kBetween0AndTotal.get(0).getId());
    assertEquals("2", kBetween0AndTotal.get(1).getId());

    // Test when k = number of stars in the file.
    List<Star> kIsTotal = command.naiveNeighborsPosition(new double[] {0, 0, 0}, 3, threeStars());
    assertEquals(3, kIsTotal.size());
    assertEquals("1", kIsTotal.get(0).getId());
    assertEquals("2", kIsTotal.get(1).getId());
    assertEquals("3", kIsTotal.get(2).getId());

    // Test when k > number of stars in the file.
    List<Star> kGreaterThanTotal = command.naiveNeighborsPosition(new double[] {0, 0, 0}, 4, threeStars());
    assertEquals(3, kGreaterThanTotal.size());
    assertEquals("1", kGreaterThanTotal.get(0).getId());
    assertEquals("2", kGreaterThanTotal.get(1).getId());
    assertEquals("3", kGreaterThanTotal.get(2).getId());
  }
}
