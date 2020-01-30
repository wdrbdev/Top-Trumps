package commandline;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

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

		// new stuff
		Scanner s = new Scanner(System.in);
		
		
		
		
		// Loop until the user wants to exit the game

		while (!userWantsToQuit) {
			// ----------------------------------------------------
			// Add your game logic here based on the requirements
			// ----------------------------------------------------#
			
			System.out.println("Do you want to see past results or play a game?");
			System.out.println("\t 1. Print game statistics");
			System.out.println("\t 2. Play a game");
			System.out.print("Enter the number for your selection:");
			int option = s.nextInt();
			if (option == 2) {
			Game game = new Game();
			game.gameloop();
			}

			userWantsToQuit = true; // use this when the user wants to exit the game

		}

	}

	

}
