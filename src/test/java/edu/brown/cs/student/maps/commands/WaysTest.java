package edu.brown.cs.student.maps.commands;

import org.junit.Test;

import java.sql.SQLException;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class WaysTest {

  Map m;
  Ways w;

  public WaysTest() throws SQLException, ClassNotFoundException {
    ArrayList<String> args = new ArrayList<>();
    args.add("map");
    args.add("data/maps/smallMaps.sqlite3");
    m = Map.getInstance();
    m.map(args);
    w = new Ways();
  }

  @Test
  public void mainWaysTest() throws Exception {

    // Normal test
    w.setCoords(41.8208, -72, 41.8206, -70);
    ArrayList<String> expectedResult = new ArrayList<>();
    expectedResult.add("/w/1");
    expectedResult.add("/w/4");
    expectedResult.add("/w/6");
    assertEquals(expectedResult, w.getWays());

    // Returns all ways
    w.setCoords(41.8206, -72, 41.82, -70);
    ArrayList<String> expectedResult2 = new ArrayList<>();
    expectedResult2.add("/w/0");
    expectedResult2.add("/w/1");
    expectedResult2.add("/w/2");
    expectedResult2.add("/w/3");
    expectedResult2.add("/w/4");
    expectedResult2.add("/w/5");
    expectedResult2.add("/w/6");
    assertEquals(expectedResult2, w.getWays());

    // Returns no ways
    w.setCoords(41.8206, -60, 41.82, -50);
    ArrayList<String> expectedResult3 = new ArrayList<>();
    assertEquals(expectedResult3, w.getWays());

  }

  @Test
  public void edgeCasesWays() throws Exception {

    // Box is a single point
    w.setCoords(41.8203, -71.4, 41.8203, -71.4);
    ArrayList<String> expectedResult = new ArrayList<>();
    expectedResult.add("/w/0");
    expectedResult.add("/w/1");
    expectedResult.add("/w/3");
    assertEquals(expectedResult, w.getWays());

    w.setCoords(0, 0, 0, 0);
    ArrayList<String> expectedResult2 = new ArrayList<>();
    assertEquals(expectedResult2, w.getWays());

    // All points within bounding box
    w.setCoords(100, -100,  -100, 100);
    ArrayList<String> expectedResult3 = new ArrayList<>();
    expectedResult3.add("/w/0");
    expectedResult3.add("/w/1");
    expectedResult3.add("/w/2");
    expectedResult3.add("/w/3");
    expectedResult3.add("/w/4");
    expectedResult3.add("/w/5");
    expectedResult3.add("/w/6");
    assertEquals(expectedResult3, w.getWays());
  }
}
