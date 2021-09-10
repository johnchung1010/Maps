package edu.brown.cs.student.stars.commands;

import edu.brown.cs.student.common.CSVParser;
import edu.brown.cs.student.common.Commands;
import edu.brown.cs.student.stars.MockPerson;

import java.util.List;

/**
 * Class representing the "mock" command.
 */
public class Mock implements Commands {

  /**
   * Execute the "mock" command.
   *
   * @param command User input
   */
  @Override
  public void execute(String command) {
    List<String> commandArgs = Commands.getCommandArguments(command);
    if (commandArgs.size() == 2) {
      mock(commandArgs);
    } else {
      System.out.println("ERROR: Incorrect number of arguments.");
    }
  }

  /**
   * Create then print a MockPerson for each person datum of the CSV file.
   *
   * @param commandArgs ArrayList of command arguments
   */
  public void mock(List<String> commandArgs) {
    String filePath = commandArgs.get(1);
    CSVParser csvParser = new CSVParser(6, filePath);

    try {
      List<String[]> parsedData = csvParser.parse();
      String[] header = parsedData.get(0);
      boolean validHeader = validateHeader(header);

      if (!validHeader) {
        System.out.println("ERROR: Invalid input file. Incorrect header.");
      } else {
        List<String[]> personData = parsedData.subList(1, parsedData.size());

        for (String[] datum : personData) {
          String firstName = datum[0];
          String lastName = datum[1];
          String datetime = datum[2];
          String email = datum[3];
          String gender = datum[4];
          String streetAddress = datum[5];

          MockPerson person = new MockPerson(
              firstName, lastName, datetime, email, gender, streetAddress);
          System.out.println(person.toString());
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Validate header line of an input file.
   *
   * @param header Header line of a file
   * @return Boolean value
   */
  public boolean validateHeader(String[] header) {
    return header[0].equals("First Name")
      && header[1].equals("Last Name")
      && header[2].equals("Datetime")
      && header[3].equals("Email Address")
      && header[4].equals("Gender")
      && header[5].equals("Street Address");
  }
}
