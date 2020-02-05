package commandline;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Model {

	private ArrayList<Card> communalDeck;
	private ArrayList<Integer> roundWinnerStats, cardAttributeNumericalValues, valueOfAttribute, stillInGame;
	private int attributeChoice, howManyOpponents, playerWhoseTurnItIs, numberOfDrawnRounds, roundsWonByHuman,
			highScorer, roundNumber, roundsWonByAI, numPlayers, roundNumberPlusOne, winningPlayerNumber,
			numberOfHumanWins, numberOfAI1Wins, numberOfAI2Wins, numberOfAI3Wins, numberOfAI4Wins;
	private ArrayList<String> attributeNames;
	private ArrayList<Player> players;
	private Boolean humanStillInGame, humanNotifiedOfElimination, gameOver;
	private View view;
	private TopTrumpsCLIApplication controller;
	
	
	private int numberOfGame; // For Albert

	/*
	 * The first item in the 'players' ArrayList is the human player, i.e. at
	 * position zero. AI 1 is at players.get(1), etc.
	 * 
	 * The stillInGame ArrayList contains Integer values which correspond to players
	 * who haven't been knocked out of the game. For example, if 0 is still in
	 * stillInGame, the human player hasn't been knocked out, and if 1 remains in
	 * stillInGame, then AI Player 1 is still active in the game. It is the presence
	 * or absence of a number in stillInGame that shows whether a player is still in
	 * the game, not the position of that number in the list.
	 * 
	 */

	public Model() {
		view = new View(this);
		controller = new TopTrumpsCLIApplication(this);
		controller.initialSetup(this);
	}

	void setBooleans() {
		humanStillInGame = true;
		gameOver = false;
		humanNotifiedOfElimination = false;
	}

	void setRoundWinnerStats() {
		roundWinnerStats = new ArrayList<Integer>();
	}

	void instantiateRoundWinnerStats() {
		roundWinnerStats = new ArrayList<Integer>();
	}

	void setInitialBoolean() {
		humanStillInGame = true;
		gameOver = false;
		humanNotifiedOfElimination = false;
	}

	public void incrementRoundNumber() {
		roundNumber++;
	}

	// Extracts the attribute types and card-specific data.

	public void extractCardDataFromTXTFile() {
		FileReader cardFileReader = null;
		try {
			cardFileReader = new FileReader("StarCitizenDeck.txt");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		@SuppressWarnings("resource")
		Scanner textFileScanner = new Scanner(cardFileReader);
		String line = textFileScanner.nextLine();
		String[] token = line.split(" ");

		attributeNames = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			attributeNames.add(i, token[i + 1]);
		}
		communalDeck = new ArrayList<Card>();
		while (textFileScanner.hasNextLine()) {
			line = textFileScanner.nextLine();
			token = line.split(" ");
			cardAttributeNumericalValues = new ArrayList<Integer>();
			for (int i = 0; i < 5; i++) {
				cardAttributeNumericalValues.add(i, Integer.parseInt(token[i + 1]));
			}
			Card card = new Card(token[0], cardAttributeNumericalValues, attributeNames);
			communalDeck.add(card);
		}
	}

	// Each player allocated the same number of cards at start:
	// In case there are 3 players, each player would get 13 cards, even though the
	// deck contains 40 cards (as per Richard McCreadie).

	void distributeCardsAmongPlayers() {
		int cardsPerPlayer = communalDeck.size() / numPlayers;
		for (int i = 0; i < cardsPerPlayer; i++) {
			for (int j = 0; j < numPlayers; j++) {
				Collections.shuffle(communalDeck);
				players.get(j).addEachPlayersCards(communalDeck.get(i));
			}
		}
		communalDeck.clear();// To remove the remaining card in the event of 3 players each getting 13 cards.
	}

	// players.get(0) is the human player

	void createPlayers() {
		stillInGame = new ArrayList<Integer>();
		players = new ArrayList<Player>();
		for (int i = 0; i < numPlayers; i++) {
			Player newPlayer = new Player();
			players.add(newPlayer);// See notes at top of this class.
			stillInGame.add(i);// See notes at top of this class.
		}
	}

	void playerCardDisplay() {
		roundNumberPlusOne = roundNumber + 1;
		if (humanStillInGame) {
			view.cardsDrawnMessage();
			
			view.displayHumanCard();// Display human card attributes and scores
			}
			view.aiDeckInfoDisplay();
			// anyKeyToContinue();
			System.out.println("DEBUG - still in game size: " + stillInGame.size());
		
	}

	void chooseAnAttribute() {
		boolean thisMethodRunning = true;
		while (thisMethodRunning) {
			if (playerWhoseTurnItIs == 0) {
				@SuppressWarnings("resource")
				Scanner s = new Scanner(System.in);
				view.itIsYourTurnMessage();
				view.listOfHumanCardAttributes();
				view.askSelectionNumber();

				if (!s.hasNextInt()) {
					view.askForAttributeNoAgain();
					continue;
				}
				int numberPlayerInput = s.nextInt();
				attributeChoice = numberPlayerInput - 1;
				if (attributeChoice < 0 || attributeChoice > 4) {
					view.askForAttributeNoAgain();
					continue;
				}
				thisMethodRunning = false;
			} else {
				attributeChoice = new Random().nextInt(4);
				view.adviseAITurnMessage();
				thisMethodRunning = false;
			}
		}
	}

	// If player has been eliminated, their slot in the value of current attribute
	// array is set to -1.
	// That way, they cannot win the round or contribute to a draw, but the position
	// of the players stays intact in the event of a player being knocked out.

	public void getValuesForAttribute() {
		valueOfAttribute = new ArrayList<Integer>();
		for (int j = 0; j < numPlayers; j++) {
			if (stillInGame.contains(j)) {
				valueOfAttribute.add(players.get(j).getDeck().get(0).getValues().get(attributeChoice));
			} else
				valueOfAttribute.add(-1);
		}
	}

	public void findRoundOutcome() {
		int topScorer = Collections.max(valueOfAttribute);
		int freq = Collections.frequency(valueOfAttribute, topScorer);

		if (freq > 1)
			actionsIfRoundADraw();

		if (freq == 1) {
			if (valueOfAttribute.indexOf(topScorer) == 0) {
				highScorer = 0;// human. Relates to position in player array.
				actionsIfRoundWonByHuman();
			} else {
				highScorer = valueOfAttribute.indexOf(topScorer);
				actionsIfRoundWonByAI();
			}
		}
		// checkIfGameOver();
	}

	
	public void setPlayerCurrentCard() {
		for (int i = 0; i < numPlayers; i++ ) {
			if (!stillInGame.contains(i))
				continue;
			players.get(i).setCurrentCard(players.get(i).getDeck().get(0));
		}
	}

	public void actionsIfRoundADraw() {
		for (int j = 0; j < numPlayers; j++) {
			if (!stillInGame.contains(j))
				continue;
			communalDeck.add(players.get(j).getDeck().get(0));
		}
		view.roundWasDrawMessage();
		roundNumber++;
		roundWinnerStats.add(-1); // This signifies that the round was a draw.
		deletePreviousRoundCards();
		updateListOfActivePlayers();
		checkIfGameOver();
		checkIfHumanStillInGame();
	}

	public void actionsIfRoundWonByHuman() {
		view.youWonRoundMessage();
		cardTransfer(players.get(highScorer));
		playerWhoseTurnItIs = 0;
		roundWinnerStats.add(0); // Zero is the array id for 'human'
		roundNumber++;
		deletePreviousRoundCards();
		updateListOfActivePlayers();
		checkIfGameOver();
	}

	public void actionsIfRoundWonByAI() {
		view.aiWonRoundMessage();
		cardTransfer(players.get(highScorer));
		playerWhoseTurnItIs = highScorer;
		roundWinnerStats.add(highScorer); // Zero is the array id for 'human'
		roundsWonByAI++;
		roundNumber++;
		deletePreviousRoundCards();
		updateListOfActivePlayers();
		checkIfHumanStillInGame();
		checkIfGameOver();
	}

	public void anyKeyToContinue() {
		view.pressAnyKeyToContinueMessage();
		try {
			System.in.read();
		} catch (IOException e) {
		}

	}

	// Gives winning player their card and the other players' cards

	void cardTransfer(Player winningPlayer) {
		// transfers to communal deck first:
		for (int j = 0; j < numPlayers; j++) {
			if (players.get(j).getDeck().isEmpty())
				continue;
			Card cardToTransfer = players.get(j).getDeck().get(0);
			communalDeck.add(cardToTransfer);
		}
		// Transfers everything in communal deck to winning player:
		players.get(highScorer).getDeck().addAll(communalDeck);
		communalDeck.clear();
	}

	// Removes card at position 0 to put new card at position 0.

	void deletePreviousRoundCards() {
		for (int j = 0; j < numPlayers; j++) {
			if (stillInGame.contains(j)) {
				players.get(j).getDeck().remove(0);
			}
		}
	}

	// Checks if the array number corresponding to a removed player is in
	// stillInGame and removes it if so.

	public void updateListOfActivePlayers() {
		ArrayList<Integer> toRemove = new ArrayList<Integer>();
		for (int j = 0; j < numPlayers; j++) {
			if (players.get(j).getDeck().size() == 0) {
				for (Integer shouldNotBeInGame : stillInGame)
					if (j == shouldNotBeInGame)
						toRemove.add(shouldNotBeInGame);
			}
		}

		for (Integer playerNumbers : toRemove) {
			stillInGame.remove((Integer) playerNumbers);
		}
	}

	// Game is deemed over if there is just one player standing

	public void checkIfGameOver() {
		if (stillInGame.size() == 1) {
			gameOver = true;
			postGameActions();
		}
	}

	public void checkIfHumanStillInGame() {
		if (!stillInGame.contains(0)) {
			humanStillInGame = false;
			if (humanNotifiedOfElimination == false) {
				notifyHumanOfElimination();
			}
		}
	}

	void notifyHumanOfElimination() {
		if (stillInGame.size() >= 2) {
			view.humanEliminationMessage();
			humanNotifiedOfElimination = true;
		}
	}

	public void postGameActions() {
		whoWonTheGame();
		
		players.get(winningPlayerNumber).setWinner(true);
		
		notifyResult();
		view.winningCard();
		anyKeyToContinue();
		roundSummary();
		anyKeyToContinue();
	}

	void whoWonTheGame() {
		for (int j = 0; j < numPlayers; j++) {
			if (stillInGame.contains(j)) {
				winningPlayerNumber = j;
			}
		}
	}

	int getWinnerOfGame() {
		return winningPlayerNumber;
	}

	void notifyResult() {
		if (getWinnerOfGame() == 0)
			view.humanWonGameMessage();
		else
			view.aiWonGameMessage();
	}

	public void roundSummary() {
		view.openingRoundSummaryMessage();
		numberOfHumanWins = 0;
		numberOfAI1Wins = 0;
		numberOfAI2Wins = 0;
		numberOfAI3Wins = 0;
		numberOfAI4Wins = 0;
		numberOfDrawnRounds = 0;

		view.numberOfRoundsMessage();

		for (int winnerThisRound : roundWinnerStats) {
			if (winnerThisRound == -1)
				numberOfDrawnRounds++;
			if (winnerThisRound == 0)
				numberOfHumanWins++;
			if (winnerThisRound == 1)
				numberOfAI1Wins++;
			if (winnerThisRound == 2)
				numberOfAI2Wins++;
			if (winnerThisRound == 3)
				numberOfAI3Wins++;
			if (winnerThisRound == 4)
				numberOfAI4Wins++;
		}

		view.roundSummaryMessage();
		view.howManyAIWins();
	}
	
	void askNumberOfPlayers() {
		boolean running = true;
		while (running) {
			@SuppressWarnings("resource")
			Scanner s = new Scanner(System.in);
			view.howManyPlayersQuestion();
			if (!s.hasNextInt()) {
				view.tryAgainMessage();
				continue;
			}
			howManyOpponents = s.nextInt();
			if (howManyOpponents < 1 || howManyOpponents > 4) {
				view.tryAgainMessage();
				continue;
			}
			view.numberOfOpponentsMessage();
			numPlayers = howManyOpponents + 1;
			running = false;
		}
	}

	int getNumberOfRoundsGameJustFinished() {
		return roundNumber;
	}

	int getNumberOfRoundsWonByAIGameJustFinished() {
		return roundsWonByAI;
	}

	int getNumberOfRoundsWonByHumanIGameJustFinished() {
		return roundsWonByHuman;
	}

	public ArrayList<Card> getCommunalDeck() {
		return communalDeck;
	}

	public ArrayList<Integer> getRoundWinnerStats() {
		return roundWinnerStats;
	}

	public ArrayList<Integer> getCardAttributeNumericalValues() {
		return cardAttributeNumericalValues;
	}

	public ArrayList<Integer> getValueOfAttribute() {
		return valueOfAttribute;
	}

	public ArrayList<Integer> getStillInGame() {
		return stillInGame;
	}

	public int getAttributeChoice() {
		return attributeChoice;
	}

	public int getHowManyOpponents() {
		return howManyOpponents;
	}

	public int getPlayerWhoseTurnItIs() {
		return playerWhoseTurnItIs;
	}

	public int getNumberOfDrawnRounds() {
		return numberOfDrawnRounds;
	}

	public int getRoundsWonByHuman() {
		return roundsWonByHuman;
	}

	public int getHighScorer() {
		return highScorer;
	}

	public int getRoundNumber() {
		return roundNumber;
	}

	public int getRoundsWonByAI() {
		return roundsWonByAI;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public int getRoundNumberPlusOne() {
		return roundNumberPlusOne;
	}

	public ArrayList<String> getAttributeNames() {
		return attributeNames;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Boolean getHumanStillInGame() {
		return humanStillInGame;
	}

	public Boolean getHumanNotifiedOfElimination() {
		return humanNotifiedOfElimination;
	}

	public Boolean getGameOver() {
		return gameOver;
	}

	public int getWinningPlayerNumber() {
		return winningPlayerNumber;
	}

	public int getNumberOfHumanWins() {
		return numberOfHumanWins;
	}

	public int getNumberOfAI1Wins() {
		return numberOfAI1Wins;
	}

	public int getNumberOfAI2Wins() {
		return numberOfAI2Wins;
	}

	public int getNumberOfAI3Wins() {
		return numberOfAI3Wins;
	}

	public int getNumberOfAI4Wins() {
		return numberOfAI4Wins;
	}

	
	

}
