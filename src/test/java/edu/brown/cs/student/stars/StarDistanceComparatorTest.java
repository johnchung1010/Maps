package edu.brown.cs.student.stars;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Class that tests the StarDistanceComparator class.
 */
public class StarDistanceComparatorTest {

  /**
   * Test whether stars are correctly compared by their distances.
   */
  @Test
  public void compare() {
    Star s1 = new Star("1", "1",0,0,0);
    Star s2 = new Star("2", "2",1,1,1);
    Star s3 = new Star("3", "3",0,0,0);

    s1.setDistance(0);
    s2.setDistance(1);
    s3.setDistance(0);

    StarDistanceComparator comp = new StarDistanceComparator();
    assertEquals(-1, comp.compare(s1, s2));
    assertEquals(0, comp.compare(s1, s3));
    assertEquals(1, comp.compare(s2, s3));
  }
}
