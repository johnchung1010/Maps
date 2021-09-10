package edu.brown.cs.student.stars.commands;

import edu.brown.cs.student.common.Commands;
import edu.brown.cs.student.stars.Star;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Class that tests the "stars" command.
 */
public class StarsTest {

  /**
   * Test the "stars" command.
   */
  @Test
  public void testStars() {
    List<String> oneStarCommand = Commands.getCommandArguments("stars data/stars/one-star.csv");
    Stars.getInstance().stars(oneStarCommand);
    List<Star> oneStar = Stars.getInstance().getStarsList();
    assertEquals(1, oneStar.size());
    assertEquals("Lonely Star", oneStar.get(0).getName());

    List<String> tenStarsCommand = Commands.getCommandArguments("stars data/stars/ten-star.csv");
    Stars.getInstance().stars(tenStarsCommand);
    List<Star> tenStars = Stars.getInstance().getStarsList();
    assertEquals(10, tenStars.size());

    List<String> manyStarsCommand = Commands.getCommandArguments("stars data/stars/stardata.csv");
    Stars.getInstance().stars(manyStarsCommand);
    List<Star> manyStars = Stars.getInstance().getStarsList();
    assertEquals(119617, manyStars.size());
  }

  /**
   * Test whether the header of a star file is valid.
   */
  @Test
  public void testValidateHeader() {
    // Valid header.
    assertTrue(Stars.getInstance().validateHeader(new String[] {
      "StarID",
      "ProperName",
      "X",
      "Y",
      "Z"}));
    // Contain spaces.
    assertFalse(Stars.getInstance().validateHeader(new String[] {
      " StarID",
      " ProperName",
      " X",
      " Y",
      " Z"}));
    // Wrong order.
    assertFalse(Stars.getInstance().validateHeader(new String[] {
      "StarID",
      "ProperName",
      "Z",
      "Y",
      "X"}));
    // Lowercase.
    assertFalse(Stars.getInstance().validateHeader(new String[] {
      "stariD",
      "propername",
      "x",
      "y",
      "z"}));
  }
}
