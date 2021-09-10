package edu.brown.cs.student.maps;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Class that establishes a connection to the database.
 */
public class Database {

  private static Connection conn;
  private final String databasePath;

  /**
   * Constructor.
   *
   * @param databasePathIn Database path
   */
  public Database(String databasePathIn) {
    databasePath = databasePathIn;
  }

  /**
   * Getter.
   *
   * @return Connection to the database
   */
  public Connection getConn() {
    return conn;
  }

  /**
   * Establish a connection to the database.
   *
   * @throws Exception SQL file not found.
   */
  public void setDatabase() throws Exception {
    File file = new File(databasePath);
    if (!file.exists()) {
      throw new Exception("ERROR: File not found.");
    } else {
      Class.forName("org.sqlite.JDBC");
      String urlToDB = "jdbc:sqlite:" + databasePath;
      conn = DriverManager.getConnection(urlToDB);

      Statement stat = conn.createStatement();
      stat.executeUpdate("PRAGMA foreign_keys=ON;");
    }
  }
}
