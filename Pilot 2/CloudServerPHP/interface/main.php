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
  <script src="js/load.js"></script>


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
      <?php  if ($_SESSION["userrol"]=="1"):?>
        <li> <a href="#" id="myout"> My outcomes </a> </li>
        <li> <a href="#" id="myaccess"> Who access </a></li>
        <li> <a href="#" id="myrequest"> Request </a></li>
        <li> <a href="#" id="mysmsg"> Messages </a></li>
      <?php else : ?>
          <li> <a href="#" id="myout"> Users outcomes </a> </li>
          <li> <a href="#" id="myrequest"> Request Access</a></li>
    <?php endif; ?>
    </ul>


</div>

<div id="info">
<?php  if ($_SESSION["userrol"]=="1"):?>
    <div id="popup"> </div>
    <div id="outcomes">
      <h2> User outcomes </h2>
      <div id="statistic">
        <h2> Statistics </h2>
        <div id="data"></div>
      </div>
      <p> Check all the outcomes generating by the system.</p>
      <p> Filter by day: <input type="date" id="dateSelect"/> </p>
       <ul>

       </ul>
    </div>

    <div id="accessrequest">
      <h2> Requests Access </h2>

      <p> From other seconday users such doctors, caregivers or relatives to access your information. Also it appear all requests. </p>
      <ul>   </ul>   </div>
    <div id="access">
    <h2> User who have access to your data </h2>
    <p> This list shows the users who have access or have had access to your data. </p>
      <ul>   </ul>   </div>
    <div id="messages">
      <h2>Your messages  </h2>
      <p> These messages are generated automatically  by the server to keep you updated about security issues.
      <ul>   </ul>   </div>
<?php else : ?>

  <div id="outcomes">
    <h2> Users' outcomes </h2>
    <p> Check users' outcomes generating by the system. It just shows the user's outcomes you have be granted access.</p>
     <ul>

     </ul>
  </div>

  <div id="accessrequest">
    <h2> Requests Access </h2>
    <p> Your access requests to users and their state. </p>
      <button id="requestAccess"> Request for access </button>
      <div id="formRequest">
          <p> Request to: <input id="userToRequest" type="text" />
      </div>
    <ul>   </ul>

   </div>
  <?php endif; ?>
</div>
<div id="iteminfo">
</div>

<div id="foot">
<img src="img/success.png" />
<img src="img/mdx.png" />
</div>

</div>



</body>

</html>
