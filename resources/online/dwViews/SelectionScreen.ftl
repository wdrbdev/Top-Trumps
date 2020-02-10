<html>
  <head>
    <!-- Web page title -->
    <title>Top Trumps</title>
    <link
      rel="stylesheet"
      href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
    />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
  </head>

  <body onload="initalize()">
    <!-- Call the initalize method when the page loads -->

    <div class="container">
      <!-- Add your HTML Here -->
      <div id="header" style="padding-bottom: 12px; font-size: 12px;">
        <nav
          class="navbar navbar-expand-lg navbar-light"
          style="background-color: #0d47a1;"
        >
          <a class="navbar-brand  text-white " href="/toptrumps">Top Trumps</a>
        </nav>
      </div>
      <div id="body" class="row justify-content-center container">
        <div class="col-6">
          <h2>Show History Statistics</h2>
          <p>
            Show the statistics from previous game play, including number of
            Games, number of human wins, number of AI wins, average number of
            draws and the longest game.
          </p>
          <div class="row justify-content-center">
            <button
              id="gotogamestatistics"
              type="button"
              onclick="location.href='/toptrumps/stats'"
              class="btn bg-success text-white text-center col-5"
              style="margin: 0px 3px;font-size: 16px;"
            >
              Go to Game Statistics
            </button>
          </div>
        </div>
        <form class="col-6">
          <h2>Start a new game</h2>
          <div class="form-group">
            <label for="exampleFormControlSelect1"
              >Choose number of human player(s)</label
            >
            <select class="form-control" id="nHumanPlayer" disabled>
              <option>1</option>
            </select>
          </div>
          <div class="form-group">
            <label for="exampleFormControlSelect1"
              >Choose number of AI player(s)</label
            >
            <select class="form-control" id="nAiPlayer">
              <option>1</option>
              <option>2</option>
              <option>3</option>
              <option selected>4</option>
              <option>20</option>
            </select>
          </div>
          <div
            id="btn-panel-1"
            class="row  justify-content-center container"
            style="margin: 6px 0;"
          >
            <button
              id="submit"
              type="button"
              class="btn btn-primary col-5"
              style="margin: 0px 3px;font-size: 16px;"
            >
              Submit
            </button>
            <button
              id="start"
              type="button"
              class="btn btn-primary col-5"
              style="margin: 0px 3px;font-size: 16px;"
              disabled="disabled"
            >
              Start
            </button>
          </div>
        </form>
      </div>
    </div>

    <script type="text/javascript">
      // Method that is called on page load
      function initalize() {
        // --------------------------------------------------------------------------
        // You can call other methods you want to run when the page first loads here
        // --------------------------------------------------------------------------
      }

      // -----------------------------------------
      // Add your other Javascript methods Here
      // -----------------------------------------

      // This is a reusable method for creating a CORS request. Do not edit this.
      function createCORSRequest(method, url) {
        var xhr = new XMLHttpRequest();
        if ("withCredentials" in xhr) {
          // Check if the XMLHttpRequest object has a "withCredentials" property.
          // "withCredentials" only exists on XMLHTTPRequest2 objects.
          xhr.open(method, url, true);
        } else if (typeof XDomainRequest != "undefined") {
          // Otherwise, check if XDomainRequest.
          // XDomainRequest only exists in IE, and is IE's way of making CORS requests.
          xhr = new XDomainRequest();
          xhr.open(method, url);
        } else {
          // Otherwise, CORS is not supported by the browser.
          xhr = null;
        }
        return xhr;
      }
    </script>

    <script type="text/javascript">
      "use strict";

      $(document).ready(function() {
        $("#gotogamestatistics").click(function(){
          $.get("http://localhost:7777/toptrumps/setnplayers?nPlayers=5");
          $.get("/toptrumps/newgame", function() {
            location.href = "http://localhost:7777/toptrumps/stats";
          });
        })

        $("button#submit").click(function() {
          $("button#start").removeAttr("disabled");
          let nHumanPlayer = parseInt(
            $("#nHumanPlayer option:selected").text()
          );
          let nAiPlayer = parseInt($("#nAiPlayer option:selected").text());
          let nPlayers = nHumanPlayer + nAiPlayer;
          console.log({
            nHumanPlayer: nHumanPlayer,
            nAiPlayer: nAiPlayer,
            nPlayers: nPlayers
          });
          $.get(
            "http://localhost:7777/toptrumps/setnplayers?nPlayers=" + nPlayers
          );
        });

        $("button#start").click(function() {
          $.get("/toptrumps/newgame", function() {
            location.href = "http://localhost:7777/toptrumps/game";
          });
        });
      });
    </script>

  </body>
</html>
