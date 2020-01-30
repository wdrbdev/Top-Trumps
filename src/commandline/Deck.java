package commandline;

import java.util.ArrayList;
import java.util.LinkedList;

public class Deck {
	
	private LinkedList<Card> cards;
	
	public Deck (LinkedList<Card> cards) {
	
		this.cards = cards;
		
	}

	public LinkedList<Card> getCards() {
		return cards;
	}
	
	public void print() {
		for (int i = 0; i < cards.size(); i++) {
		System.out.print("\n");
		cards.get(i).print();
	}
}
}
