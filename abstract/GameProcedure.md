- ## Start game

  - Call `new Game()` and initialize Game record/data (e.g. Game ID, ...) by setting attributes of `Game` by constructor of `Game`

- ## Import communal deck form DB

  - Call `Deck.Deck(dataSource)`, where source includes DB by `Deck.importDB()`, txt file by `Deck.importTXT()`, csv file by `Deck.importCSV()`, array of Card object by `Deck.importCards()`
  - Note: test dataset of cards could be imported by `new Card(String[] cardName, String[] categoryNames, int[] categoryValues)`

- ## Create players

  - Call `Game.setPlayer(int nHumanPlayer, int nAiPlayer)`

    - Check if # of Players >= 2 or Human Player > 1
    - Select # of human players and create Player (# = 0 or 1) and initialize human players by
    - Select # of AI players and create Player (# >= 0)

* ## Distribute deck to each Player according to # of players
  - For all players, `Player.deck = Deck.getRandomDeck(nCards)`, where `nCards = Deck.nCards / Game.Players[].length`
  - Edge case: not divisible (`Deck.nCards % Game.nPlayers != 0`)

##### Start of Turns

- ## Start turns of Game until a Player win the game

  - `while (Game.isGameOver()){}` for infinite loop
  - Game.isGameOver() check if there's only one Player who `Player.nCards > 0`

- ## Decide the current player for this turn

  - `Game.currentPlayer = Game.whosNext()`

- ## Each Player draw one card

  - Draw (transfer) one card from `Player.deck`(delete one card) to `Player.currentCard` by `Player.currentCard = Player.draw()`

- ## Current player choose one category from current card

  - For human player: `Game.chosenCategory = Player.chooseCategory()`
  - For AI player `Game.chosenCategory = Player.randomlyChooseCategory()`

- ## Determine which player wins

  - `Game.currentWinner = Game.whoWin(currentPlayer)`
    - Get current card of each player by `Player.getCurrentCard()`
    - Compare each card from the value `Player.currentCard.getCategoryValue(Game.chosenCategory)` of selected category `Game.chosenCategory`
    - Edge case: Tie
    - Set current winner to `Game.currentWinner = Game.whoWin()`
  - Transfer card from losers to winner by `Deck.addCards()` by `Game.cardTransfer(Game.currentWinner)`

- ## Reinitialize current status of Game and Player in this turn

  - Set `Player.currentCard, Game.chosenCategory, ...` by Player.reinitialize()

- ## End this turn and start new turn
  - `Game.turnId ++`

##### End of Turns

- ##Export and storage game statistics to DB

  - Call `Game.exportStatistics()`

- ##Show history statistics
  - Import history statistics by `Model.importStatistics()`
