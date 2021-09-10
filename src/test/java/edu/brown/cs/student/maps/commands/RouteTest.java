package edu.brown.cs.student.maps.commands;

import edu.brown.cs.student.maps.GenericEdge;
import edu.brown.cs.student.maps.Node;
import edu.brown.cs.student.maps.Way;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class RouteTest {

  Map m;
  Route r;

  public RouteTest() {
    ArrayList<String> args = new ArrayList<>();
    args.add("map");
    args.add("data/maps/smallMaps.sqlite3");
    m = Map.getInstance();
    m.map(args);
    r = new Route();
  }

  @Test
  public void findIntersectionTests() throws Exception {
    // normal intersections
    assertEquals(new Node("/n/2", new double[]{41.8206, -1.4}),
        r.findIntersection("'Chihiro Ave'", "'Kamaji Pl'"));
    assertEquals(new Node("/n/5", new double[]{41.8206, -1.4}),
        r.findIntersection("'Yubaba St'", "'Kamaji Pl'"));

    // two parallel streets
    Exception exception = assertThrows(Exception.class, () -> {
      r.findIntersection("'Chihiro Ave'", "'Yubaba St'");
    });
    String expectedMessage = "ERROR: These streets do not intersect";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));

    // non existent streets
    Exception exception2 = assertThrows(Exception.class, () -> {
      r.findIntersection("'Fake Street'", "'Fake Street2'");
    });
    String expectedMessage2 = "ERROR: These streets do not intersect";
    String actualMessage2 = exception2.getMessage();
    assertTrue(actualMessage2.contains(expectedMessage2));
  }

  @Test
  public void printRouteTests() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    // normal direction of paths
    ArrayList<GenericEdge> paths = new ArrayList<>();
    paths.add(new Way("/w/0",
        "", "",
        new Node("/n/0", new double[]{41.8206, -1.4}), new Node("/n/1", new double[]{41.8206, -1.4})));
    paths.add(new Way("/w/1",
        "", "",
        new Node("/n/1", new double[]{41.8206, -1.4}), new Node("/n/2", new double[]{41.8206, -1.4})));
    paths.add(new Way("/w/4",
        "", "",
        new Node("/n/2", new double[]{41.8206, -1.4}), new Node("/n/5", new double[]{41.8206, -1.4})));

    // reverse direction of paths
    ArrayList<GenericEdge> paths2 = new ArrayList<>();
    paths2.add(new Way("/w/6",
        null, null,
        new Node("/n/4", null), new Node("/n/5", null)));
    paths2.add(new Way("/w/5",
        null, null,
        new Node("/n/3", null), new Node("/n/4", null)));

    r.setStart(new Node("/n/0", new double[]{41.8206, -1.4}));
    r.printRoute(paths);
    r.setStart(new Node("/n/5", new double[]{41.8206, -1.4}));
    r.printRoute(paths2);

    // no paths
    r.setStart(new Node("/n/0", new double[]{41.8206, -1.4}));
    r.setEnd(new Node("/n/1", new double[]{41.8206, -1.4}));
    r.printRoute(new ArrayList<>());

    String expectedOutput  =
        "/n/0 -> /n/1 : /w/0\n" +
        "/n/1 -> /n/2 : /w/1\n" +
        "/n/2 -> /n/5 : /w/4\n" +
        "/n/5 -> /n/4 : /w/6\n" +
        "/n/4 -> /n/3 : /w/5\n" +
        "/n/0 -/- /n/1\n";

    assertEquals(expectedOutput, outputStream.toString());
  }

}
