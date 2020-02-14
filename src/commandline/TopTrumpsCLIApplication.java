package commandline;

import controllers.Controller;
import models.Game;
import models.Stats;
import views.View;

/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApplication {

	/**
	 * This main method is called by TopTrumps.java when the user specifies that
	 * they want to run in command line mode. The contents of args[0] is whether we
	 * should write game logs to a file.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		boolean writeGameLogsToFile = false; // Should we write game logs to file?
		if (args[0].equalsIgnoreCase("true"))
			writeGameLogsToFile = true; // Command line selection

		// State
		boolean userWantsToQuit = false; // flag to check whether the user wants to quit the application

		// MVC pattern
		Game game = new Game();
		Controller controller = new Controller(game);
		View view = new View(game, controller);
		controller.addView(view);
		// Start the test log if the -t flag is declared
		game.activateTestLog(writeGameLogsToFile);

		// Loop until the user wants to exit the game
		while (!userWantsToQuit) {

			// ----------------------------------------------------
			// Add your game logic here based on the requirements
			// ----------------------------------------------------
			// Initialize player and cards
			int nPlayers = 5;
			game.startGame(nPlayers);

			// Clear the screen
			// System.out.print("\033[H\033[2J");
			// System.out.flush();

			// Print start screen
			view.printStart();
			// If user choose to quit, break the while loop and exit from program
			if (controller.getUserWantsToQuit())
				break;

			// The loop for each game
			while (!game.isGameOver) {
				// Clear the screen
				// System.out.print("\033[H\033[2J");
				// System.out.flush();

				view.printRound();
				view.printActivePlayer();
				if (!game.isHumanOut) {
					view.printNCards();
					view.printDrawnCard();
				}
				if (game.currentWinner.isHuman) {
					view.printChoices();
				} else {
					game.chooseByAi();
				}

				if (writeGameLogsToFile) {
					game.tLog.writeChosenCategoryValues();
				}

				game.whoWon();

				if (game.getNTieCards() > 0) {
					view.printTieCards();
				} else {
					view.printWinner();
				}

				game.endTurn();
				if (writeGameLogsToFile) {
					game.tLog.writeAllDecks();
				}

				// view.click2Continue();

			}

			if (writeGameLogsToFile) {
				game.tLog.writeWinner();
			}

			// Update the game statistics and export to DB
			Stats.export2DB(game);
			view.printGameStats();

			// userWantsToQuit = true; // use this when the user wants to exit the game

			// post-game action
			game = new Game();
			controller = new Controller(game);
			view = new View(game, controller);
			controller.addView(view);

		}

	}

}
