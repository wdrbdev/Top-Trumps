package commandline;

import java.util.Scanner;

/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApplication {
	Model model;

	public TopTrumpsCLIApplication(Model model) {
		this.model = model;
	}

	public static void main(String[] args) {

		boolean running = true;
		while (running) {
			@SuppressWarnings("resource")
			Scanner s = new Scanner(System.in);
			View newViewObject = new View();
			newViewObject.initialOptionMessages();
			if (!s.hasNextInt()) {
				newViewObject.invalidSelectionMessage();
				continue;
			}
			int option = s.nextInt();
			if (option < 1 || option > 3) {
				newViewObject.invalidSelectionMessage();
				continue;
			}

			if (option == 1) {
				// TO DO
			}

			if (option == 2) {
				Model newGame = new Model();
				gameloop(newGame);

			}
			if (option == 3)
				running = false;
		}
	}
	
	void initialSetup(Model model) {
		model.extractCardDataFromTXTFile();
		model.setRoundWinnerStats(); // keep track of round winners.
		model.askNumberOfPlayers();
		model.createPlayers();
		model.distributeCardsAmongPlayers();
		model.setBooleans();
	}

	public static void gameloop(Model model) {
		{
			boolean running = true;
			while (running) {
				model.playerCardDisplay();
				model.chooseAnAttribute();
				model.getValuesForAttribute();
				model.findRoundOutcome();
				model.setPlayerCurrentCard();
				running = !model.getGameOver();
			}
		}
	}

}