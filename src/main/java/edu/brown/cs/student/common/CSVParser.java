package edu.brown.cs.student.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a CSV parser.
 */
public class CSVParser {

  private final int numColumns;
  private final String filePath;

  /**
   * Constructor.
   *
   * @param numColumnsIn Expected number of columns
   * @param filePathIn   File path
   */
  public CSVParser(int numColumnsIn, String filePathIn) {
    numColumns = numColumnsIn;
    filePath = filePathIn;
  }

  /**
   * Parse the CSV file.
   *
   * @return List of rows from the CSV file represented as arrays of strings
   * @throws Exception File has malformed data or not found
   */
  public List<String[]> parse() throws Exception {
    File csvFile = new File(filePath);

    if (!csvFile.exists()) {
      throw new Exception("ERROR: File not found.");
    } else {
      BufferedReader fileReader = new BufferedReader(new FileReader(csvFile));
      List<String[]> data = new ArrayList<>();
      String row;

      while ((row = fileReader.readLine()) != null) {
        String[] datum = row.split(",", -1);
        if (datum.length == numColumns) {
          data.add(datum);
        } else {
          throw new Exception("ERROR: File contains malformed data.");
        }
      }
      return data;
    }
  }
}
