package commandline;

public class View {

	Model model;

	public View() {
	}

	public View(Model model) {
		this.model = model;
	}

	public void howManyOpponentsNotification() {
		System.out.println("You will be facing " + model.getHowManyOpponents() + " opponents. Good luck!");
	}

	public void howManyPlayersQuestion() {
		System.out.println("Game Start" + "\nYou can have up to 4 opponents. How many would you like? \n");
	}

	public void cardsDrawnMessage() {
		System.out.println("\nRound " + model.getRoundNumberPlusOne() + " - Players have drawn their cards\n"
				+ "\nYou drew " + model.getPlayers().get(0).getDeck().get(0).getName() + "\n");
	}

	public void displayHumanCard() {
		for (int i = 0; i < 5; i++)
			System.out.println(model.getAttributeNames().get(i) + ":"
					+ model.getPlayers().get(0).getDeck().get(0).getValues().get(i));

		System.out.println("\nThere are " + model.getPlayers().get(0).getDeck().size() + " cards in your deck.");
	}

	public void aiDeckInfoDisplay() {
		for (int aiPlayerNumber = 1; aiPlayerNumber < model.getNumPlayers(); aiPlayerNumber++) {
			if (!model.getStillInGame().contains(aiPlayerNumber))
				System.out.println("AI Player " + aiPlayerNumber + " has been eliminated so has no cards.");
			else {
				System.out.println("AI Player " + aiPlayerNumber + " has "
						+ model.getPlayers().get(aiPlayerNumber).getDeck().size() + " cards in their deck.");
			}
		}
	}

	public void pressAnyKeyToContinueMessage() {
		System.out.println("Press any key to continue");
	}

	public void playersHaveDrawnMessage() {
		System.out.println(
				"\nRound " + model.getRoundNumberPlusOne() + ": The remaining players have drawn their cards.\n");
	}

	public void listOfHumanCardAttributes() {
		for (int i = 0; i < 5; i++)
			System.out.println((i + 1) + ": " + model.getAttributeNames().get(i));
	}

	void itIsYourTurnMessage() {
		System.out.println("\nIt is your turn to select a category, the categories are:\n");
	}

	void askSelectionNumber() {
		System.out.println("\nEnter the number for your selection: ");
	}

	void askForAttributeNoAgain() {
		System.out.println("I need you to enter a number between 1 and 5. Please try again.");
	}

	void adviseAITurnMessage() {
		System.out.println("\nIt is the turn of AI player " + model.getHighScorer() + " to choose a category.\n");
	}

	void roundWasDrawMessage() {
		System.out.println("ROUND " + model.getRoundNumberPlusOne() + " COMPLETED: A draw! The community pile now has "
				+ model.getCommunalDeck().size() + " cards.");
	}

	void youWonRoundMessage() {
		System.out.println("\nROUND " + model.getRoundNumberPlusOne() + " COMPLETED: You won this round! Well done!");
	}

	void aiWonRoundMessage() {
		System.out.println("ROUND " + model.getRoundNumberPlusOne() + " COMPLETED: AI Player " + model.getHighScorer()
				+ " won this round.");
	}

	void humanEliminationMessage() {
		System.out.println(
				"I'm afraid you've been knocked out of the game. \nBetter luck next time! \nThe game will continue till only one player has any cards left.\n");
	}

	void humanWonGameMessage() {
		System.out.println("\nCongratulations - you won the game!\n");
	}

	void aiWonGameMessage() {
		System.out.println("\nAI Player " + model.getWinnerOfGame() + " won this time. Better luck next time!\n");
	}

	void winningCard() {
		System.out.println("This is the winning card. \n");
		System.out.println(
				"Card name: " + model.getPlayers().get(model.getWinningPlayerNumber()).getDeck().get(0).getName());

		for (int i = 0; i < 5; i++)
			System.out.println(model.getAttributeNames().get(i) + ":"
					+ model.getPlayers().get(model.getWinningPlayerNumber()).getDeck().get(0).getValues().get(i));
	}

	void tryAgainMessage() {
		System.out.println("I am looking for a number between 1 and 4. Please try again");
	}

	void numberOfOpponentsMessage() {
		System.out.println("You will be facing " + model.getHowManyOpponents() + " opponents. Good luck!");
	}

	void roundSummaryMessage() {
		if (model.getNumberOfHumanWins() == 1)
			System.out.println("You won one round.");
		if (model.getNumberOfHumanWins() > 1)
			System.out.println("You won " + model.getNumberOfHumanWins() + " rounds.");
		else
			System.out.println("You didn't win any rounds. Better luck next time.");

		if (model.getNumberOfDrawnRounds() == 1)
			System.out.println("There was one draw.");
		if (model.getNumberOfDrawnRounds() > 1)
			System.out.println("There were " + model.getNumberOfDrawnRounds() + " draws.");
		else
			System.out.println("There were no draws during this game.");

		if (model.getNumberOfAI1Wins() == 1)
			System.out.println("AI Player 1 won one round.");
		if (model.getNumberOfAI1Wins() > 1)
			System.out.println("AI Player 1 won " + model.getNumberOfAI1Wins() + " rounds.");
		else
			System.out.println("AI Player 1 didn't win any rounds.");

		if (model.getNumPlayers() < 3)
			return;

		if (model.getNumberOfAI2Wins() == 1)
			System.out.println("AI Player 2 won one round.");
		if (model.getNumberOfAI2Wins() > 1)
			System.out.println("AI Player 2 won " + model.getNumberOfAI2Wins() + " rounds.");
		else
			System.out.println("AI Player 2 didn't win any rounds.");

		if (model.getNumPlayers() < 4)
			return;

		if (model.getNumberOfAI3Wins() == 1)
			System.out.println("AI Player 3 won one round.");
		if (model.getNumberOfAI3Wins() > 1)
			System.out.println("AI Player 3 won " + model.getNumberOfAI3Wins() + " rounds.");
		else
			System.out.println("AI Player 3 didn't win any rounds.");

		if (model.getNumPlayers() < 5)
			return;

		if (model.getNumberOfAI4Wins() == 1)
			System.out.println("AI Player 4 won one round.");
		if (model.getNumberOfAI4Wins() > 1)
			System.out.println("AI Player 4 won " + model.getNumberOfAI4Wins() + " rounds.");
		else
			System.out.println("AI Player 4 didn't win any rounds.");
	}

	void howManyAIWins() {
		if (model.getRoundsWonByAI() == 1)
			System.out.println("The computer won 1 round in total");
		if (model.getRoundsWonByAI() > 1)
			System.out.println("The computer won " + model.getRoundsWonByAI() + " rounds");
		else
			System.out.println("The computer didn't win any rounds");
	}

	void initialOptionMessages() {
		System.out.println("Do you want to see past results or play a game?");
		System.out.println("\t 1. Print game statistics");
		System.out.println("\t 2. Play a game");
		System.out.println("\t 3. Quit game");
		System.out.print("Enter the number for your selection: ");
	}

	void invalidSelectionMessage() {
		System.out.println("That is not a valid option. Please enter 1, 2 or 3.");
	}

	void openingRoundSummaryMessage() {
		System.out.println("SUMMARY OF THIS GAME:");
	}

	void numberOfRoundsMessage() {
		System.out.println("\nThere were a total of " + model.getRoundNumber() + " rounds.");
	}

}
