package edu.brown.cs.student.maps.commands;

import org.junit.Test;

import java.sql.SQLException;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class NearestTest {

  Map m;
  Nearest n;

  public NearestTest() throws SQLException, ClassNotFoundException {
    ArrayList<String> args = new ArrayList<>();
    args.add("map");
    args.add("data/maps/smallMaps.sqlite3");
    m = Map.getInstance();
    m.map(args);
    n = new Nearest();
  }

  @Test
  public void MainNearestTest() throws Exception {

    // tests with smaller database
    n.setLatitude(0);
    n.setLongitude(0);
    
    assertEquals("/n/0", n.getNearestTraversableNode().getId());

    n.setLatitude(41.8206);
    n.setLongitude(-71.4);
    assertEquals("/n/2", n.getNearestTraversableNode().getId());

    n.setLatitude(45);
    n.setLongitude(-71.4003);
    assertEquals("/n/5", n.getNearestTraversableNode().getId());

    ArrayList<String> args = new ArrayList<>();
    args.add("map");
    args.add("data/maps/maps.sqlite3");
    m.map(args);

    // test with large map database
    n.setLatitude(0);
    n.setLongitude(0);
    assertEquals("/n/4113.7055.527769081", n.getNearestTraversableNode().getId());

  }

  @Test
  public void NearestEdgeCaseTests() throws Exception {

    ArrayList<String> args = new ArrayList<>();
    args.add("map");
    args.add("data/maps/smallMaps.sqlite3");
    m.map(args);

    // extremely distant start point
    n.setLongitude(-100000000);
    n.setLatitude(-100000000);

    assertEquals("/n/3", n.getNearestTraversableNode().getId());

    // start point identical to a node's coordinates
    n.setLatitude(41.8206);
    n.setLongitude(-71.4003);

    assertEquals("/n/5", n.getNearestTraversableNode().getId());
  }
}
