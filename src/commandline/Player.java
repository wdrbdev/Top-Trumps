package commandline;

import java.util.ArrayList;

public class Player {
	private ArrayList<Card> eachPlayersCards;
	private boolean isActive;
	
	
	private boolean isWinner; // For Albert
	private Card currentCard; // For Albert

	public Card getCurrentCard() {
		return currentCard;
	}

	public void setCurrentCard(Card currentCard) {
		this.currentCard = currentCard;
	}

	public boolean isWinner() {
		return isWinner;
	}

	public void setWinner(boolean isWinner) {
		this.isWinner = isWinner;
	}

	public Player() {
		eachPlayersCards = new ArrayList<Card>();
	}

	public boolean isActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void addEachPlayersCards(Card card) {
		eachPlayersCards.add(card);
	}

	public ArrayList<Card> getDeck() {
		return eachPlayersCards;
	}

	public String toString() {
		return this.getDeck().get(0) + "";
	}

}
