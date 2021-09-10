package edu.brown.cs.student.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Interface representing valid commands.
 */
public interface Commands {

  /**
   * Execute the command.
   *
   * @param command User input
   */
  void execute(String command);

  /**
   * Create a List of command arguments.
   *
   * @param command User input
   * @return List of command arguments
   */
  static List<String> getCommandArguments(String command) {
    // Ensure that phrases within quotes do not get split.
    List<String> commandArgs = new ArrayList<>();
    Pattern regexPattern = Pattern.compile("[^\\s\"']+|\"[^\"]*\"|'[^']*'");
    Matcher regexMatcher = regexPattern.matcher(command);

    while (regexMatcher.find()) {
      commandArgs.add(regexMatcher.group());
    }
    return commandArgs;
  }
}
