package models;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import controllers.Controller;

public class TestLog {
	public Game game;
	private static final Logger logger = Logger.getLogger( TestLog.class.getName() );
	
	
	   
	    
	
	
	// Logger logger = Logger.getLogger("toptrumps.log");  
    FileHandler fileHandler;  
	public TestLog(Game game) {
		
		 this.game = game;

		    try {  

		        // This block configure the logger with handler and formatter  
		        fileHandler = new FileHandler("TestLog.log");  
		        logger.addHandler(fileHandler);
		        SimpleFormatter formatter = new SimpleFormatter();  
		        fileHandler.setFormatter(formatter);  

		        // the following statement is used to log any messages  
		        logger.info("New log");  

	
		    } catch (IOException e) {  
		        e.printStackTrace();  
		    }  
		}  
	
	public void writeAllDecks() { // TODO rewrite so account for players booted
		String deck = "\nALL DECKS \n";
		
		for (Player player : this.game.players) {
		      // Exclude players who lose
		    if (!player.isOut() || player.currentCard != null) {
		    	if (player.isHuman == true) {
		    		deck += "Human Player";
		    	}
		    	else {
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
	
	
	public void writeCommunalDeck() {
		  String deck = "\nCOMMUNAL DECK \n";
		  if (this.game.communalDeck.size() == 0) {
			  deck = "The communal deck is empty";
		  }
		  else for (int i = 0; i < this.game.communalDeck.size(); i++) {
	   		deck += "\n" + this.game.communalDeck.get(i).toString();
	   		}
		   deck += "\n-----------------";
		   logger.info(deck);
	   }
	
	
	
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
	
	
		
		
		
		
		
		
	
	
	 public void writeChosenCategoryValues() {
		  
		  String category ="\nCHOSEN CATEGORY VALUES \n";
		  for(Player player: this.game.players) {
			  if (!player.isOut() || player.currentCard != null) {
				  int playerId = player.id;
				  int value = player.currentCard.getValue(this.game.chosenCategory);
				  category += Integer.toString(player.id) + " -> " + this.game.chosenCategory + ": "
							  + Integer.toString(value) + "\n";
					  
				 
		  }
		  
		  }
		  logger.info(category);
	  }
	
	public void writeWinner() {
		
		String winner = "\nWINNER\n";
		winner += "Player " + game.getWinner().id + " wins!";
		logger.info(winner);
	
		
	}
	
}
	
	

//• The contents of the complete deck once it has been read in and constructed
//• The contents of the complete deck after it has been shuffled
//• The contents of the user’s deck and the computer’s deck(s) once they have been allocated. Be sure to
//indicate which the user’s deck is and which the computer’s deck(s) is.
//• The contents of the communal pile when cards are added or removed from it
//• The contents of the current cards in play (the cards from the top of the user’s deck and the computer’s
//deck(s))
//• The category selected and corresponding values when a user or computer selects a category
//• The contents of each deck after a round
//• The winner of the game
	





