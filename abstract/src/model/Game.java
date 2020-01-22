package model;

public abstract class Game {
  public int id;
  public Player[] players;
  public Deck communalDeck;
  public Player currentPlayer;
  public Player currentWinner = null;
  public String chosenCategory = null;
  public int turnId = 1;

  /**
   * Choose the next player according to turnId. Next Player = (turnId %
   * (Player[].length) - 1 == -1) ? Player[].length : (turnId % (Player[].length)
   * - 1)). And then update this.currentPlayer.
   * 
   * @return a Player, the next Player
   */
  public Player whosNext() {
    int next = (this.turnId % (this.players.length) - 1 == -1) ? this.players.length
        : (this.turnId % this.players.length - 1);
    return this.players[next];
  }

  /**
   * if-else statement to determine whether any Player win the Game
   * 
   * @return boolean, true if someone win the game.
   */
  public abstract boolean isGameOver();

  /**
   * Compare current card of all Player to determine who win this turn.
   * 
   * @param currentPlayer in this turn, which player get to choose the category.
   * @return a Player, the player who won this turn
   */
  public Player whoWin() {
    Player winner = null;

    // For each player, draw card by Player.draw()
    // ...

    // Wait until the current user (this.currentPlayer) choose one category
    String chosenCategory = currentPlayer.chooseCategory();
    // ...

    // logic to compare the value of this.players[i].getChosenValue()
    // for each player to determine winner.
    // ...

    return winner;
  }

  /**
   * Transfer card to a winner from losers.
   * 
   * @param winner a Player, the player who won this turn.
   */
  public void cardTransfer() {
    // Iterate the player to get current cards (Player.currentCard)
    Card[] cards;
    // ...

    // set Player.currentCard = null and chosenCategory = null
    // ...

    // add card to winner (this.currentWinner) by Player.addCard(cards)
    // ...

  }

  /**
   * Export and store the statistic (e.g. turnId) of this game play in database.
   */
  public abstract void exportStatistics();

  /**
   * Initialize human players and AI players and check if nHumanPlayer + nAiPlayer
   * <2 or # of human players > 1
   * 
   * @param nHumanPlayer an integer, # of human players
   * @param nAiPlayer    an integer, # of AI players
   * @return boolean, true if adding players successfully
   */
  public boolean setPlayer(int nHumanPlayer, int nAiPlayer) {
    if (nHumanPlayer + nAiPlayer < 2 || nHumanPlayer > 1)
      return false;
    // add Player by new Player into this.players
    // ...
    return true;

  }

  // Setters
  public abstract void setCurrentPlayer(Player currentPlayer);

  public abstract void setCurrentWinner(Player currentWinner);

}