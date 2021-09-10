package edu.brown.cs.student.stars;

/**
 * Class representing a person.
 */
public class MockPerson {

  private final String firstName;
  private final String lastName;
  private final String datetime;
  private final String email;
  private final String gender;
  private final String streetAddress;

  /**
   * Constructor.
   *
   * @param firstNameIn     First name
   * @param lastNameIn      Last name
   * @param datetimeIn      Datetime
   * @param emailIn         Email address
   * @param genderIn        Gender
   * @param streetAddressIn Street address
   */
  public MockPerson(String firstNameIn, String lastNameIn, String datetimeIn,
                    String emailIn, String genderIn, String streetAddressIn) {
    firstName = firstNameIn;
    lastName = lastNameIn;
    datetime = datetimeIn;
    gender = genderIn;
    streetAddress = streetAddressIn;

    // Verify email address.
    // Empty string assumes user is uncomfortable sharing their email address.
    if (emailIn.equals("")) {
      email = emailIn;
      // A valid email address contains an "@" symbol followed by a ".".
    } else {
      if (emailIn.contains("@")) {
        if (emailIn.split("@")[1].contains(".")) {
          email = emailIn;
        } else {
          email = "Invalid email address";
        }
      } else {
        email = "Invalid email address";
      }
    }
  }

  /**
   * Return the attributes of a person.
   *
   * @return Attributes of a person
   */
  public String toString() {
    return "First name: " + firstName + ", Last name: " + lastName
      + ", Datetime: " + datetime + ", Email address: " + email
      + ", Gender: " + gender + ", Street address: " + streetAddress;
  }
}
