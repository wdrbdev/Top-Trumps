package models;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TestLog {
	public Game game;
	private Logger logger = Logger.getLogger(TestLog.class.getName());

	private static FileHandler fileHandler;

	/**
	 * A method to set up a new log
	 * 
	 * @param game a Game object, the model in MVC pattern
	 */
	public TestLog(Game game) {
		this.game = game;
		this.addHandler(); // This block configures the logger with handler and formatter
		// the following statement denotes a new log
		logger.info("New log");
	}

	/**
	 * A method which configures the logger and correctly formats
	 */
	public void addHandler() {
		try {
			fileHandler = new FileHandler("TLog");
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.addHandler(fileHandler);
		SimpleFormatter formatter = new SimpleFormatter();
		fileHandler.setFormatter(formatter);
	}

	/**
	 * Method to close the FileHandler after the game is over.
	 */
	public void closeHandler() {
		fileHandler.close(); // must call fileHandler.close or an .LCK file will remain
	}

	/**
	 * Write the decks of all players to the log
	 */
	public void writeAllDecks() {
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

	/**
	 * Write the communal deck to the log
	 */
	public void writeCommunalDeck() {
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

	/**
	 * Write the current cards in play to the log
	 */
	public void writeInPlayCards() {
		String deck = "\nCARDS IN PLAY \n";
		for (Player player : this.game.players) {
			// Exclude players who lose
			if (!player.isOut() || player.currentCard != null) {
				deck += "\n" + player.currentCard.toString();
			}
		}
		logger.info(deck);
	}

	/**
	 * Write the chosen category and the corresponding values to the log
	 */
	public void writeChosenCategoryValues() {
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

	/**
	 * Write the winner to the log
	 */
	public void writeWinner() {
		String winner = "\nWINNER\n";
		winner += "Player " + game.getWinner().id + " wins!";
		logger.info(winner);
		closeHandler();
	}
}