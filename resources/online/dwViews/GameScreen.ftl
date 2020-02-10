<html>
  <head>
    <!-- Web page title -->
    <title>Top Trumps</title>

    <!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) -->

    <!-- Optional Styling of the Website, for the demo I used Bootstrap (see https://getbootstrap.com/docs/4.0/getting-started/introduction/) -->

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
        <div id="body" class="row">
          <div id="left-panel" class="col-4">
            <div id="top-left-panel">
              <div class="card">
                <div class="card-header">
                  <strong id="turnid">Turn Status</strong>
                </div>
                <div class="card-body">
                  <p id="gamestatus" class="card-text"></p>
                  <div id="btn-panel" class="row  justify-content-center"></div>
                </div>
              </div>
            </div>
            <div
              id="testrow"
              class="row justify-content-center"
              style="margin-top: 12px;"
            ></div>
          </div>
          <div id="right-panel" class="col-8">
            <div id="AiCards" class="row"><h2></h2></div>
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
      $(document).ready(function() {
        $.ajax({
          type: "get", url: "/toptrumps/getgame",
          success: function (data, text) {
            turnBeginning();
          },
          error: function (request, status, error) {
              alert("Please start from the homepage.");
              location.href= "/toptrumps";
          }
        });
      });
    </script>
  </body>
</html>
