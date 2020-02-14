package models;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 * The game class contains the rules and procedure of the game. The game class
 * is composed of Player, Card, and Stats. This is the core Model of the MVC
 * design.
 */
public class Game {
  public boolean isGameOver = false;
  /**
   * If user pass -t flag for command line, isTestLog vwill be set to true.
   */
  public boolean isTestLog = false;
  /**
   * Test Log object would be created only if -t flag presented.
   */
  public TestLog tLog;
  /**
   * # of players in the game. i.e. the size of players array (players.size())
   */
  public int nPlayers;
  /**
   * Record the turn is tie for the turn status and to check if the winner need to
   * take all cards from common pile.
   */
  public boolean isTie = false;
  /**
   * The common pile. If the turn is tie, all the current card goes to
   * communalDeck.
   */
  public LinkedList<Card> communalDeck = new LinkedList<Card>();
  /**
   * An array of Player, including all players no matter he lost or not.
   */
  public ArrayList<Player> players = new ArrayList<Player>();

  /**
   * The category chosen in this turn.
   */
  public String chosenCategory;
  /**
   * The winner of this turn. If tie, the winner will not be reassigned since if
   * it's tie, the winner of last turn will become the active player.
   */
  public Player currentWinner;
  /**
   * If not tie, the card(s) which win this turn will be added here. If tie, the
   * winningCard will be reassigned to the card with highest value.
   */
  public Card winningCard;
  /**
   * If tie, all cards with highest value will be added here.
   */
  public ArrayList<Card> tieCards = new ArrayList<Card>();
  /**
   * Check if human player lose or not.
   */
  public boolean isHumanOut = false;

  // Statistics
  /**
   * Stats record the statistics of the current game and history games.
   */
  public Stats stats;
  /**
   * The round number which will increment by 1 for next round.
   */
  public int turnId = 1;
  /**
   * Record how many turns are tie/draw for game statistics and history
   * statistics.
   */
  public int nTie = 0;
  /**
   * Record how many turns each player win. The index is the id of the players.
   */
  public int[] winningRecord;

  /**
   * Create player objects and put it in game.players according to input nPlayers.
   * The active player of the first turn will be chosen randomly.
   * 
   * @param nPlayers an int, the # of players
   */

  public void initPlayer(int nPlayers) {
    this.nPlayers = nPlayers;
    boolean isFirstPlayer = false;
    // Random generate one player id to be the first player
    int firstId = new Random().nextInt(this.nPlayers);

    for (int i = 0; i < this.nPlayers; i++) {
      // If the user id equals the random id, then the players will plays first.
      if (i == firstId) {
        isFirstPlayer = true;
      } else {
        isFirstPlayer = false;
      }

      // Create the players with id starting from 0. The id = 0 should be human
      // player, and AI players' id start at 1.
      if (i == 0) {
        Player humanPlayer = new Player(i, true, isFirstPlayer);
        this.players.add(i, humanPlayer);
      } else {
        this.players.add(i, new Player(i, false, isFirstPlayer));
      }

      // Assign active player as currentWinner
      for (Player player : this.players) {
        if (player.isWinner) {
          this.currentWinner = player;
        }
      }
    }

    // Initialize winning record according to # of playeys
    this.winningRecord = new int[nPlayers];
  }

  /**
   * Import card into common pile from txt file and shuffle it.
   */
  public void importCard() {
    FileReader cardFileReader = null;
    // Read the txt file with information of cards, including card name, categories,
    // and values, by FileReader. The txt file should be in the root file of the
    // project.
    try {
      cardFileReader = new FileReader("StarCitizenDeck.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    // Processing txt file line by line.
    Scanner textFileScanner = new Scanner(cardFileReader);
    String line = textFileScanner.nextLine();
    String[] token = line.split(" ");

    // The array for category names and values
    ArrayList<String> categories = new ArrayList<String>();
    ArrayList<Integer> values;
    Card card;

    int nCategories = token.length - 1;
    // Category names are in the first line
    for (int i = 0; i < nCategories; i++) {
      categories.add(i, token[i + 1]);
    }

    // One line is the information of one card. Go through all line and create card
    // for each line.
    while (textFileScanner.hasNextLine()) {
      line = textFileScanner.nextLine();
      token = line.split(" ");
      values = new ArrayList<Integer>();
      for (int i = 0; i < nCategories; i++) {
        values.add(i, Integer.parseInt(token[i + 1]));
      }
      card = new Card(token[0], categories, values);
      this.communalDeck.add(card);
    }

    if (isTestLog) {
      tLog.writeCommunalDeck();
    }

    // Shuffle the common pile before distributing cards to deck
    Collections.shuffle(this.communalDeck);

    if (isTestLog) {
      tLog.writeCommunalDeck();
    }

  }

  /**
   * Deal cards to all players from common pile. If # of cards in common pile
   * cannot be evenly deal to players, some player will get more cards than
   * others. e.g. if # of players is 3 and there are 40 cards in the common pile,
   * the cards for each players are 13, 13, 13.
   */
  public void distributeCards() {
    int nCards = this.communalDeck.size();
    int nExtraCards = this.communalDeck.size() % this.nPlayers;
    for (int i = 0; i < nCards - nExtraCards; i++) {
      int playerIndex = i % this.players.size();
      this.players.get(playerIndex).addCard(this.communalDeck.pop());
    }
    this.communalDeck.clear();
    this.updateNCards();
  }

  /**
   * Decide who win the turn according to the rule of top trump.
   * <ol>
   * <li>First, the highest value of the chosen category of each turn are
   * calculated.</li>
   * <li>Count how many players' card have the highest value calculated, if count
   * 1, then it's tie. If count =1, then there's a winner in this turn.</li>
   * <li>If only one player have the card with highest value on chosen category,
   * the player win this turn and get the card from other players' currentCard and
   * all cards in common pile.</li>
   * <li>If more than one player have the card with highest value on chosen
   * category, it's tie and all players' currentCard go to common pile.</li>
   * <li>After card transaction, the current card are removed. i.e. currentCard is
   * assigned as null.</li>
   * </ol>
   */
  public void whoWon() {

    if (isTestLog) {
      tLog.writeInPlayCards();
    }

    int nWinner = 0;
    int maxValue = Integer.MIN_VALUE;
    // Clear the tie cards
    this.tieCards.clear();

    // Calculate the highest value
    for (Player player : this.players) {
      // Exclude players who lose
      if (!player.isOut() && player.currentCard != null) {
        if (player.currentCard.getValue(this.chosenCategory) > maxValue) {
          maxValue = player.currentCard.getValue(this.chosenCategory);
        }
      }
    }

    // Count how many winner in this turn
    for (Player player : this.players) {
      // Exclude players who lose
      if (!player.isOut() && player.currentCard != null) {
        if (player.currentCard.getValue(this.chosenCategory) == maxValue) {
          nWinner++;
        }
      }
    }

    // If # of winners are larger than 1, it's a tie
    if (nWinner > 1) {
      this.isTie = true;
      this.tie();
      for (Player player : this.players) {
        // Exclude players who lose
        if (!player.isOut() && player.currentCard != null) {
          if (player.currentCard.getValue(this.chosenCategory) == maxValue) {
            // Update currentWinner and winning card
            // DO NOT update isWinner
            // this.currentWinner = player;
            this.winningCard = player.currentCard;
            this.tieCards.add(player.currentCard);
          }
        }
      }
    } else { // If # of winner is 1, than there's a winner for this turn
      for (Player player : this.players) {
        // Exclude players who lose
        if (!player.isOut() || player.currentCard != null) {
          // Player who have card with highest value is winner
          if (player.currentCard.getValue(this.chosenCategory) == maxValue) {
            // Update isWinner, currentWinner, winning record
            player.isWinner = true;
            this.currentWinner = player;
            this.winningCard = player.currentCard;
            this.winningRecord[player.id]++;
          } else {
            // Player other than winner lose this turn
            player.isWinner = false;
          }
        }
      }
      // Transfer card to winner and when isTie=true, transfer cards in common pile to
      // winner as well.
      this.win(this.isTie);

      // Update isTie when there's a winner in this turn after card transaction
      this.isTie = false;
    }
    // Remove the currentCard nd update nCards of each player
    this.removeCurrentCard();
    this.updateNCards();

  }

  /**
   * When there's a winner, transfer cards to the winner from losers. If the
   * previous game is tie, then transfer the cards of common piles as well.
   * 
   * @param isTie a boolean, true if the last turn is tie
   */
  public void win(boolean isTie) {
    Player winner = null;
    // Find winner
    for (Player player : this.players) {
      if (player.isWinner)
        winner = player;
    }
    // Transfer cards from losers to winner
    for (Player player : this.players) {
      // Exclude players who lose
      if (!player.isOut() || player.currentCard != null) {
        winner.addCard(player.currentCard);
      }
    }
    // If last turn is tie, transfer the card of common pile too
    if (isTie) {
      winner.addCard(this.communalDeck);
      // Remember to clear the common pile!
      this.communalDeck.clear();

    }

    if (isTestLog) {
      tLog.writeCommunalDeck();
    }
  }

  /**
   * If it's tie, transfer players' currentCard into common pile and update # of
   * tie.
   */
  public void tie() {
    this.nTie++;
    for (Player player : this.players) {
      // Exclude players who lose
      if (!player.isOut() || player.currentCard != null) {
        this.communalDeck.add(player.currentCard);
      }
    }
    if (isTestLog) {
      tLog.writeCommunalDeck();
    }
  }

  /**
   * Only update the game statistics but not export to DB
   * 
   * @param game a Game object, the current game object
   * @return a Stats object
   */
  public static Stats game2Stats(Game game) {
    Stats stats = new Stats(game.nPlayers, game.nTie, game.turnId, game.currentWinner.isHuman);
    return stats;
  }

  /**
   * If a player have 0 card in deck, then the player lose. And if there's only
   * one player who have more than 0 card, the player win and the game is over.
   */
  public void checkIsGameOver() {
    int nLosers = 0;
    // Count how many player lost
    for (Player player : this.players) {
      if (player.deck.isEmpty()) {
        nLosers++;
        if (player.isHuman) {
          this.isHumanOut = true;
        }
      }
    }

    // If only one player not lose, then the game is over.
    if (nLosers >= this.players.size() - 1) {
      this.isGameOver = true;
    }
  }

  /**
   * Methods would be called when start a game.
   * 
   * @param nPlayers an int, the # of players to be Initialized
   */
  public void startGame(int nPlayers) {
    // Initialize player objects
    this.initPlayer(nPlayers);
    // Import card data from txt to common pile
    this.importCard();

    // Deal cards from common pile to players
    this.distributeCards();

    if (isTestLog) {
      tLog.writeAllDecks();
    }

    // Draw one card as currentCard from players' deck
    this.draw();
    // Initialize current wining card and current winner for the first player
    this.updateCurrentWinningCard();
  }

  /**
   * Methods would be called when end of a turn.
   */
  public void endTurn() {
    // Check if the game is over
    this.checkIsGameOver();

    // Start next turn
    if (!this.isGameOver) {
      this.turnId++;
    }
    // All players draw one card from their deck
    this.draw();
  }

  /**
   * All player draw one card form their own deck.
   */
  public void draw() {
    for (Player player : this.players) {
      // Exclude players who lost
      if (!player.isOut()) {
        player.draw();
      }
    }
  }

  /**
   * Update the current winner and current winning card according to
   * player.isWinner. If player.isWinner is true, then the player is the winner,
   * and the player's card is current winning card.
   */
  public void updateCurrentWinningCard() {
    for (Player player : this.players) {
      if (player.isWinner) {
        this.currentWinner = player;
        this.winningCard = player.currentCard;
      }
    }
  }

  /**
   * Update nCards for all players
   */
  public void updateNCards() {
    // System.out.println("===ncard===");
    for (Player player : this.players) {
      player.nCards = player.deck.size();
      // System.out.println(player.nCards);
    }
    // System.out.println("===ncard===");
  }

  /**
   * Set current card as null when the turn ends.
   */
  public void removeCurrentCard() {
    // For debug
    // this.printCards();

    for (Player player : this.players) {
      player.removeCurrentCard();
    }
  }

  /**
   * Getter for human player according to player.isHuman
   * 
   * @return a Player object, the human player if it exist
   */
  public Player getHumanPlayer() {
    if (this.players.get(0).isHuman) {
      return this.players.get(0);
    }
    return null;
  }

  /**
   * Getter for currentCard of human player according to player.isHuman
   * 
   * @return a Card object, the human player if it has currentCard
   */
  public Card getHumanCard() {
    if (this.getHumanPlayer() != null) {
      return this.getHumanPlayer().currentCard;
    }
    return null;
  }

  /**
   * Getter for # of tie cards
   * 
   * @return an int
   */
  public int getNTieCards() {
    return this.tieCards.size();
  }

  /**
   * Getters for all players of the game
   * 
   * @return an arraylist of players
   */
  public ArrayList<Player> getPlayers() {
    return this.players;
  }

  /**
   * Getters for the player with isWinner=true, which is the winner of last turn
   * or the next player
   * 
   * @return a Player object, the winner
   */
  public Player getWinner() {
    for (Player player : this.players) {
      if (player.isWinner)
        return player;
    }
    return null;
  }

  /**
   * Getter for currentWinner's currentCard
   * 
   * @return a Card object
   */
  public Card getWinningCard() {
    return this.winningCard;
  }

  /**
   * Getter for statistics
   * 
   * @return a Stats object
   */
  public Stats getStatcs() {
    return this.stats;
  }

  /**
   * Getter for the category chosen for this turn
   * 
   * @return a String
   */
  public String getChosenCategory() {
    return this.chosenCategory;
  }

  /**
   * The # of turn which each player won. The index is the player id.
   * 
   * @return an array of int
   */
  public int[] getWinningRecord() {
    return this.winningRecord;
  }

  /**
   * Getter for turnId
   * 
   * @return an int
   */
  public int getTurnId() {
    return this.turnId;
  }

  /**
   * Getter for isTie
   * 
   * @return an boolean
   */
  public boolean getIsTie() {
    return this.isTie;
  }

  /**
   * Getter for how many card in common pile
   * 
   * @return an int, the # of cards in common pile
   */
  public int getNCard() {
    return this.communalDeck.size();
  }

  /**
   * Setter for chosen category
   * 
   * @param chosenCategory a String
   */
  public void setChosenCategory(String chosenCategory) {
    this.chosenCategory = chosenCategory;
  }

  /**
   * For debug only, print currentCard of all players.
   */
  public void printCards() {
    for (Player player : this.players) {
      // Exclude players who lose
      if (!player.isOut() && player.currentCard != null) {
        System.out.print("ID: " + player.id + " -> ");
        player.currentCard.print(System.err);
      }
    }
  }

  public void chooseByAi() {
    // AI players will choose the max value
    // this.chosenCategory = this.currentWinner.chooseMaxCategory();

    // AI players will choose the value randomly
    this.chosenCategory = this.currentWinner.chooseRandomCategory();
  }

  /**
   * Start recording the test status to log file. isTestLog will be set to true
   * when -t flag present.
   * 
   * @param isTestLog
   */
  public void activateTestLog(boolean isTestLog) {
    this.isTestLog = isTestLog;
    if (isTestLog) {
      this.tLog = new TestLog(this);
    }
  }

}