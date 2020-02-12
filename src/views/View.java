package views;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import controllers.Controller;
import models.Card;
import models.Game;
import models.Player;

public class View {
  public Game game;
  public Controller controller;

  public View(Game game, Controller controller) {
    this.game = game;
    this.controller = controller;
  }

  public void printHistoryStats() {
    System.out.println("TODO: Stats will be shown here");
  }

  public void printGameStats() {
    System.out.print("The overall winner was ");
    Player winner = this.game.getWinner();
    if (winner.isHuman) {
      System.out.println("you.");
    } else {
      System.out.println("AI player " + winner.id);
    }
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

  public void printNCards() {
    System.out.println("There are " + this.game.getHumanPlayer().nCards + " cards in your deck");
  }

  public void printRound() {
    System.out.println("\n----------");
    System.out.println("Round " + this.game.turnId);
    System.out.print("Round " + this.game.turnId + ": ");
  }

  public void printDrawnCard() {
    Card winingCard = this.game.getHumanCard();
    System.out.println("You have drawn \'" + winingCard.cardName + "\':");
    for (String category : winingCard.categories) {
      System.out.println("> " + category + ": " + winingCard.getValue(category));
    }
  }

  public void printChoices() {
    System.out.println("It is your turn to select a category, the categories are:");
    Card humanCard = this.game.getHumanCard();
    System.out.println("You drew \'" + humanCard.cardName + "\':");
    for (int i = 0; i < humanCard.categories.size(); i++) {
      System.out.println(i + 1 + ". " + humanCard.categories.get(i));
    }

    System.out.print("Enter the number for your attribute: ");
    int input = this.controller.validateInput(humanCard.categories.size());
    this.controller.chooseCategory(input);

  }

  public void click2Continue() {
    System.out.print("Press ENTER to continue.");
    Scanner scanner = new Scanner(System.in);
    scanner.nextLine();
  }

  public void printWinner() {
    Player winner = this.game.currentWinner;
    if (this.game.getIsTie()) {
      System.out.println("This round was a Draw, common pile now has " + this.game.getNCard() + " cards");
    } else {
      // System.out.println("winner id: " + winner.id + " & " + winner.isHuman);
      if (winner.isHuman) {
        System.out.println("You won this round");
      } else {
        System.out.println("AI Player " + winner.id + " won this round");
      }
    }

    Card winingCard = this.game.winningCard;
    System.out.println("The winning card is \'" + winingCard.cardName + "\':");
    for (String category : winingCard.categories) {
      System.out.print("> " + category + ": " + winingCard.getValue(category));
      if (category.equals(this.game.chosenCategory))
        System.out.print(" <--");
      System.out.println();
    }

  }

  public void printTieCards() {
    Set<String> tieCardNames = new HashSet<String>();

    System.out.println("This round was a Draw, common pile now has " + this.game.getNCard() + " cards");
    for (Card tieCard : this.game.tieCards) {
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

  public void printStart() {
    while (true) {
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

  public void printCards() {
    for (Player player : this.game.players) {
      if (player.isOut() || player.currentCard != null) {
        System.out.print("ID: " + player.id + " -> ");
        player.currentCard.print(System.err);
      }
    }
  }
  
 
  
  
	  

  
 
   

}