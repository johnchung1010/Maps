package edu.brown.cs.student.stars.commands;

import edu.brown.cs.student.common.CSVParser;
import edu.brown.cs.student.common.Commands;
import edu.brown.cs.student.common.KDTree;
import edu.brown.cs.student.stars.Star;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Class representing the "stars" command.
 */
public final class Stars implements Commands {

  private List<Star> starsList;
  private Hashtable<String, Star> nameToStar;
  private KDTree<Star> starsTree;
  private static final Stars INSTANCE = new Stars();

  /**
   * Getter.
   *
   * @return Stars data loaded from CSV file
   */
  public List<Star> getStarsList() {
    return new ArrayList<>(starsList);
  }

  /**
   * Getter.
   *
   * @return Stars data loaded from CSV file
   */
  public Hashtable<String, Star> getNameToStar() {
    return new Hashtable<>(nameToStar);
  }

  /**
   * Getter.
   *
   * @return Stars data loaded from CSV file
   */
  public KDTree<Star> getStarsTree() {
    return starsTree;
  }

  /**
   * Getter.
   *
   * @return The only instance of the Stars object
   */
  public static Stars getInstance() {
    return INSTANCE;
  }

  /**
   * Execute the "stars" command.
   *
   * @param command User input
   */
  @Override
  public void execute(String command) {
    List<String> commandArgs = Commands.getCommandArguments(command);
    if (commandArgs.size() == 2) {
      stars(commandArgs);
    } else {
      System.out.println("ERROR: Incorrect number of arguments.");
    }
  }

  /**
   * Load in star position information.
   *
   * @param commandArgs List of command arguments
   */
  public void stars(List<String> commandArgs) {
    starsList = new ArrayList<>();
    nameToStar = new Hashtable<>();
    String filePath = commandArgs.get(1);
    CSVParser csvParser = new CSVParser(5, filePath);

    try {
      List<String[]> parsedData = csvParser.parse();
      String[] header = parsedData.get(0);
      boolean validHeader = validateHeader(header);

      if (!validHeader) {
        System.out.println("ERROR: Invalid input file. Incorrect header.");
      } else {
        List<String[]> starData = parsedData.subList(1, parsedData.size());

        for (String[] datum : starData) {
          Star star = createStar(datum);
          starsList.add(star);
          nameToStar.put(star.getName(), star);
        }
        starsTree = new KDTree<>(3, starsList);
        System.out.println("Read " + starsList.size() + " stars from " + filePath);
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
    return header[0].equals("StarID")
        && header[1].equals("ProperName")
        && header[2].equals("X")
        && header[3].equals("Y")
        && header[4].equals("Z");
  }

  /**
   * Create a new star.
   * @param datum Star information obtained from CSV file
   * @return Created star
   */
  public Star createStar(String[] datum) {
    String id = datum[0];
    String name = datum[1];
    double x = Double.parseDouble(datum[2]);
    double y = Double.parseDouble(datum[3]);
    double z = Double.parseDouble(datum[4]);

    return new Star(id, name, x, y, z);
  }
}
