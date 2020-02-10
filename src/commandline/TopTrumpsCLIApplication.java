package commandline;

import controllers.Controller;
import models.Game;
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

		// MVC
		Game game = new Game();
		Controller controller = new Controller(game);
		View view = new View(game, controller);
		controller.addView(view);

		// Loop until the user wants to exit the game
		while (!userWantsToQuit) {

			// ----------------------------------------------------
			// Add your game logic here based on the requirements
			// ----------------------------------------------------
			int nPlayers = 5;
			game.startGame(nPlayers);
			System.out.print("\033[H\033[2J");
			System.out.flush();
			view.printStart();
			if (controller.getUserWantsToQuit())
				break;

			while (!game.isGameOver) {
				System.out.print("\033[H\033[2J");
				System.out.flush();

				view.printRound();
				if (!game.isHumanOut) {
					view.printNCards();
					view.printDrawnCard();
				}
				if (game.currentWinner.isHuman) {
					view.printChoices();
				} else {
					game.chooseByAi();
				}

				game.whoWon();

				if (game.getNTieCards() > 0) {
					view.printTieCards();
				} else {
					view.printWinner();
				}

				game.endTurn();

				view.click2Continue();

			}
			game.updateStats();
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
