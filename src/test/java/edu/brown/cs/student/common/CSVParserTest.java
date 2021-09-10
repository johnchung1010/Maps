package edu.brown.cs.student.common;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CSVParserTest {

  CSVParser c;

  @Test
  public void CSVTests() throws Exception {

    // missing inputs
    c = new CSVParser(2, "./data/test/missing-inputs.csv");

    ArrayList<String[]> expectedParse = new ArrayList<>();
    expectedParse.add(new String[]{"1", "a"});
    expectedParse.add(new String[]{"2", "b"});
    expectedParse.add(new String[]{"3", "c"});
    expectedParse.add(new String[]{"", "d"});
    expectedParse.add(new String[]{"5", ""});

    assertEquals(expectedParse.get(3)[0], c.parse().get(3)[0]);
    assertEquals(expectedParse.get(4)[1], c.parse().get(4)[1]);
    assertEquals(expectedParse.get(4)[0], c.parse().get(4)[0]);

    // Empty file
    c = new CSVParser(0, "./data/test/empty.csv");
    assertEquals(new ArrayList<>(), c.parse());
  }

}
