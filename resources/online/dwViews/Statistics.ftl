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

    <script type="text/javascript" src="/assets/Controller.js"></script>
  </head>

  <body onload="initalize()">
    <!-- Call the initalize method when the page loads -->

    <div class="container">
      <!-- Add your HTML Here -->
      <div id="root">
        <div id="header" style="padding-bottom: 12px; font-size: 12px;">
          <nav
            class="navbar navbar-expand-lg navbar-light"
            style="background-color: #0d47a1;"
          >
            <a class="navbar-brand  text-white " href="/toptrumps"
              >Top Trumps</a
            >
          </nav>
        </div>

        <div id="body">
          <div class="row justify-content-center">
            <div class="card col-6" style="margin: 6px">
              <div class="card-body">
                <h2 class="card-title">Game Statistics</h2>
              </div>
              <ul class="list-group list-group-flush">
                <li class="list-group-item" style="padding: 3px 20px;">
                  <div class="float-left">Number of Games</div>
                  <div id="NumberOfGames" class="float-right"></div>
                </li>
                <li class="list-group-item" style="padding: 3px 20px;">
                  <div class="float-left">Number of Human Wins</div>
                  <div id="NumberOfHumanWins" class="float-right"></div>
                </li>
                <li class="list-group-item" style="padding: 3px 20px;">
                  <div class="float-left">Number of AI Wins</div>
                  <div id="NumberOfAIWins" class="float-right"></div>
                </li>
                <li class="list-group-item" style="padding: 3px 20px;">
                  <div class="float-left">Number of Draws</div>
                  <div id="NumberOfDraws" class="float-right"></div>
                </li>
                <li class="list-group-item" style="padding: 3px 20px;">
                  <div class="float-left">Longest Game</div>
                  <div id="LongestGame" class="float-right"></div>
                </li>
              </ul>
            </div>
          </div>
          <div class="row justify-content-center">
            <button
              id="backtohomepage"
              type="button"
              onclick="location.href='/toptrumps'"
              class="btn btn-primary col-3"
              style="margin: 0px 3px;font-size: 16px;"
            >
              Back to Homepage
            </button>
          </div>
        </div>
      </div>
    </div>

    <script type="text/javascript">
      // Method that is called on page load
      function initalize() {
        // --------------------------------------------------------------------------
        // You can call other methods you want to run when the page first loads here
        // --------------------------------------------------------------------------
        // For example, lets call our sample methods
        // Initialization
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
    <Script type="text/javascript">
      $.get("/toptrumps/newgame");
      $.get("http://localhost:7777/toptrumps/setnplayers?nPlayers=5");
      $(document).ready(function() { const historyStatistics =
      getGame().historyStatistics; console.log(historyStatistics);
      $("#NumberOfGames").text(historyStatistics.sumNGame);
      $("#NumberOfHumanWins").text(historyStatistics.sumNHumanWon);
      $("#NumberOfAIWins").text(historyStatistics.sumAiWon);
      $("#NumberOfDraws").text(historyStatistics.sumNTie);
      $("#LongestGame").text(historyStatistics.nLongestGame); });
    </Script>
  </body>
</html>
