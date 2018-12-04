<?php
/**
 * [File implements functions related with security]
 *
 */
include_once('session.php');
require_once('dbConection/mysqlConection.php');
//error_reporting(E_ERROR | E_PARSE);
/**
 * [$session allow manage securtiy activities and checks ]
 * @var [Session]
 */
$session = Session::getInstance();

/**
 * [header function prepare the head HTTML response following general security recomendations]
 */
header("Expires: " . gmdate('D, d M Y H:i:s \G\M\T', time() + (60 * 60)));
header("Last-Modified: " . gmdate("D, d M Y H:i:s") . " GMT");
header("Cache-Control: no-store, no-cache, must-revalidate");
header("Cache-Control: post-check=0, pre-check=0", false);
header("Pragma: no-cache");

//Security General functions <<Docblockr:Decorate>>

/**
 * [checkUserAccess check if user has started a session at server and therefore, cheking the session, to know wheter user go on log or not. ]
 * @param  [int] $id [Parameter from url or user cookie Userid ]
 * @return [Boolean]     [User session started]
 */
function checkUserAccess($id){
      if ($id==$_SESSION["userid"]){
        return TRUE;
      }
      return FALSE;
}
/**
 * [isPrimaryUser check whether user is a Primary user checking the rol from session, previusly retrived from DB users table ]
 * @return boolean [User is primary user ROL==1]
 */
function isPrimaryUser(){
    global $session;
    return $session->userrol==1;
}
/**
 * [isSecondaryUser check rol over whether user is secondary user]
 * @return boolean [User is secondary user ROL!=1]
 */
function isSecondaryUser(){
   global $session;
   return $session->userrol>1;
}


/**
 * [checkIPaddress Procedure Check wheter the user ip device is the same each login. Whether is the same does nothing, but does not created a @Message in DB for user ]
 * @return [VOID] []
 */
function checkIPaddress(){
   include_once("resources/messages.php");
    global $session;
    $connection= new MySqlConection();

    $query = "select * from ipaddress where iduser=" . $session->userid; // . " and address='" . $session->ipaddress ."'";
    $connection->query($query);
  //Check if it is the firts time the user log into system, no mesagge is created.
  //  if ($connection->getResult()) {
      if($connection->getAffectedRow()>0){
        while ($row = $connection->getResult()->fetch_assoc()) {
          if ($row["address"]==$session->ipaddress){ //if exist -> ok
              return; //IP EXISTS
          }
        }
      }
//    }
//    echo "ROW AFFeCTEd " . $connection->getAffectedRow();
    if($connection->getAffectedRow()>0){

      $msg = new Messages();
      $msg->createMessage($session->userid, Messages::$MSGType["IP_ACCESS"], $session->ipaddress  . ":" . $session->userAgent);
    }

    $query="INSERT INTO ipaddress VALUES (" .  $session->userid . ",'" . $session->ipaddress ."');";
    $connection->query($query);


}

/**
 * [blockIP (Procedure) adds the current user IP to DB ipblocked table which is checkid each log in, checks whether user trying to log in exists thereby it creates a @Message for him]
 * @param  [string] $username [The url parameter username.]
 * @return [VOID]             [description]
 */
function blockIP($username){
  include_once("resources/messages.php");
  global $session;
//  echo "Insert IP in block ip ************";
  $connection= new MySqlConection();
  $query = "Insert into ipblocked VALUES ('" . $session->ipaddress . "')";
  $connection->query($query);
//  echo "Inserted IP $query ************ ";
  $query = "Select iduser from users where name='" . $username ."'";
  $connection->query($query);
//  echo "check user in db $query ************ ";
  if($connection->getAffectedRow()>0){
  //  echo "create msg for user in db ************";

      $row = $connection->getResult()->fetch_assoc();
    //  var_dump($row["iduser"]);
      $msg = new Messages();
    //  $session->userid=;
      $msg->createMessage($row["iduser"], Messages::$MSGType["ACCESS_ATTEMPTS"], $session->ipaddress . " Blocked. Please contact system admin");
    //  unset($session->userid);
    //  echo " msg created in db " .   $session->userid . "************" ;
  }
}

/**
 * [isIPBlocked checks if the current access user IP (getting from $session) is in DB ipblocked table]
 * @return boolean [Exists IP in ipblocked]
 */
function isIPBlocked(){
      global $session;
  //    echo "checking IP is blocked ************";
      $connection= new MySqlConection();
      $query = "select ip from ipblocked where ip='". $session->ipaddress . "'";
      $connection->query($query);
//        echo "Ip state $query -> " .  $connection->getAffectedRow() . " ************ ";
      return $connection->getAffectedRow()>0;
}

/**
 * [printSession Prints the current user session ]
 * @return [VOID] [var_dump of current user $session object]
 */
function printSession(){
   var_dump($_SESSION);
}

?>
