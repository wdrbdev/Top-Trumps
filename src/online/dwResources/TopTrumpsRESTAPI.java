package online.dwResources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import controllers.Controller;
import models.Game;
import online.configuration.TopTrumpsJSONConfiguration;

@Path("/toptrumps") // Resources specified here should be hosted at http://localhost:7777/toptrumps
@Produces(MediaType.APPLICATION_JSON) // This resource returns JSON content
@Consumes(MediaType.APPLICATION_JSON) // This resource can take JSON content as input
/**
 * This is a Dropwizard Resource that specifies what to provide when a user
 * requests a particular URL. In this case, the URLs are associated to the
 * different REST API methods that you will need to expose the game commands to
 * the Web page.
 * 
 * Below are provided some sample methods that illustrate how to create REST API
 * methods in Dropwizard. You will need to replace these with methods that allow
 * a TopTrumps game to be controled from a Web page.
 */
public class TopTrumpsRESTAPI {

	/**
	 * A Jackson Object writer. It allows us to turn Java objects into JSON strings
	 * easily.
	 */
	ObjectWriter oWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();

	// Model and Controller
	public Game game;
	public Controller controller;
	public int nPlayers;

	/**
	 * Contructor method for the REST API. This is called first. It provides a
	 * TopTrumpsJSONConfiguration from which you can get the location of the deck
	 * file and the number of AI players.
	 * 
	 * @param conf
	 */
	public TopTrumpsRESTAPI(TopTrumpsJSONConfiguration conf) {
		// ----------------------------------------------------
		// Add relevant initalization here
		// ----------------------------------------------------

	}

	// ----------------------------------------------------
	// Add relevant API methods here
	// ----------------------------------------------------

	@GET
	@Path("/helloJSONList")
	/**
	 * Here is an example of a simple REST get request that returns a String. We
	 * also illustrate here how we can convert Java objects to JSON strings.
	 * 
	 * @return - List of words as JSON
	 * @throws IOException
	 */
	public String helloJSONList() throws IOException {

		List<String> listOfWords = new ArrayList<String>();
		listOfWords.add("Hello");
		listOfWords.add("World!");

		// We can turn arbatory Java objects directly into JSON strings using
		// Jackson seralization, assuming that the Java objects are not too complex.
		String listAsJSONString = oWriter.writeValueAsString(listOfWords);

		return listAsJSONString;
	}

	@GET
	@Path("/helloWord")
	/**
	 * Here is an example of how to read parameters provided in an HTML Get request.
	 * 
	 * @param Word - A word
	 * @return - A String
	 * @throws IOException
	 */
	public String helloWord(@QueryParam("Word") String Word) throws IOException {
		return "Hello " + Word;
	}

	@GET
	@Path("/newgame")
	public void newGame() throws IOException {
		this.game = new Game();
		this.controller = new Controller(game);
		this.game.startGame(this.nPlayers);

		System.out.println("----------");
		System.out.println("A new game is activated");
		System.out.println("nPlayers = " + this.game.nPlayers);
		System.out.println("----------");
	}

	@GET
	@Path("/setnplayers")
	public void getGame(@QueryParam("nPlayers") int nPlayers) {
		this.nPlayers = nPlayers;
	}

	@GET
	@Path("/getgame")
	public String getGame() {
		return Controller.game2Json(this.game);
	}

	@GET
	@Path("/endturn")
	public void endTurn() {
		this.game.endTurn();
	}

	@GET
	@Path("/choosecategory")
	public void chooseCategory(@QueryParam("category") String chosenCategory) {

		// System.out.println("----------");
		// System.out.println("Current chosenCategory = " + chosenCategory);
		for (String category : this.game.winningCard.categories) {
			if (category.equals(chosenCategory)) {
				this.game.setChosenCategory(category);
				// System.out.println(this.game.currentWinner.currentCard.getValue(category));
			}
		}
		// System.out.println("----------");

		// return this.game.chosenCategory;
	}

	@GET
	@Path("/choosebyai")
	public void chooseByAi() {
		this.game.chooseByAi();
	}

	@GET
	@Path("/whowon")
	public void whowon() {
		this.game.whoWon();
	}

	@GET
	@Path("/getchosencategory")
	public String getChosenCategory() {
		return this.game.chosenCategory;
	}

	@GET
	@Path("/printcard")
	public void printCard() {
		this.game.currentWinner.currentCard.print(System.err);
	}

	@GET
	@Path("/gethistorystats")
	public String getHitoryStats() {
		return Controller.historyStatistics2Json(this.game);
	}

}
