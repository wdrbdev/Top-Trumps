package model;

public abstract class Model {
  private String historyStatistics;
  private Game game;
  private Deck communalDeck;

  public Model() {
    this.historyStatistics = this.importStatistics();

    /**
     * After receiving start signal, setup players -> import communal Deck from
     * source -> start the game.
     */
    String source = null;
    this.communalDeck = new Deck(source);

    /**
     * Initialise and start playing the game.
     * <ol>
     * <li>Import players and communal deck.</li>
     * <li>Give each player a random deck from communal Deck.</li>
     * <li>Start the game play.</li>
     * <li>Store current game statistics into database.</li>
     * </ol>
     * 
     * @param players      a array of players, players who join the game
     * @param communalDeck deck imported directly from database or file
     */
    this.game = new Game();

    // add players to this.players
    int nHumanPlayer, nAiPlayer;
    this.game.setPlayer(nHumanPlayer, nAiPlayer);
    // ...

    // Import communal Deck and distribute equally to all players
    // ...

    // Demo game play: A infinite loop to play the Game until the someone win the
    // game.
    while (this.game.isGameOver()) {
      this.game.setCurrentPlayer(this.game.whosNext());
      this.game.setCurrentWinner(this.game.whoWin());
      this.game.cardTransfer();
      this.game.turnId++;
    }
    this.game.exportStatistics();

    // Update and show new statistics
    this.historyStatistics = this.importStatistics();

  }

  /**
   * Connect to database through account/password or socket
   */
  public abstract void connectDB();

  /**
   * Import the statistics (the history record of games) from database. Each game
   * statistics is serialized into one String.
   * 
   * @return an array of String, the statistics of every game.
   */
  public abstract String importStatistics();

}