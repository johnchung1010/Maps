package edu.brown.cs.student.common;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Class that tests the Commands interface.
 */
public class CommandsTest {

  /**
   ** Test whether the user input is correctly split into command arguments.
   */
  @Test
  public void testGetCommandArguments() {
    // Test valid user input.
    List<String> validInput = Commands.getCommandArguments("stars data/stars/stardata.csv");
    assertEquals(2, validInput.size());
    assertEquals("stars", validInput.get(0));
    assertEquals("data/stars/stardata.csv", validInput.get(1));

    // Test no user input.
    List<String> noInput = Commands.getCommandArguments("");
    assertEquals(0, noInput.size());

    // Test numbers as arguments.
    List<String> numbersTest = Commands.getCommandArguments("naive_neighbors 5 0 12.3865 -8937");
    assertEquals(5, numbersTest.size());
    assertEquals("naive_neighbors", numbersTest.get(0));
    assertEquals("5", numbersTest.get(1));
    assertEquals("0", numbersTest.get(2));
    assertEquals("12.3865", numbersTest.get(3));
    assertEquals("-8937", numbersTest.get(4));

    // Test name of a star that contains a space.
    List<String> spaceInName = Commands.getCommandArguments("naive_neighbors 12 \"Lonely Star\"");
    assertEquals(3, spaceInName.size());
    assertEquals("naive_neighbors", spaceInName.get(0));
    assertEquals("12", spaceInName.get(1));
    assertEquals("\"Lonely Star\"", spaceInName.get(2));

    // Test star that has no name.
    List<String> noName = Commands.getCommandArguments("naive_radius 5 \"\"");
    assertEquals(3, noName.size());
    assertEquals("naive_radius", noName.get(0));
    assertEquals("5", noName.get(1));
    assertEquals("\"\"", noName.get(2));
  }
}
