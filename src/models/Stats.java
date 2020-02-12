package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Stats record the game statistics to import/export from database.
 */
public class Stats {
  // Stats for current Game
  public int nTie;
  public int nTurns;
  public int[] winningRecord;

  // History statics
  public int sumNGame, sumNHumanWon, sumNAiWon, avgTie, nLongestTurn;

  public Connection connection;

  public Stats() {
    // this.clearTable();
    this.importFromDB();
  }

  public Stats(int nPlayers, int nTie, int turnId, boolean isHumanWon) {
    if (isHumanWon) {
      this.sumNHumanWon = 1;
    } else {
      this.sumNAiWon = 1;
    }
    this.nTie = nTie;
    this.nTurns = turnId;
  }

  public void connect() {
    try {
      Class.forName("org.postgresql.Driver");
      /* need to change host localhos to yacha to be tested on lab machines */
      this.connection = DriverManager.getConnection("jdbc:postgresql://52.24.215.108/SuperClasses", "SuperClasses",
          "SuperClasses"); // connecting
      // System.out.println("Connected!"); /* needs to be delted for final project */
    } catch (ClassNotFoundException | SQLException e) {
      System.out.println(e + "");
    }
  }

  public void disconnect() {
    try {
      this.connection.close();
      // System.out.println("Disconnected");
    } catch (SQLException e) {
      System.out.println(e + "");
    }
  }

  public void createTable() {
    String query = "CREATE TABLE IF NOT EXISTS toptrumps (nHumanWon int, nAiWon int, nTie int, nTurns int not null)";
    Statement stmt = null;

    try {
      stmt = this.connection.createStatement();
      stmt.executeUpdate(query);
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ":" + e.getMessage());
      System.exit(0);
    }
  }

  public void insertData() {
    String query = "Insert into toptrumps(nHumanWon, nAiWon, nTie ,nTurns) values (?,?,?,?)";
    this.connect();
    try {
      PreparedStatement st = this.connection.prepareStatement(query);
      /* To insert values to attributes of table */
      st.setInt(1, this.sumNHumanWon);
      st.setInt(2, this.sumNAiWon);
      st.setInt(3, this.nTie);
      st.setInt(4, this.nTurns);
      int stResult = st.executeUpdate();
      /*
       * if condition is only there to test insertion of values to table. Need to be
       * deleted for final project
       */
      if (stResult > 0) {
        System.out.println("Row inserted successfully");
      } else {
        System.out.println("Return value of st.executeUpdate() is " + Integer.toString(stResult));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    this.disconnect();
  }

  public void query(String query) {
    Statement stmt = null;
    this.connect();
    try {
      stmt = this.connection.createStatement();
      stmt.executeUpdate(query);
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ":" + e.getMessage());
      System.exit(0);
    }
    this.disconnect();
  }

  public void importFromDB() {
    this.connect();
    String[] queries = new String[] { "select count(nTurns) from toptrumps;", "select sum(nHumanWon) from toptrumps;",
        "select sum(nAiWon) from toptrumps;", "select round(avg(nTie)) from toptrumps;",
        "select max(nTurns) from toptrumps;" };
    String[] queryResult = new String[] { "Total Number of Games So Far = ", "Total Number of Games Human Won = ",
        "Total Number of Games Computer Won = ", "Average Draw Round = ",
        "Maximum Number of Rounds (Longest Game) in a Game = " };

    PreparedStatement ps = null;
    ResultSet rs = null;
    for (int i = 0; i < queries.length; i++) {
      try {
        /*
         * Count function will count the number of rows, which will give us number of
         * games played
         */
        ps = this.connection.prepareStatement(queries[i]);
        rs = ps.executeQuery();
        int data;
        if (rs.next()) {
          // System.out.println(queries[i]);
          data = rs.getInt(1);
          switch (i) {
          case 0:
            this.sumNGame = rs.getInt(1);
            break;
          case 1:
            this.sumNHumanWon = rs.getInt(1);
            break;
          case 2:
            this.sumNAiWon = rs.getInt(1);
            break;
          case 3:
            this.avgTie = rs.getInt(1);
            break;
          case 4:
            this.nLongestTurn = rs.getInt(1);
            break;
          }
          // System.out.println(queryResult[i] + Integer.toString(data));
        }
      } catch (SQLException e) {
        System.out.println(e.toString());
      }
    }
    this.disconnect();
  }

  public void clearTable() {
    String query = "DELETE FROM toptrumps;";
    Statement stmt = null;
    this.connect();
    try {
      stmt = this.connection.createStatement();
      stmt.executeUpdate(query);
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ":" + e.getMessage());
      System.exit(0);
    }
    this.disconnect();
  }

  public static void export2DB(Game game) {
    Stats stats = new Stats(game.nPlayers, game.nTie, game.turnId, game.currentWinner.isHuman);
    stats.connect();
    stats.createTable();
    stats.insertData();
    stats.disconnect();
  }

  public static void main(String[] args) {
    Stats importStats = new Stats();
    // importStats.clearTable();
    // importStats = new Stats();

    // Game game = new Game();
    // int nPlayers = 5;
    // game.initPlayer(nPlayers);
    // game.turnId = 92;
    // game.nTie = 23;
    // game.winningRecord = new int[] { 4, 78, 31, 23, 1 };
    // Stats.export2DB(game);

  }
}