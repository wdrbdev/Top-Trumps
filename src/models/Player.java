package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

/**
 * The player object represent human or ai player, deal with adding/removing
 * cards from deck and choosing category. This will be created by
 * game.initPlayer(int nPlayers).
 */
public class Player {
  /**
   * The player id for identification, human should be id=0.
   */
  public int id;
  /**
   * Is the player human or AI. True if the player is human, and false if the
   * player is AI player.
   */
  public boolean isHuman;
  /**
   * The player wins the previous turn or not. This will be set when initialize
   * player object to decide who plays first. When the player plays first, the
   * isWinner will be initialized as true. Also, when the player win the turn,
   * this value will be assign true and this value of other player will be
   * assigned false. If tie, then this value will not be assign new value to
   * decide the next player is the previous winner.
   */
  public boolean isWinner = false;
  /**
   * The current card are the card for the current turn. When start of the game
   * and the turn is over,this will be assign by player.draw(), which pops one
   * card from deck and assigns it to currentCard. This card is dependent from
   * deck.
   */
  public Card currentCard;
  /**
   * All the card that the player have, excluding the currentCard. When the start
   * of the game, it's be assigned by game.distributeCards().
   */
  public LinkedList<Card> deck = new LinkedList<Card>();
  /**
   * How many cards the user have. Could be calculated either by deck.size() if
   * excluding currentCard, or deck.size() if including currentCard.
   */
  public int nCards;

  /**
   * Create a player by player id, is human or AI, is the first player.
   * 
   * @param id            an int, the player id for identification, human should
   *                      be id=0.
   * @param isHuman       a boolean, true if the player is human, false if the
   *                      player is AI player
   * @param isFirstPlayer a boolean, if the player plays first, then
   *                      isFirstPlayer=true. There should only be one player that
   *                      this is true, if more than one player are true, the
   *                      lowest id value will be the first player.
   */
  public Player(int id, boolean isHuman, boolean isFirstPlayer) {
    this.id = id;
    this.isHuman = isHuman;
    this.isWinner = isFirstPlayer;
  }

  /**
   * Pop one card from deck into as the current card of the turn. i.e. Draw one
   * card from deck to currentCard. Then update nCard by # of Card in deck and the
   * current card.
   */
  public void draw() {
    this.currentCard = deck.pop();
    this.nCards = this.deck.size() + 1;
  };

  /**
   * Add multiple cards into deck. When tie, iterate all cards in common pile and
   * add each one into deck.
   * 
   * @param cards a linked list of Card, the cards to be added into deck.
   */
  public void addCard(LinkedList<Card> cards) {
    for (Card card : cards) {
      this.deck.add(card);
    }
    Collections.shuffle(this.deck);
  }

  /**
   * Add one card into deck. When winning the turn, iterate all player and add the
   * currentCard of losers into winner's deck.
   * 
   * @param card a Card object, the card to be added into deck.
   */
  public void addCard(Card card) {
    this.deck.add(card);
    Collections.shuffle(this.deck);
  }

  /**
   * Choose highest value of the currentCard and return that category. By
   * iterating all the values in categoryValues, if the value is larger than
   * maxValue, the values is assign as new maxValue. This is called when AI player
   * is the current active player.
   * 
   * @return a String, the category name with the max value
   */
  public String chooseMaxCategory() {
    int maxValue = Integer.MIN_VALUE;
    String maxCategory = null;

    // excluding the user with no currentCard
    if (this.currentCard != null) {
      // Iterate all keys and values to find largest value in the currentCard
      for (Map.Entry<String, Integer> entry : this.currentCard.categoryValues.entrySet()) {
        if (entry.getValue() > maxValue) {
          maxValue = entry.getValue();
          maxCategory = entry.getKey();
        }
      }
    }
    return maxCategory;
  }

  /**
   * Randomly choose one category of currentCard. This is called when AI player is
   * the current active player.
   * 
   * @return a String, the category chosen
   */
  public String chooseRandomCategory() {
    ArrayList<String> categories = this.currentCard.categories;
    return categories.get(new Random().nextInt(categories.size()));
  }

  /**
   * Check if the player lost or not. If the deck is empty, the player loses.
   * 
   * @return a boolean, true if the player lost.
   */
  public boolean isOut() {
    if (this.deck.isEmpty()) {
      this.isWinner = false;
      return true;
    }
    return false;
  }

  /**
   * Set currentCard as null
   */
  public void removeCurrentCard() {
    this.currentCard = null;
  }

  /**
   * Getter for nCards. Return # of card the player have according to the size of
   * the deck, including currentCard. If no currentCard, then just return the
   * cards in deck.
   * 
   * @return an int, # of cards the player have
   */
  public int getNCard() {
    if (this.currentCard != null) {
      return this.deck.size() + 1;
    }
    return this.deck.size();
  }

  /**
   * Setter for nCards. Update nCards by the size of the deck, excluding
   * currentCard.
   */
  public void updateNCards() {
    this.nCards = this.deck.size();
  }
}
