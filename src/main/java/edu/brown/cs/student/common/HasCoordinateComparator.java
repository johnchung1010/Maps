package edu.brown.cs.student.common;

import java.util.Comparator;

/**
 * Class used to compare objects with coordinates by their relevant axis coordinates.
 */
public class HasCoordinateComparator implements Comparator<HasCoordinate> {

  private final int axis;
  private boolean ascend;

  /**
   * Constructor.
   *
   * @param axisIn   Relevant axis
   * @param ascendIn Boolean value
   */
  public HasCoordinateComparator(int axisIn, boolean ascendIn) {
    axis = axisIn;
    ascend = ascendIn;
  }

  /**
   * Getter.
   *
   * @return Boolean value
   */
  public boolean getAscend() {
    return ascend;
  }

  /**
   * Compare objects with coordinates by their relevant axis coordinates.
   *
   * @param coord1 An object with a coordinate
   * @param coord2 Another object with a coordinate
   * @return Comparison value
   */
  @Override
  public int compare(HasCoordinate coord1, HasCoordinate coord2) {
    int compVal = Double.compare(
        coord1.getCoordinate()[axis],
        coord2.getCoordinate()[axis]);

    if (ascend) {
      return compVal;
    } else {
      return -1 * compVal;
    }
  }
}
