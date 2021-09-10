package edu.brown.cs.student.maps.commands;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MapTest {

  Map m;

  @Test
  public void testMap() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    ArrayList<String> args = new ArrayList<>();
    args.add("map");
    args.add("data/maps/smallMaps.sqlite3");
    ArrayList<String> args2 = new ArrayList<>();
    args2.add("map");
    args2.add("data/maps/maps.sqlite3");
    // non existent file
    ArrayList<String> args3 = new ArrayList<>();
    args3.add("map");
    args3.add("data/maps/test.sqlite3");
    m = Map.getInstance();
    m.map(args);
    m.map(args2);
    m.map(args3);

    String expectedOutput =
      "map set to data/maps/smallMaps.sqlite3\n" +
        "map set to data/maps/maps.sqlite3\n" +
        "ERROR: File not found.\n";

    assertEquals(expectedOutput, outputStream.toString());

  }
}