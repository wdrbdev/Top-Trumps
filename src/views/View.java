package views;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import controllers.Controller;
import models.Card;
import models.Game;
import models.Player;
import models.Stats;

public class View {
  public Game game;
  public Controller controller;

  /**
   * Include model and controller in view.
   * 
   * @param game       a Game object, the model in MVC pattern
   * @param controller a Game object, the model in MVC pattern
   */
  public View(Game game, Controller controller) {
    this.game = game;
    this.controller = controller;
  }

  /**
   * Print history stats from Stats, which import data from database.
   */
  public void printHistoryStats() {
    // Create Stats object to import data from database
    this.game.stats = new Stats();

    // Subtract history data from Stats
    System.out.println("----------");
    System.out.println("Total Number of Games So Far = " + Integer.toString(this.game.stats.sumNGame));
    System.out.println("Total Number of Games Human Won = " + Integer.toString(this.game.stats.sumNHumanWon));
    System.out.println("Total Number of Games Computer Won = " + Integer.toString(this.game.stats.sumNAiWon));
    System.out.println("Average Draw Round = " + Integer.toString((this.game.stats.avgTie)));
    System.out.println(
        "Maximum Number of Rounds (Longest Game) in a Game = " + Integer.toString(this.game.stats.nLongestTurn));
    System.out.println("----------");
  }

  /**
   * Print overall winner and the statistics of current game, which do not involve
   * the Stats object and database.
   */
  public void printGameStats() {
    // Print the overall winner from currentWinner
    System.out.print("The overall winner was ");
    Player winner = this.game.getWinner();
    if (winner.isHuman) {
      System.out.println("you.");
    } else {
      System.out.println("AI player " + winner.id);
    }

    // Print # of turns the player won
    System.out.println("Scores:");
    int[] winningRecord = this.game.winningRecord;
    for (int i = 0; i < winningRecord.length; i++) {
      if (i == 0) {
        System.out.println("You: " + winningRecord[i]);
      } else {
        System.out.println("AI player " + i + ": " + winningRecord[i]);
      }
    }
  }

  /**
   * Print how many card the human player have if the human player has not lost.
   */
  public void printNCards() {
    if (this.game.getHumanPlayer() != null) {
      System.out.println("There are " + this.game.getHumanPlayer().nCards + " cards in your deck");
    }
  }

  /**
   * Print the top of each round with horizontal line and show what round it is
   */
  public void printRound() {
    System.out.println("\n----------");
    System.out.println("Round " + this.game.turnId);
    System.out.print("Round " + this.game.turnId + ": ");
  }

  /**
   * Print the current card drawn by human player
   */
  public void printDrawnCard() {
    Card winingCard = this.game.getHumanCard();
    System.out.println("You have drawn \'" + winingCard.cardName + "\':");
    for (String category : winingCard.categories) {
      System.out.println("> " + category + ": " + winingCard.getValue(category));
    }
  }

  /**
   * Print the index and categories of the human's current card as an ordered list
   * format to show choices of user input. And then ask user to choose a category
   * by input integer.
   */
  public void printChoices() {
    // Print the categories of human's current card
    System.out.println("It is your turn to select a category, the categories are:");
    Card humanCard = this.game.getHumanCard();
    System.out.println("You drew \'" + humanCard.cardName + "\':");
    for (int i = 0; i < humanCard.categories.size(); i++) {
      System.out.println(i + 1 + ". " + humanCard.categories.get(i));
    }

    // Ask user for input to choose a category
    System.out.print("Enter the number for your attribute: ");
    int input = this.controller.validateInput(humanCard.categories.size());
    this.controller.chooseCategory(input);

  }

  /**
   * Before clean the screen, let user see the current game information and then
   * the user can input anything to continue.
   */
  public void click2Continue() {
    System.out.print("Press ENTER to continue.");
    Scanner scanner = new Scanner(System.in);
    scanner.nextLine();
  }

  /**
   * Print the winner of the turn and the winning cards only when there's a
   * winner. When draw, use printTieCards() instead.
   */
  public void printWinner() {
    Player winner = this.game.currentWinner;
    if (this.game.getIsTie()) {
      System.out.println("This round was a Draw, common pile now has " + this.game.getNCard() + " cards");
    } else {
      if (winner.isHuman) {
        System.out.println("You won this round");
      } else {
        System.out.println("AI Player " + winner.id + " won this round");
      }
    }

    // Print the winning card with arrow indicating the winning category/value.
    Card winingCard = this.game.winningCard;
    System.out.println("The winning card is \'" + winingCard.cardName + "\':");
    for (String category : winingCard.categories) {
      System.out.print("> " + category + ": " + winingCard.getValue(category));
      if (category.equals(this.game.chosenCategory))
        System.out.print(" <--");
      System.out.println();
    }

  }

  /**
   * Print the draw information and the cards with highest value only when it's
   * draw. When there's a winner, use printWinner() instead.
   */
  public void printTieCards() {
    // Add the card name into a set to avoid print the same card twice
    Set<String> tieCardNames = new HashSet<String>();
    System.out.println("This round was a Draw, common pile now has " + this.game.getNCard() + " cards");

    // Iterate through the array of all the tie card
    for (Card tieCard : this.game.tieCards) {
      // only print when the name didn't print before, exclude cards with the same
      // name from printing twice
      if (!tieCardNames.contains(tieCard.cardName)) {
        tieCardNames.add(tieCard.cardName);

        System.out.println("One of the winning card is \'" + tieCard.cardName + "\':");
        for (String category : tieCard.categories) {
          System.out.print("> " + category + ": " + tieCard.getValue(category));
          if (category.equals(this.game.chosenCategory))
            System.out.print(" <--");
          System.out.println();
        }
      }
    }
  }

  /**
   * Print the active player of the current turn.
   */
  public void printActivePlayer() {
    Player winner = this.game.currentWinner;
    System.out.print("The active player is ");
    if (winner.isHuman) {
      System.out.println("you");
    } else {
      System.out.println("AI Player " + winner.id);
    }
  }

  /**
   * Print the game start screen. The options include:
   * <ol>
   * <li>option 1: show game statistics</li>
   * <li>option 2: start a new game and play it</li>
   * <li>option 3: exit the program</li>
   * </ol>
   * The infinite loop of start screen will continue until the game start.
   */
  public void printStart() {
    while (true) {
      System.out.println("----------");
      System.out.println("Do you want to see past results or play a game?");
      System.out.println("1: Print Game Statistics");
      System.out.println("2: Play game");
      System.out.println("3: Exit game");
      System.out.print("Enter the number for your selection: ");
      int input = this.controller.validateInput(3);
      if (this.controller.chooseStart(input)) {
        return;
      }
    }
  }

  /**
   * Print the player id and the current card of the player. The card information
   * includes card name, category name, and values.
   */
  public void printCards() {
    for (Player player : this.game.players) {
      if (player.isOut() || player.currentCard != null) {
        System.out.print("ID: " + player.id + " -> ");
        player.currentCard.print(System.err);
      }
    }
  }

}