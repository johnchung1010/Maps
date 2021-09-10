package edu.brown.cs.student.maps;

import edu.brown.cs.student.maps.commands.Map;
import org.junit.Test;

import java.sql.SQLException;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class AStarTest {

  Map m;
  AStar d;
  
  public AStarTest() throws SQLException, ClassNotFoundException {
    ArrayList<String> args = new ArrayList<>();
    args.add("map");
    args.add("data/maps/smallMaps.sqlite3");
    m = Map.getInstance();
    m.map(args);
    d = new AStar(new Node("/n/0", new double[]{41.82, -71.4}),
        new Node("/n/5", new double[]{41.8206, -71.4003}));
  }

  @Test
  public void normalDijkstraTests() {
    // 3 path solution
    ArrayList<Way> expectedResult = new ArrayList<>();
    expectedResult.add(new Way("/w/0",
        null, null, null, null));
    expectedResult.add(new Way("/w/1",
        null, null, null, null));
    expectedResult.add(new Way("/w/4",
        null, null, null, null));
    assertEquals(expectedResult, d.getShortestPath());

    // 2 path solution
    d.setStart(new Node("/n/2", new double[]{42.8206, -71.4}));
    d.setEnd(new Node("/n/4", new double[]{42.8203, -71.4003}));

    ArrayList<Way> expectedResult2 = new ArrayList<>();
    expectedResult2.add(new Way("/w/1",
        null, null, null, null));
    expectedResult2.add(new Way("/w/3",
        null, null, null, null));
    assertEquals(expectedResult2, d.getShortestPath());

    // 1 path solution
    d.setStart(new Node("/n/3", new double[]{41.82, -71.4003}));

    ArrayList<Way> expectedResult3 = new ArrayList<>();
    expectedResult3.add(new Way("/w/5",
        null, null, null, null));
    assertEquals(expectedResult3, d.getShortestPath());

  }

  @Test
  public void dijkstraEdgeCases() {
    // 0 path solution
    d.setStart(new Node("/n/3", new double[]{41.82, -71.4003}));
    d.setEnd(new Node("/n/3", new double[]{41.82, -71.4003}));
    assertEquals(new ArrayList<>(), d.getShortestPath());

    // unreachable node
    d.setStart(new Node("/n/99", new double[]{0, 0}));
    assertEquals(new ArrayList<>(), d.getShortestPath());

    // equidistant nodes
    ArrayList<String> args = new ArrayList<>();
    args.add("map");
    args.add("data/test/square.sqlite3");
    m.map(args);

    d.setStart(new Node("/n/0", new double[]{40, 40}));
    d.setEnd(new Node("/n/3", new double[]{41, 41}));


    ArrayList<GenericEdge> result = d.getShortestPath();
    ArrayList<GenericEdge> expectedResult = new ArrayList<>();
    expectedResult.add(new Way("/w/0",
        null, null,
        new Node("/n/0", new double[]{40, 40}),
        new Node("/n/1", new double[]{41, 40})));
    expectedResult.add(new Way("/w/2",
        null, null,
        new Node("/n/1", new double[]{41, 40}),
        new Node("/n/3", new double[]{41, 41})));

    double totalDist = 0;
    double totalExpectedDist = 0;

    // checking whether the total path weight is the same
    for (GenericEdge edge : result) {
      totalDist += edge.getWeight();
    }

    for (GenericEdge expectedEdge : expectedResult) {
      totalExpectedDist += expectedEdge.getWeight();
    }

    assertEquals(totalExpectedDist, totalDist, 0.001);


    ArrayList<String> args2 = new ArrayList<>();
    args2.add("map");
    args2.add("data/test/grid.sqlite3");
    m.map(args2);

    d.setStart(new Node("/n/0", new double[]{40, 40}));
    d.setEnd(new Node("/n/6", new double[]{41, 43}));

    ArrayList<GenericEdge> result2 = d.getShortestPath();
    ArrayList<GenericEdge> expectedResult2 = new ArrayList<>();
    expectedResult2.add(new Way("/w/0",
        null, null,
        new Node("/n/0", new double[]{40, 40}),
        new Node("/n/1", new double[]{41, 40})));
    expectedResult2.add(new Way("/w/2",
        null, null,
        new Node("/n/1", new double[]{41, 40}),
        new Node("/n/3", new double[]{41, 41})));
    expectedResult2.add(new Way("/w/4",
        null, null,
        new Node("/n/3", new double[]{41, 41}),
        new Node("/n/4", new double[]{41, 42})));
    expectedResult2.add(new Way("/w/7",
        null, null,
        new Node("/n/4", new double[]{41, 42}),
        new Node("/n/6", new double[]{41, 43})));

    double totalDist2 = 0;
    double totalExpectedDist2 = 0;

    // checking whether the total path weight is the same
    for (GenericEdge edge : result2) {
      totalDist2 += edge.getWeight();
    }

    for (GenericEdge expectedEdge : expectedResult2) {
      totalExpectedDist2 += expectedEdge.getWeight();
    }

    assertEquals(totalExpectedDist2, totalDist2, 0.001);

    // only one node in database

    ArrayList<String> args3 = new ArrayList<>();
    args3.add("map");
    args3.add("data/test/one_node.sqlite3");
    m.map(args3);

    d.setStart(new Node("/n/0", new double[]{40, 40}));
    d.setEnd(new Node("/n/0", new double[]{40, 40}));

    assertEquals(new ArrayList<>(), d.getShortestPath());

  }
}
