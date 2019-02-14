
<?php
/**
 * [FILE Manage users logins.
 * Create new @Session for user if not it does no exists. Fill all @Session parameter needs if user gets log in.
 * Retrieve URL parameter @username and @Password and checks if user exists in DB and password is correct for the user.
 * Crontols the number of attempts of login. @TIMEWAITATTEMP increments the time to wait after a wrong log in attempt. More than 3 attemps calls blockIP
 * If user login has success creates a cookie for the user.
 * return 0 or 1 and cookies]
 */
require_once 'security/security.php';
require_once 'class/json.php';
require_once 'dbConection/mysqlConection.php';
/**
 * [$TIMEWAITATTEMP Coeficent to increment waiting time each wrong attempt to log in ]
 * @var integer
 */
 $TIMEWAITATTEMP=0;

$json = new JsonResponse(null, null);

 if($session->isActiveSession()){
    $json->sendResponse($json::$SUCCESS,"User already is login" );
 }

 if (!isset($_SESSION['ipaddress'])) {
   $session->ipaddress = $_SERVER['REMOTE_ADDR'];
 }
 if (isIPBlocked()){
   $json->sendResponse($json::$ERROR,"The current IP is blocked");
 }

 if (!isset($_SESSION['loginAttemp'])) {
     $_SESSION['loginAttemp'] = 0;
 }

 if (empty($_REQUEST['username']) || empty($_REQUEST['password'])) {
      // $json->code=$json::$ERROR;
      // $json->msg="Username and Password required ";
      // echo $json->getJson();
      // exit();
      $json->sendResponse($json::$ERROR,"Username and Password required");
  }
  else
  {
     // Define $username and $password

      $sqlConn = new MySqlConection();
       if(!isset($sqlConn)){
           $json->sendResponse($json::$ERROR,"Internal error connecting DataBase");
       }
        $username= $sqlConn->cleanString($_REQUEST['username']);
        $password= $sqlConn->cleanString($_REQUEST['password']);


        $query = "Select iduser, rol from users where name='" . $username . "' and password='" . $password ."'";
        $sqlConn->query($query);

        if ($sqlConn->getNrows()>0){
           $session->username = $username;
           $row = $sqlConn->getResult()->fetch_row();
           $session->userid = $row[0];
           $session->userrol =$row[1];
           $session->userAgent = substr( $_SERVER['HTTP_USER_AGENT'],0,30);
           $session->SessionKey = uniqid(mt_rand(), true);
           $session->SID = session_id();

           $session->APIrequest=0;    //count the number API request: just 0 or 1 at the moment: sent a msg to user when someone check results.
           $session->LastActivity = $_SERVER['REQUEST_TIME'];
           if(isPrimaryUser()){
               checkIPaddress();
           }
           setcookie("userid",$session->userid);
           $json->addJsonObject("session", $session->getJson());
           $json->sendResponse($json::$SUCCESS,"User login successfully");

           // echo $session->getJson();

        }else{
        //   echo $session->loginAttemp ;
           sleep($session->loginAttemp*$TIMEWAITATTEMP);
           $session->loginAttemp++;
           if($session->loginAttemp=="3"){
              blockIP($username);
              $session->destroy();
           }
           $json->sendResponse($json::$ERROR,"Invalid user or password");

        }
        //$sqlConn->desconn();
    }

?>
