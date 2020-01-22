package model;

public abstract class Deck {
  private String dataSource;
  private int nCards;
  private Card[] cards;;

  /**
   * Import deck of cards from provided data source by calling importDB(),
   * importTXT(), or importCSV() and then export it to database for storage.
   */
  public Deck(String dataSource) {
    this.dataSource = dataSource;
    switch (this.dataSource) {
    case "database":
      this.importDB();
      break;
    case "txt":
      this.importTXT();
      break;
    case "csv":
      this.importCSV();
      break;
    }
    this.export2DB();
  }

  /**
   * Equally distribute communal deck into small deck containing nCards.
   * 
   * @param Deck a Deck, the couumal deck with all the cards
   * @param nCards an integer, number of this smaller deck
   * @return a Deck, a random deck with nCards cards inside.
   */
  public Deck(Deck communalDeck,int nCards){
    // ...
  }

  /**
   * Import cards from database.
   * 
   * @implSpec This should be static method.
   */
  public abstract void importDB();

  /**
   * Import cards from txt file.
   * 
   * @implSpec This should be static method.
   */
  public abstract void importTXT();

  /**
   * Import cards from csv file.
   * 
   * @implSpec This should be static method.
   */
  public abstract void importCSV();

  /**
   * Import an array of Card when player wins cards or the system manually add new
   * card to one player. Update the nCard in the end.
   * 
   * @param cards an array of Cards, new cards which is to be added to deck.
   */
  public abstract void importCards(Card[] cards);

  /**
   * Export Deck to database.
   * 
   * @implSpec This should be static method.
   */
  public abstract void export2DB();

  /**
   * Randomly choose one card from deck and DELETE that card.
   * 
   * @return a Card, the card drawn
   */
  public abstract Card drawOneCard();

}