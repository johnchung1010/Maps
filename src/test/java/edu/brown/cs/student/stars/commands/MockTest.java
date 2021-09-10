package edu.brown.cs.student.stars.commands;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Class that tests the "mock" command.
 */
public class MockTest {

  private final Mock mockCommand = new Mock();

  /**
   * Test whether the header of a mock file is valid.
   */
  @Test
  public void testValidateHeader() {
    // Valid header.
    assertTrue(mockCommand.validateHeader(new String[] {
      "First Name",
      "Last Name",
      "Datetime",
      "Email Address",
      "Gender",
      "Street Address"}));
    // Contain spaces.
    assertFalse(mockCommand.validateHeader(new String[] {
      " First Name",
      " Last Name",
      " Datetime",
      " Email Address",
      " Gender",
      " Street Address"}));
    // Lowercase.
    assertFalse(mockCommand.validateHeader(new String[] {
      "first name",
      "last name",
      "datetime",
      "email address",
      "gender",
      "street address"}));
  }
}
