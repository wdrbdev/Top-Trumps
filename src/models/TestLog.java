package models;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TestLog {
	public Game game;
	private Logger logger = Logger.getLogger(TestLog.class.getName());

	private static FileHandler fileHandler;

	public TestLog(Game game) {
		this.game = game;

		

			// This block configure the logger with handler and formatter
			
			addHandler();
			// the following statement is used to log any messages
			logger.info("New log");
			


	}
	
	public void addHandler() {
		try {
			fileHandler = new FileHandler("TLog");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.addHandler(fileHandler);
		SimpleFormatter formatter = new SimpleFormatter();
		fileHandler.setFormatter(formatter);
		
	}
	
	public void closeHandler() {
		    fileHandler.close();   //must call fileHandler.close or a .LCK file will remain
		
	}

	public void writeAllDecks() { // Write all the decks to the log
		String deck = "\nALL DECKS \n";

		for (Player player : this.game.players) {
			// Exclude players who lose
			if (!player.isOut() || player.currentCard != null) {
				if (player.isHuman == true) {
					deck += "Human Player";
				} else {
					deck += ("\nPlayer" + player.id);
				}
				for (int i = 0; i < player.deck.size(); i++) {
					deck += "\n" + player.deck.get(i).toString();
				}
			}
		}
		deck += "\n-----------------";
		logger.info(deck);
	}

	public void writeCommunalDeck() { // Write the communal deck to the log
		String deck = "\nCOMMUNAL DECK \n";
		if (this.game.communalDeck.size() == 0) {
			deck = "The communal deck is empty";
		} else
			for (int i = 0; i < this.game.communalDeck.size(); i++) {
				deck += "\n" + this.game.communalDeck.get(i).toString();
			}
		deck += "\n-----------------";
		logger.info(deck);
	}

	public void writeInPlayCards() { // Write the current cards in play to the log
		String deck = "\nCARDS IN PLAY \n";
		for (Player player : this.game.players) {
			// Exclude players who lose
			if (!player.isOut() || player.currentCard != null) {
				deck += "\n" + player.currentCard.toString();
			}
		}
		logger.info(deck);
	}

	public void writeChosenCategoryValues() { // Write the chosen category and the corresponding values to the log

		String category = "\nCHOSEN CATEGORY VALUES \n";
		for (Player player : this.game.players) {
			if (!player.isOut() || player.currentCard != null) {
				int playerId = player.id;
				int value = player.currentCard.getValue(this.game.chosenCategory);
				category += Integer.toString(player.id) + " -> " + this.game.chosenCategory + ": " + Integer.toString(value)
						+ "\n";

			}

		}
		logger.info(category);
	}

	public void writeWinner() { // Write the winner to the log

		String winner = "\nWINNER\n";
		winner += "Player " + game.getWinner().id + " wins!";
		logger.info(winner);
		closeHandler();
		

	}
	
	

}