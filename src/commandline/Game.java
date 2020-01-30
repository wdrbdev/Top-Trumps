package commandline;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import java.util.Scanner;

public class Game { // this class should perhaps be named 'TopTrumpsModelCLIModel' or something like
					// that
	private ArrayList<Player> players;
	private ArrayList<Card> communalDeck; // This should maybe be an ArrayList as well, since it is an array of objects
											// of
											// varying length - T

	private int roundNumber = 1;
	private boolean draw;
	private int playerWhoseTurnItIs = 0;

	public Game() {

	}

	public void gameloop() {

		// make a filereader object
		FileReader fr = null;
		try {
			fr = new FileReader("StarCitizenDeck.txt"); // Weirdly just putting the name seems to make it work. We
														// should test this on other setups - T
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// make a scanner around the filereader
		Scanner r = new Scanner(fr);

		String line = r.nextLine();

		String[] token = line.split(" ");
		String[] categories = new String[5];

		for (int i = 0; i < 5; i++)
			categories[i] = token[i + 1];

		LinkedList<Card> communalDeck = new LinkedList<Card>();

		// Loop until no lines left
		while (r.hasNextLine()) {
			// get the next line
			line = r.nextLine();
			token = line.split(" ");

			int[] attributeArray = new int[5];

			for (int i = 0; i < 5; i++) {
				attributeArray[i] = Integer.parseInt(token[i + 1]);
			}

			Card card = new Card(token[0], attributeArray);

			communalDeck.add(card);

		}

		Scanner s = new Scanner(System.in);
		System.out.println("Game Start" + "\nHow many will play?" + "\n\t 1. 2 Players" + "\n\t 2. 3 Players"
				+ "\n\t 3. 4 Players" + "\n\t 4. 5 Players" + "\nEnter the number for your selection:");

		int option = s.nextInt();
		if (option > 0 && option < 5) {

			int numPlayers = option + 1;

			Collections.shuffle(communalDeck);

			LinkedList<Card>[] subDecks = new LinkedList[numPlayers];

			for (int i = 0; i < numPlayers; i++) {
				subDecks[i] = new LinkedList<Card>();
			}

			int size = communalDeck.size();
			for (int i = 0; i < size / numPlayers; i++) {
				for (int j = 0; j < numPlayers; j++) {
					subDecks[j].add(communalDeck.pop());
				}
			}

			Player[] players = new Player[numPlayers];
			for (int i = 0; i < numPlayers; i++) {
				players[i] = new Player(false, new Deck(subDecks[i]));
			}

			System.out.println("Game Start");

			

			while (true) {

				draw = false;
				System.out.println(
						"\nRound " + roundNumber + "\n" + "\nRound " + roundNumber + ": Players have drawn their cards"
								+ "\nYou drew '" + players[0].getDeck().getCards().get(0).getName() + "'" + "\n"
								+ categories[0] + ":" + players[0].getDeck().getCards().get(0).getCategories()[0] + "\n"
								+ categories[1] + ":" + players[0].getDeck().getCards().get(0).getCategories()[1] + "\n"
								+ categories[2] + ":" + players[0].getDeck().getCards().get(0).getCategories()[2] + "\n"
								+ categories[3] + ":" + players[0].getDeck().getCards().get(0).getCategories()[3] + "\n"
								+ categories[4] + ":" + players[0].getDeck().getCards().get(0).getCategories()[4]
								+ "\nThere are " + players[0].getDeck().getCards().size() + " cards in your deck.");

				int[] values = new int[numPlayers];

				if (playerWhoseTurnItIs == 0) {
					System.out.println("\nIt is your turn to select a category, the categories are:" + "\n1: "
							+ categories[0] + "\n2: " + categories[1] + "\n3: " + categories[2] + "\n4: "
							+ categories[3] + "\n5: " + categories[4] + "\nEnter the number for your selection:");
					option = s.nextInt() - 1;

				}

				else {
					option = 0;
				}

				for (int j = 0; j < numPlayers; j++) {
					values[j] = players[j].getDeck().getCards().get(0).getCategories()[option];
					System.out.println(players[j].getDeck().getCards().get(0).getCategories()[option]);
				}

				for (int j = 0; j < numPlayers; j++) {
					communalDeck.add(subDecks[j].pop());
				}

				int max = values[0];
				int playerWhoWon = 0;

				for (int k = 1; k < values.length; k++) {
					if (max < values[k]) {
						max = values[k];
						playerWhoWon = k;
					} else if (max == values[k])

						draw = true;
				}

				if (playerWhoWon == 0 && draw == false) {
					System.out.println("Round" + roundNumber + ": You won this round");
					players[0].getDeck().getCards().addAll(communalDeck);
					communalDeck.clear();
					playerWhoseTurnItIs = 0;
				}

				else if (playerWhoWon != 0 && draw == false) {
					System.out.println("Round" + roundNumber + ": AI Player " + playerWhoWon + " won this round");
					players[playerWhoWon].getDeck().getCards().addAll(communalDeck);
					communalDeck.clear();
					playerWhoseTurnItIs = playerWhoWon;
				} else {
					System.out.println("Round" + roundNumber + ": This round was a Draw, commonpile now has "
							+ communalDeck.size() + " cards");
				}

				printWinningCard(); // to be completed

				for (int j = 0; j < numPlayers; j++) {
					if (players[j].getDeck().getCards().size() == 0) {
						players[j].setIsActive(false);
					}

					

				}
				roundNumber++;
			}
		}

	}

	private void printWinningCard() {
		// TODO Auto-generated method stub

	}

}
