package model;

import java.util.HashMap;

public abstract class Card {
  private String name;
  private HashMap<String, Integer> categories = new HashMap<>();
  private int nCategories;
  // The path of the corresponding card picture according to the card name
  private String cardPicPath;

  /**
   * <p>
   * Import name, categories, and values of category into a card. And then
   * calculate the number of categories.
   * </p>
   * 
   * <p>
   * Since abstract constructor is not allowed in Java, Constructor Card() will
   * call the abstract method importCard() to initialise Card instead.
   * </p>
   * 
   * @param cardName       a String, name
   * @param categoryNames  an array of String, names for each category
   * @param categoryValues an array of String, value for each category
   */
  public Card(String[] cardName, String[] categoryNames, int[] categoryValues) {
    this.importCard(cardName, categoryNames, categoryValues);
  };

  protected abstract void importCard(String[] cardName, String[] categoryNames, int[] categoryValues);

  /**
   * Get the value according to selected category.
   * 
   * @param category a String, the selected category
   * @return an integer, the value of the selected category
   */
  public int getCategoryValue(String category) {
    return this.categories.get(category);
  }



}