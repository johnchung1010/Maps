<!DOCTYPE html>
<html lang="en">
<meta name="description" content="This site allows users to search within a dataset of stars using a variety of search algorithms.">
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- In real-world webapps, css is usually minified and
         concatenated. Here, separate normalize from our code, and
         avoid minification for clarity. -->
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/html5bp.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Space+Mono&display=swap" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
    <script src="js/main.js"></script>
  </head>
  <body>
    <div class="navbar-fixed">
    <nav class="navbar">
      <div class="nav-wrapper">
        <h1 class="brand-logo center title">S T A R S</h1>
        <ul class="left">
          <li>
            <button class="backBtn" onclick="window.location.href='/stars'">HOME</button>
          </li>
        </ul>
        <ul class="right hide-on-med-and-down">
          <li class="navbar_item" style="margin: 30px">By: Naveen Sharma</li>
        </ul>
      </div>
    </nav>
  </div>
    ${content}
     <!-- Again, we're serving up the unminified source for clarity. -->
   <script src="js/jquery-2.1.1.js"></script>
  </body>
  <!-- See http://html5boilerplate.com/ for a good place to start
       dealing with real world issues like old browsers.  -->
</html>