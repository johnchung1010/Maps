package edu.brown.cs.student.stars;

import java.util.Comparator;

/**
 * Class used to compare Star objects by their distances from a point.
 */
public class StarDistanceComparator implements Comparator<Star> {

  /**
   * Compare stars by their distances.
   *
   * @param s1 A star
   * @param s2 Another star
   * @return Comparison value
   */
  @Override
  public int compare(Star s1, Star s2) {
    boolean ascend = true;
    int compVal = Double.compare(s1.getDistance(), s2.getDistance());

    if (ascend) {
      return compVal;
    } else {
      return -1 * compVal;
    }
  }
}
