<?php

$user = $_GET['user'];
$pw = $_GET['pw'];

$query = "Insert into users (name, password) values ($user, $pw)";

echo $query;

  $ip='localhost';
  $user='root';
  $pass='1234';
  $dbname='server_cloud';

$connection = new mysqli($ip, $user, $pass, $dbname);
$connection->query($query);

?>
