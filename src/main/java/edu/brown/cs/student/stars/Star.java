package edu.brown.cs.student.stars;

import edu.brown.cs.student.common.HasCoordinate;

/**
 * Class representing star datum loaded from CSV file.
 */
public class Star implements HasCoordinate {

  private final String id;
  private final String name;
  private final double[] position;
  private double distance;

  /**
   * Constructor.
   *
   * @param idIn       Star ID
   * @param nameIn     Name of a star
   * @param x the x coordinate of the star
   * @param y the y coordinate of the star
   * @param z the z coordinate of the star
   */
  public Star(String idIn, String nameIn, double x, double y, double z) {
    id = idIn;
    name = nameIn;
    position = new double[] {x, y, z};
  }

  @Override
  public double[] getCoordinate() {
    return position;
  }

  /**
   * Calculates the euclidean distance between this star and an end point.
   * @param endPoint - the point to calculate the distance to
   * @return the euclidean distance (as a double) to the endPoint
   */
  public double calcDistance(double[] endPoint) {
    double sum = 0;
    for (int i = 0; i < position.length; i++) {
      sum += Math.pow(position[i] - endPoint[i], 2);
    }
    return Math.sqrt(sum);
  }

  /**
   * Getter.
   *
   * @return Star ID
   */
  public String getId() {
    return id;
  }

  /**
   * Getter.
   *
   * @return Name of a star
   */
  public String getName() {
    return name;
  }

  /**
   * Getter.
   *
   * @return Distance between star and another point
   */
  public double getDistance() {
    return distance;
  }

  /**
   * Setter.
   *
   * @param distanceIn Distance between star and another point
   */
  public void setDistance(double distanceIn) {
    distance = distanceIn;
  }
}
