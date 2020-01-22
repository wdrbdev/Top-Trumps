package model;

public abstract class Player {
  private int id;
  private String name;
  // True if the player is human, false of the player is computer/AI
  private boolean isHuman;
  private Deck deck;
  private int nCards;
  // current card on hand, which initialised as null.
  private Card currentCard = null;

  public Player(int id, String name, boolean isHuman, Deck deck) {
    // initialise Player
    // ...
  }

  /**
   * Draw one random Card as this.currentCard from deck by this.deck.dealCard()
   * and then DELETE the card from deck.
   * 
   */
  public abstract void draw();

  /**
   * Choose one Category from the current card of the user (this.currentCard)
   * 
   * @return a String, the category chosen
   */
  public abstract String chooseCategory();

  public Card getCurrentCard() {
    return this.currentCard;
  }

  /**
   * Randomly choose one category. Card.chosenCategory would be set to String of
   * the chosen category. Card.randomlyPlay() would call Card.play() to play this
   * card.
   * 
   * @return a Card, the card played.
   */
  public String randomlyChooseCategory() {
    String chosenCategory = null;
    // randomly choose one category and set to this.chosenCategory
    // ...
    return chosenCategory;
  }

  /**
   * Add cards into this player's deck and set Card.chosenCategory to null.
   * 
   * @param cards an array of Card, the card won or received by this player.
   */
  public abstract void addCards(Card[] cards);


}