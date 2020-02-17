"use strict";

// Get the json of Game object in java side model
function getGame() {
  let game;
  $.ajax({
    url: "/toptrumps/getgame",
    type: "get",
    data: {},
    async: false,
    success: function(data) {
      game = data;
    }
  });
  return game;
}

// When the end of the turn, call game.endTurn()
function endTurn() {
  $.get("/toptrumps/endturn");
}

// Add all the cards, including human card and AI cards
function getAllCards() {
  $("#AiCards").empty();
  const game = getGame();

  // Template html for card
  const cardTemplate = `<div id="card" class="card" style="margin: 4px 2px;-ms-flex: 0 0 30%; flex: 0 0 30%; max-width: 30%;"> <div id="card-header" class="container" style="margin:5px 0;"><h5 id="playerName" class="d-inline-block float-left">Player </h5><h5 class="d-inline-block" style="margin: 0 5px"><span id="nCards" class="badge badge-primary float-right">nCards</span></h5></div><div class="card-body align-top" style="padding: 3px"><img class="card-img-top container" src="https://placedog.net/480/360" alt="card image" width="160" height="90" /> <h5 id="emptycardtitle" class="card-title container"></h5></div><ul id="emptycardul" class="list-group list-group-flush align-bottom"></ul></div>`;
  const cardLi = `<li id="emptyli" class="list-group-item" style="padding: 3px 20px;"  > <div id="emptycategory" class="float-left">level</div> <div id="emptyvalue" class="float-right">8</div> </li>`;

  // Call insertCurrentCard() to add card.
  for (let i = 0; i < game.players.length; i++) {
    if (
      game.players[i].isOut !== true &&
      game.players[i].currentCard !== null
    ) {
      insertCurrentCard(game.players, cardTemplate, cardLi, "#AiCards", i);
    }
  }
}

// Only add AI players' cards
function getAiCards() {
  $("#AiCards").empty();
  const game = getGame();

  // Template html for card
  const cardTemplate = `<div id="card" class="card" style="margin: 4px 2px;-ms-flex: 0 0 30%; flex: 0 0 30%; max-width: 30%;"> <div id="card-header" class="container" style="margin:5px 0;"><h5 id="playerName" class="d-inline-block float-left">Player </h5><h5 class="d-inline-block" style="margin: 0 5px"><span id="nCards" class="badge badge-primary float-right">nCards</span></h5></div><div class="card-body align-top" style="padding: 3px"><img class="card-img-top container" src="https://placedog.net/480/360" alt="card image" width="160" height="90" /> <h5 id="emptycardtitle" class="card-title container"></h5></div><ul id="emptycardul" class="list-group list-group-flush align-bottom"></ul></div>`;
  const cardLi = `<li id="emptyli" class="list-group-item" style="padding: 3px 20px;"  > <div id="emptycategory" class="float-left">level</div> <div id="emptyvalue" class="float-right">8</div> </li>`;

  for (let i = 0; i < game.players.length; i++) {
    if (game.players[i].isHuman !== true) {
      if (
        game.players[i].isOut !== true &&
        game.players[i].currentCard !== null
      ) {
        insertCurrentCard(game.players, cardTemplate, cardLi, "#AiCards", i);
      }
    }
  }
}

// Only add human player's card
function getHumanCards() {
  $("#AiCards").empty();
  const game = getGame();

  // Template html for card
  const cardTemplate = `<div id="card" class="card" style="margin: 4px 2px;-ms-flex: 0 0 30%; flex: 0 0 30%; max-width: 30%;"> <div id="card-header" class="container" style="margin:5px 0;"><h5 id="playerName" class="d-inline-block float-left">Player </h5><h5 class="d-inline-block" style="margin: 0 5px"><span id="nCards" class="badge badge-primary float-right">nCards</span></h5></div><div class="card-body align-top" style="padding: 3px"><img class="card-img-top container" src="https://placedog.net/480/360" alt="card image" width="160" height="90" /> <h5 id="emptycardtitle" class="card-title container"></h5></div><ul id="emptycardul" class="list-group list-group-flush align-bottom"></ul></div>`;
  const cardLi = `<li id="emptyli" class="list-group-item" style="padding: 3px 20px;"  > <div id="emptycategory" class="float-left">level</div> <div id="emptyvalue" class="float-right">8</div> </li>`;

  for (let i = 0; i < game.players.length; i++) {
    if (game.players[i].isHuman === true) {
      if (
        game.players[i].isOut !== true &&
        game.players[i].currentCard !== null
      ) {
        insertCurrentCard(game.players, cardTemplate, cardLi, "#AiCards", i);
      }
    }
  }
  $("li#emptyli")
    .attr("onclick", "turnMid(event)")
    .attr("onmouseover", "$(this).addClass('bg-warning')")
    .attr("onmouseout", "$(this).removeClass('bg-warning')");
}

// Insert players' card as bootstrap card template
function insertCurrentCard(data, cardTemplate, cardLi, locationId, index) {
  let cardTemplateHTML = $.parseHTML(cardTemplate);
  let cardTitle = data[index].currentCard["cardName"]; //"Avenger"
  $("body").data("foo", 52);
  $(cardTemplateHTML).data("playerId", data[index].id);
  $(cardTemplateHTML)
    .find("#emptycardtitle")
    .text(cardTitle);
  $(cardTemplateHTML)
    .find("img")
    .attr(
      "src",
      "http://dcs.gla.ac.uk/~richardm/TopTrumps/" + cardTitle + ".jpg"
    );
  $(cardTemplateHTML)
    .find("#nCards")
    .text(data[index].nCards);
  if (index === 0) {
    $(cardTemplateHTML)
      .find("#playerName")
      .text("Player (you)");
  } else {
    $(cardTemplateHTML)
      .find("#playerName")
      .text("AI Player " + index);
  }

  for (let i = 0; i < data[index].currentCard.categories.length; i++) {
    $(cardTemplateHTML)
      .find("ul")
      .append(cardLi);
  }

  for (let i = 0; i < data[index].currentCard.categories.length; i++) {
    $(cardTemplateHTML)
      .find("#emptycategory:eq(" + i + ")")
      .text(data[index].currentCard.categories[i]);
    $(cardTemplateHTML)
      .find("#emptyvalue:eq(" + i + ")")
      .text(data[index].currentCard[data[index].currentCard.categories[i]]);
  }
  $(locationId).append(cardTemplateHTML);
}

// Show the game information, including the overall winner, the winner of the turn (or draw and # of cards in common pile), and the winning cards.
function updateTurnStatus() {
  const game = getGame();
  let gameStatus = "";

  if (game.isGameOver === true) {
    gameStatus = "The game is over." + "<br />";
    game.players.forEach(element => {
      if (element.isHuman && element.isWinner === true) {
        gameStatus += "Congratulations! You won this game.\n";
      }
      if (!element.isHuman && element.isWinner === true) {
        gameStatus += "AI player " + element.id + " won this game.\n";
      }
    });
  } else {
    if (game.isTie === true) {
      gameStatus = "Draw. Cards will go to common pile.\n";
      gameStatus += "The common pile now has " + game.nCards + " cards\n";
    } else {
      game.players.forEach(element => {
        if (element.isHuman && element.isWinner === true) {
          gameStatus = "You won this turn.\n";
        }
        if (!element.isHuman && element.isWinner === true) {
          gameStatus = "AI player " + element.id + " won this turn.\n";
        }
      });
    }
  }

  const winningCard = getGame().winningCard;
  if (game.isTie !== true) {
    gameStatus +=
      "<br />" + "The winning card is " + winningCard.cardName + ".";
  }
  // Add message to board
  $("#gamestatus").html(gameStatus);
}

// Show which turn it is
function updateTurnId() {
  const game = getGame();
  let gameStatus = "Status of Turn " + game.turnId;
  // Add message to board
  $("#turnid").html(gameStatus);
}

// Show the active player's name
function showActivePlayer() {
  const game = getGame();
  let gameStatus = "";

  game.players.forEach(element => {
    if (element.isHuman && element.isWinner === true) {
      gameStatus = "The active player is you";
    }
    if (!element.isHuman && element.isWinner === true) {
      gameStatus = "The active player is AI player " + element.id;
    }
  });
  // Add message to board
  $("#gamestatus").html(gameStatus);
}

// Show the statistics of the current game which include how many turn each player won
function getGameStatistics() {
  $.get("http://localhost:7777/toptrumps/export2db");

  $("#AiCards").empty();
  const gameStatistics = getGame().gameStatistics;
  // Template of game status board
  let gameStatisticsTemplate = `<div class="row justify-content-center text-center col-8"><div class="card col-12" style="margin: 6px"><div class="card-body"><h2 class="card-title">Game Statistics</h2></div><ul class="list-group list-group-flush"></ul></div><div id="boardbottompanel" class="row col-12 justify-content-center"></div></div>`;

  let gameStatisticsHTML = $.parseHTML(gameStatisticsTemplate);
  // Add data into game status board
  $("#AiCards").append(gameStatisticsHTML);
  let cardLi = `<li class="list-group-item text-left" style="padding: 3px 20px;"><div id="key"class="float-left"></div><div id="value" class="float-right"></div></li>`;
  let cardLiHtml = $.parseHTML(cardLi);

  for (let i = 0; i < gameStatistics.winningRecord.length; i++) {
    cardLiHtml = $.parseHTML(cardLi);
    if (i === 0) {
      $(cardLiHtml).text(
        "You won " + gameStatistics.winningRecord[i] + " turns."
      );
    } else {
      $(cardLiHtml).text(
        "AI player " + i + " won " + gameStatistics.winningRecord[i] + " turns."
      );
    }
    $(gameStatisticsHTML)
      .find("ul")
      .append(cardLiHtml);
  }

  $("AiCards").append(gameStatisticsHTML);

  // Add button to go to game history statistics page (/toptrumps/stats)
  $("#boardbottompanel").append(
    `<button id="gotohistory" type="button" onclick="location.href='/toptrumps'" class="btn btn-primary col-6" style="margin: 0px 3px;font-size: 16px;">Go back to homepage</button>`
  );

  // Adjust layout for game statistics board
  $("#AiCards").addClass("justify-content-center");
}

// Let user choose the category by click the category li tag
function chooseCategory(event) {
  // Get the category chosen
  let category = $(event.target)
    .find("#emptycategory")
    .text();
  if (category === undefined || category === "") {
    category = $(event.target.parentElement)
      .find("#emptycategory")
      .text();
  }
  console.log(category);

  // Send the chosen category to model
  $.get("http://localhost:7777/toptrumps/choosecategory?category=" + category);
}

// Add green frame to winning card
function showWinningCard() {
  const winningCard = getGame().winningCard;
  const chosenCategory = getGame().chosenCategory;
  const winningValue = getGame().winningCard[chosenCategory];
  let cardTitle = "";
  $(".card").each(function(index, element) {
    cardTitle = $(element)
      .find("#emptycardtitle")
      .text();
    if (cardTitle === winningCard.cardName) {
      $(element).addClass("border-success");
    }
  });

  $(".card")
    .find("li")
    .each(function(index, element) {
      if (
        $(element)
          .find("#emptycategory")
          .text() === chosenCategory &&
        $(element)
          .find("#emptyvalue")
          .text() == winningValue
      ) {
        $(element)
          .closest(".card")
          .addClass("border-success");
        $(element)
          .addClass("bg-success")
          .addClass("text-white");
      }
    });
}

// Append the "continue" button
function appendBtnMid() {
  $("#btn-panel").empty();
  let btn = `<button id="continue" type="button" onClick="turnMid(event)" class="btn btn-primary col-8" style="margin: 0px 3px;font-size: 16px;">Continue</button>`;
  $("#btn-panel").append(btn);
}

// Append the "next turn" button
function appendBtnEnd() {
  $("#btn-panel").empty();
  let btn = `<button id="continue" type="button" onClick="turnEnd()" class="btn btn-primary col-8" style="margin: 0px 3px;font-size: 16px;">Next Turn</button>`;
  $("#btn-panel").append(btn);
}

// Append the "skip" button
function appendBtnSkip() {
  const game = getGame();
  if (game.isHumanOut === true) {
    let btn = `<button id="continue" type="button" onClick="skip()" class="btn btn-primary col-8" style="margin: 3px;font-size: 16px;">Skip</button>`;
    $("#btn-panel").append(btn);
  }
}

// The functionality of the skip button, which will automatically play the game until there's a winner
function skip() {
  let isGameOver = getGame().isGameOver;
  while (isGameOver === false) {
    turnMid(null);
    turnEnd();

    isGameOver = getGame().isGameOver;
    turnBeginning();
  }
}

// Run all methods for the beginning of the turn
function turnBeginning() {
  $("#AiCards").empty();

  const game = getGame();
  if (game.isGameOver === true) {
    getGameStatistics();
    updateTurnStatus();
  } else {
    updateTurnId();
    showActivePlayer();
    if (game.isGameOver !== true) {
      appendBtnMid();
    }
    if (game.currentWinner.isHuman === true) {
      getHumanCards();
      $("#continue").remove();
    }
  }
  if (game.isGameOver !== true) {
    appendBtnSkip();
  } else {
    $("#btn-panel").empty();
  }
}

// Run all methods for the middle of the turn
function turnMid(event) {
  let game = getGame();
  if (game.currentWinner.isHuman === true) {
    chooseCategory(event);
  } else {
    $.get("/toptrumps/choosebyai");
  }

  game = getGame();
  getAllCards();
  $.get("/toptrumps/whowon");
  updateNCards();

  showWinningCard();
  if (game.isGameOver !== true) {
    appendBtnEnd();
  } else {
    $("#btn-panel").empty();
  }
  updateTurnStatus();
}

// Run all methods for the end of the turn
function turnEnd() {
  $.get("/toptrumps/endturn");
  turnBeginning();
}

// Show # of cards each player have
function updateNCards() {
  const game = getGame();
  let dataPlayerId = -1;
  let playerId = -1;
  $(".card").each(function(index, element) {
    dataPlayerId = $(element).data("playerId");
    if (dataPlayerId !== undefined) {
      game.players.forEach(player => {
        playerId = player.id;
        if (playerId == dataPlayerId) {
          $(element)
            .find(".badge")
            .text(player.nCards);
        }
      });
    }
  });
}

// Get the game history statistics from database
function getHistoryStats() {
  let historyStats;
  $.ajax({
    url: "/toptrumps/gethistorystats",
    type: "get",
    data: {},
    async: false,
    success: function(data) {
      historyStats = data;
    }
  });
  return historyStats;
}
