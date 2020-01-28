<?php
include_once('../security/session.php');

if (!isset($_SESSION["username"])){
     header("Location: login.html");
}
?>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

  <link rel="stylesheet" type="text/css" href="css/main.css">
  <link rel="stylesheet" type="text/css" href="css/pager.css">
  <script src="js/collection.js"></script>
  <script src="js/outcome.js"></script>
  <script src="js/access.js"></script>
  <script src="js/request.js"></script>
  <script src="js/message.js"></script>
  <script src="js/common.js"></script>
  <script src="js/loads.js"></script>


   <!-- <script src="js/model/outcomes.js"></script> -->

   <title>SUCCESS</title>
</head>

<body>

  <div id="content">
  <div id="header">
    <h1>
      SUCCESS User Records
    </h1>
    <div id="userinfo">
        <span id="user"> User: <?php  echo $_SESSION["username"];?> </span>
        <span id="rol">  <?php  if ($_SESSION["userrol"]=="1") {  echo "Primary User";} else{ echo "Secondary User";} ?> </span> </br>
        <span id="ip"> Accessing: <?php  echo $_SESSION["ipaddress"] . "</br>" .  $_SESSION["userAgent"];?> </span> </br>
          <button id="logout"> Log Out </button>

    </div>
    <ul id="menu">
      <li> <a href="#" id="myout"> Users outcomes </a> </li>
      <li> <a href="#" id="myrequest"> Request Access</a></li>
    </ul>
</div>

<div id="info">


  <div id="outcomes">
    <h2> Users' outcomes </h2>
    <div id="statistic">
      <h2> Statistics </h2>
      <div id="data"></div>
    </div>
    <p> Check users' outcomes generating by the system. It just shows the user's outcomes you have be granted access.</p>
    <p> User <select id="selectUser" name="users">    </select> </p>
    <p> Filter by day: <input type="date" id="dateSelect"/> </p>
    <div id="usersName">

    </div>
  </div>

  <div id="accessrequest">
    <h2> Requests Access </h2>
    <p> Your access requests to users and their state. </p>
      <button id="requestAccess"> Request for access </button>
      <div id="formRequest">
          <div class="close"> <a href="#" >X</a></div>
          <p> Request to: <input id="userToRequest" type="text" /> </br>
          <button id="request"> Request </button> </br>
          <p class="msg"></p>
      </div>
    <ul>   </ul>

   </div>

</div>

<?php
// do php stuff
include('page/foot.html');
?>



</div>



</body>

</html>
