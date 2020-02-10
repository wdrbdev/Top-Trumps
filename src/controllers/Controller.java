package controllers;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import models.Card;
import models.Game;
import models.Player;
import models.Stats;
import views.View;

public class Controller {
  public boolean userWantsToQuit = false;
  public Game game;
  public View view;

  public boolean toRestart = false;

  public Controller(Game game) {
    this.game = game;
  }

  public void addView(View view) {
    this.view = view;
  }

  public int validateInput(int nChoices) {
    Scanner scanner = new Scanner(System.in);
    boolean isInvalidInput = true;
    int intInput = -1;
    while (isInvalidInput) {
      String stringInput = scanner.nextLine();
      try {
        intInput = Integer.parseInt(stringInput);
      } catch (NumberFormatException e) {
        System.out.println("Invalid input.");
      }
      if (intInput > 0 && intInput <= nChoices) {
        System.out.println();
        return intInput;
      }
      System.out.print("Please enter your choice again: ");
    }
    return -1;
  }

  public Game getCliGame() {
    return this.game;
  }

  public static JSONObject player2Json(Player player) {
    JSONObject jsonPlayer = new JSONObject();
    jsonPlayer.put("id", player.id);
    jsonPlayer.put("nCards", player.getNCard());
    jsonPlayer.put("isWinner", player.isWinner);
    jsonPlayer.put("isHuman", player.isHuman);
    jsonPlayer.put("isOut", player.isOut());
    if (player.currentCard != null) {
      jsonPlayer.put("currentCard", Controller.card2Json(player.currentCard));
    } else {
      jsonPlayer.put("currentCard", JSONObject.NULL);
    }

    return jsonPlayer;
  }

  public static JSONObject card2Json(Card card) {
    JSONObject jsonCard = new JSONObject();

    jsonCard.put("cardName", card.cardName);
    jsonCard.put("nCategories", card.categories.size());
    for (Map.Entry<String, Integer> entry : card.categoryValues.entrySet()) {
      jsonCard.put(entry.getKey(), entry.getValue());
    }
    JSONArray categories = new JSONArray();
    for (String category : card.categories) {
      categories.put(category);
    }
    jsonCard.put("categories", categories);

    return jsonCard;
  }

  public static JSONArray players2JsonArray(ArrayList<Player> players) {
    JSONArray jsonPlayers = new JSONArray();
    // Add all players into jsonPlayers, which is a JSON array, by foreach loop
    for (Player player : players) {
      jsonPlayers.put(Controller.player2Json(player));
    }
    return jsonPlayers;
  }

  public static JSONObject historyStatistics2Json(Game game) {
    JSONObject jsonHistoryStatistics = new JSONObject();
    Stats stats = new Stats();

    jsonHistoryStatistics.put("sumNGame", stats.sumNGame);
    jsonHistoryStatistics.put("sumNRound", stats.sumNRound);
    jsonHistoryStatistics.put("sumNTie", stats.sumNTie);
    jsonHistoryStatistics.put("sumNHumanWon", stats.sumNHumanWon);
    jsonHistoryStatistics.put("sumAiWon", stats.sumNAiWon);
    jsonHistoryStatistics.put("nLongestGame", stats.nLongestGame);

    return jsonHistoryStatistics;
  }

  public static JSONObject gameStatistics2Json(Game game) {
    Stats stats = Game.game2Stats(game);

    JSONObject jsonGameStatistics = new JSONObject();
    jsonGameStatistics.put("nRounds", stats.nRounds);
    jsonGameStatistics.put("nTie", stats.nTie);

    JSONArray jsonArray = new JSONArray();
    for (int i : stats.winningRecord) {
      jsonArray.put(i);
    }
    jsonGameStatistics.put("winningRecord", jsonArray);

    return jsonGameStatistics;
  }

  public static String game2Json(Game game) {
    JSONObject jsonGameStatistics = new JSONObject();

    jsonGameStatistics.put("isGameOver", game.isGameOver);
    jsonGameStatistics.put("nPlayers", game.nPlayers);
    jsonGameStatistics.put("isTie", game.isTie);
    jsonGameStatistics.put("nTie", game.nTie);
    jsonGameStatistics.put("chosenCategory", game.chosenCategory);
    jsonGameStatistics.put("isHumanOut", game.isHumanOut);
    jsonGameStatistics.put("players", Controller.players2JsonArray(game.players));
    jsonGameStatistics.put("turnId", game.turnId);
    jsonGameStatistics.put("nCards", game.getNCard());
    jsonGameStatistics.put("chosenCategory", game.chosenCategory);

    // put object which could be null

    if (game.winningCard != null) {
      jsonGameStatistics.put("winningCard", Controller.card2Json(game.winningCard));
    } else {
      jsonGameStatistics.put("currentWinner", JSONObject.NULL);
    }
    if (game.currentWinner != null) {
      jsonGameStatistics.put("currentWinner", Controller.player2Json(game.currentWinner));
    } else {
      jsonGameStatistics.put("currentWinner", JSONObject.NULL);
    }
    if (game.currentWinner != null) {
      jsonGameStatistics.put("humanPlayer", Controller.player2Json(game.getHumanPlayer()));
    } else {
      jsonGameStatistics.put("humanPlayer", JSONObject.NULL);
    }
    if (game.getHumanCard() != null) {
      jsonGameStatistics.put("humanCard", Controller.card2Json(game.getHumanCard()));
    } else {
      jsonGameStatistics.put("humanCard", JSONObject.NULL);
    }

    jsonGameStatistics.put("gameStatistics", Controller.gameStatistics2Json(game));
    jsonGameStatistics.put("historyStatistics", Controller.historyStatistics2Json(game));

    return jsonGameStatistics.toString();
  }

  public void chooseCategory(int choice) {
    this.game.chosenCategory = this.game.getHumanCard().categories.get(choice - 1);
  }

  public void control() {
    // TODO decide to show human or AI cards or show stats
    // Case 1: someone won the game
    // Case 1.1: human player won
    // Case 1.2: AI player won
    // Case 2: no one won
    // Case 2.1: human player's turn
    // Case 2.2: AI player's turn
  }

  public boolean quit() {
    // TODO update userWantsToQuit in app.java
    return true;
  }

  /**
   * Getters for view
   */

  public boolean chooseToRestart(int choice) {
    switch (choice) {
    case 1:
      this.toRestart = false;
      break;
    case 2:
      this.toRestart = true;
      break;
    }
    return this.toRestart;
  }

  public boolean chooseStart(int input) {
    switch (input) {
    case 1:
      this.view.printHistoryStats();
      return false;
    case 2:
      System.out.println("dd");
      return true;
    case 3:
      this.userWantsToQuit = true;
      return true;
    }
    return false;
  }

  public boolean getUserWantsToQuit() {
    return this.userWantsToQuit;
  }

}