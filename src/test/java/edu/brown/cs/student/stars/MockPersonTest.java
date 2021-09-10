package edu.brown.cs.student.stars;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Class that tests the MockPerson class.
 */
public class MockPersonTest {

  /**
   * Test whether the constructor correctly constructs the mock person.
   */
  @Test
  public void testToString() {
    // All fields valid.
    MockPerson everything = new MockPerson("John", "Chung",
      "2/14/21", "jy_chung@brown.edu", "Male", "NewPem 4");
    assertEquals("First name: John, Last name: Chung, Datetime: 2/14/21, " +
      "Email address: jy_chung@brown.edu, Gender: Male, Street address: NewPem 4",
      everything.toString());

    MockPerson noEmail = new MockPerson("John", "Chung",
      "2/14/21", "", "Male", "NewPem 4");
    assertEquals("First name: John, Last name: Chung, Datetime: 2/14/21, " +
        "Email address: , Gender: Male, Street address: NewPem 4",
      noEmail.toString());

    // Invalid email address.
    MockPerson missingAt = new MockPerson("John", "Chung",
      "2/14/21", "jy_chungbrown.edu", "Male", "NewPem 4");
    assertEquals("First name: John, Last name: Chung, Datetime: 2/14/21, " +
        "Email address: Invalid email address, Gender: Male, Street address: NewPem 4",
      missingAt.toString());

    MockPerson missingPeriod = new MockPerson("John", "Chung",
      "2/14/21", "jy_chung@brownedu", "Male", "NewPem 4");
    assertEquals("First name: John, Last name: Chung, Datetime: 2/14/21, " +
        "Email address: Invalid email address, Gender: Male, Street address: NewPem 4",
      missingPeriod.toString());
  }
}
