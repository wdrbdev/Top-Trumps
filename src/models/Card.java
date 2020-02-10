package models;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * Card represent one top trumps card, including the card name, categories, and
 * the corresponding values.
 */
public class Card {
  public String cardName;
  /**
   * The arraylist of all the category names.
   */
  public ArrayList<String> categories;
  /**
   * The values of each category will be stored as key-value pair in a hashmap
   * called categoryValues. The key is the category name and the value is the
   * category value.
   */
  public HashMap<String, Integer> categoryValues = new HashMap<>();

  /**
   * Create a card by card name, an array of category names and an array of
   * values. When the start of the game, it is created by game.importCard().
   * 
   * @param cardName   a String, the card name.
   * @param categories a arraylist of String, as defined in game.importCard()
   * @param values     a arraylist of integer, as defined in game.importCard()
   */
  public Card(String cardName, ArrayList<String> categories, ArrayList<Integer> values) {
    this.cardName = cardName;
    this.categories = categories;
    for (int i = 0; i < categories.size(); i++) {
      this.categoryValues.put(categories.get(i), values.get(i));
    }
  }

  /**
   * Get the corresponding value of the input category for this card form the
   * hashmap called categoryValues.
   * 
   * @param category a String, the input category
   * @return an int, the value of the corresponding category.
   */
  public int getValue(String category) {
    return this.categoryValues.get(category);
  }

  /**
   * For debug only. Convert card object to JSON object and print the cardName and
   * categoryValuesas as a JSON String. For example, it can be called by
   * card.print(System.err).
   * 
   * @param ps a PrintStream object, such as System.err
   */
  public void print(PrintStream ps) {
    JSONObject jsonCard = new JSONObject();

    jsonCard.put("cardName", this.cardName);
    for (Map.Entry<String, Integer> entry : this.categoryValues.entrySet()) {
      jsonCard.put(entry.getKey(), entry.getValue());
    }

    ps.println(jsonCard.toString());
  }

}