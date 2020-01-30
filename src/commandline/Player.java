package commandline;

import java.util.ArrayList;

public class Player {
	private Deck deck;
	private boolean isHuman;
	private Card currentCard;
	private boolean isActive;
	
	
	public Player(boolean isHuman, Deck deck) {
		this.isHuman = isHuman;
		this.deck = deck;
		
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	

}
