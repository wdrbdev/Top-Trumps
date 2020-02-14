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

  /**
   * Include model(Game) to initialize controller
   * 
   * @param game a Game object, the model in MVC pattern
   */
  public Controller(Game game) {
    this.game = game;
  }

  /**
   * Add view into controller
   * 
   * @param view a View object, the view in MVC pattern
   */
  public void addView(View view) {
    this.view = view;
  }

  /**
   * Scan input and validate it according to # of choices. The choices is valid
   * when it's between 0(included) amd nChoices(excluded). Print "invalid input"
   * and ask user to input again when the input is not within the range.
   * 
   * @param nChoices an int, # of choices shown in view
   * @return an int, the valid input of user
   */
  public int validateInput(int nChoices) {
    // Use scanner for input
    Scanner scanner = new Scanner(System.in);
    // the input value as valid integer
    int intInput = -1;
    // infinite loop which break when the input is validated
    boolean isInvalidInput = true;
    while (isInvalidInput) {
      // use nextLine() rather than nextLine(nextInt) to exclude exception raised
      String stringInput = scanner.nextLine();
      // if the input cannot be converted to int, then it's invalid
      try {
        intInput = Integer.parseInt(stringInput);
      } catch (NumberFormatException e) {
      }
      // When input can be converted to integer & input is within acceptable range
      if (intInput > 0 && intInput <= nChoices) {
        System.out.println();
        return intInput;
      }
      System.out.println("Invalid input. Please enter a number between 1 to " + Integer.toString(nChoices) + ".");
      System.out.print("Please enter your choice again: ");
    }
    return -1;
  }

  /**
   * Convert player object to JSON object. e.g. this will be called
   * players2JsonArray or game2Json.
   * 
   * @param player a Player object
   * @return a JSON object, the player of json object format
   */
  public static JSONObject player2Json(Player player) {
    JSONObject jsonPlayer = new JSONObject();

    // Add object attribute-value as key-value pair
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

  /**
   * Convert card object to JSON object and Convert categories as JSON array.
   * 
   * @param card a Card object, e.g. currentCard
   * @return a JSON object, the card of json object format
   */
  public static JSONObject card2Json(Card card) {
    JSONObject jsonCard = new JSONObject();

    // Add object attribute-value as key-value pair
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

  /**
   * Convert array of player object to JSON array
   * 
   * @param players
   * @return a JSON array, the game.players array of json object format
   */
  public static JSONArray players2JsonArray(ArrayList<Player> players) {
    JSONArray jsonPlayers = new JSONArray();

    // Add object attribute-value as key-value pair
    // Add all players into jsonPlayers, which is a JSON array, by foreach loop
    for (Player player : players) {
      jsonPlayers.put(Controller.player2Json(player));
    }
    return jsonPlayers;
  }

  /**
   * Convert stats object (the statistics of history games) to JSON object
   * 
   * @param game
   * @return a JSON object, the stats of json object format
   */
  public static String historyStatistics2Json(Game game) {
    JSONObject jsonHistoryStatistics = new JSONObject();
    game.stats = new Stats();

    // Add object attribute-value as key-value pair
    jsonHistoryStatistics.put("sumNGame", game.stats.sumNGame);
    jsonHistoryStatistics.put("sumNTie", game.stats.avgTie);
    jsonHistoryStatistics.put("sumNHumanWon", game.stats.sumNHumanWon);
    jsonHistoryStatistics.put("sumAiWon", game.stats.sumNAiWon);
    jsonHistoryStatistics.put("nLongestGame", game.stats.nLongestTurn);

    return jsonHistoryStatistics.toString();
  }

  /**
   * Convert the statistics of current game to JSON object. The statistics is the
   * attribute in game object. This do not involve Stats object.
   * 
   * @param game a Game object, the model in MVC pattern
   * @return a JSON object, the game of json object format
   */
  public static JSONObject gameStatistics2Json(Game game) {
    Stats stats = Game.game2Stats(game);
    JSONObject jsonGameStatistics = new JSONObject();

    // Add object attribute-value as key-value pair
    jsonGameStatistics.put("nRounds", stats.nTurns);
    jsonGameStatistics.put("nTie", stats.nTie);

    JSONArray jsonArray = new JSONArray();

    if (game.winningRecord != null) {
      for (int i : game.winningRecord) {
        jsonArray.put(i);
      }
    }
    jsonGameStatistics.put("winningRecord", jsonArray);

    return jsonGameStatistics;
  }

  /**
   * Convert game object to JSON object. Add all model into one JSON string which
   * would be pass to javascript controller.
   * 
   * @param game a Game object, the model in MVC pattern
   * @return a String, the String of JSON object by toString()
   */
  public static String game2Json(Game game) {
    JSONObject jsonGameStatistics = new JSONObject();

    // Add object attribute-value as key-value pair
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
    jsonGameStatistics.put("gameStatistics", Controller.gameStatistics2Json(game));

    // put value which could be null
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

    return jsonGameStatistics.toString();
  }


  /**
   * Set the category chosen according to the user input
   * 
   * @param choice an int, a valid input value
   */
  public void chooseCategory(int choice) {
    this.game.chosenCategory = this.game.getHumanCard().categories.get(choice - 1);
  }

  /**
   * Decide what to do next according to the use input before the game is start.
   * <ul>
   * <li>input = 1: print the history statistics and continue while loop.
   * <li>
   * <li>input = 2: start a game
   * <li>
   * <li>input = 3: quit from program. Break the while loop and set
   * userWantsToQuit as true.
   * <li>
   * </ul>
   * 
   * @param input
   * @return
   */
  public boolean chooseStart(int input) {
    switch (input) {
    case 1:
      this.view.printHistoryStats();
      return false;
    case 2:
      return true;
    case 3:
      this.userWantsToQuit = true;
      return true;
    }
    return false;
  }

  /**
   * Getter for userWantsToQuit
   * 
   * @return a boolean
   */
  public boolean getUserWantsToQuit() {
    return this.userWantsToQuit;
  }

}