package models;

/**
 * Stats record the game statistics to import/export from database.
 */
public class Stats {
  // Stats for current Game
  public int nGame = 1;
  public int nRounds;
  public int nTie = 0;
  public int[] winningRecord;

  // History statics
  public int sumNGame, sumNRound, sumNTie, sumNHumanWon, sumNAiWon;
  public int nLongestGame = 0;

  public Stats() {
    this.importFromDB();
  }

  public Stats(int nPlayers, int nTie, int turnId, int[] winningRecord) {
    this.winningRecord = winningRecord;
    for (int i = 0; i < nPlayers; i++) {
      if (i == 0) {
        this.sumNHumanWon = winningRecord[i];
      } else {
        this.sumNAiWon += winningRecord[i];
      }
    }
    this.nTie = nTie;
    this.nRounds = turnId;
  }

  public void importFromDB() {
    // TODO import stats from DB
    // update game history data, including sumNGame, sumNRound, sumNTie,
    // sumNHumanWon, sumAiWon, nLongestGame = 0;
  }

  public void export2DB() {
    // TODO export stats from DB
  }
}