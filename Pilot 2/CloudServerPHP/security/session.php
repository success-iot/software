<?php
session_start();
/**
 * [Session manages the server sesssions using $_SESSION var ]
 */
class Session
{
    const SESSION_STARTED = TRUE;
    const SESSION_NOT_STARTED = FALSE;

    // The state of the session
    private $sessionState = self::SESSION_NOT_STARTED;


    // THE only instance of the class
    private static $instance;


    private function __construct() {}

    /**
    *    Returns THE instance of 'Session'.
    *    The session is automatically initialized if it wasn't.
    *
    *    @return    object
    **/

    public static function getInstance()
    {
        if ( !isset(self::$instance))
        {
            self::$instance = new self;
        }

        self::$instance->startSession();
        $_SESSION['LastActivity'] = $_SERVER['REQUEST_TIME'];
        //$_SESSION['IPAddress'] =$_SERVER['REMOTE_ADDR'];
        return self::$instance;
    }


    /**
    *    (Re)starts the session.
    *
    *    @return    bool    TRUE if the session has been initialized, else FALSE.
    **/

    public function startSession()
    {

        if ( $this->sessionState == self::SESSION_NOT_STARTED )
        {
            $this->sessionState = session_start();
        //    session_regenerate_id(true);
        }

        return $this->sessionState;
    }

    public function isActiveSession(){
    //  if (empty(session_id())){
      if (isset($_SESSION['userid'])) {
      //  echo "User in Session";
        return TRUE;
      }
      return FALSE;
    }

    /**
    *    Stores datas in the session.
    *    Example: $instance->foo = 'bar';
    *
    *    @param    name    Name of the datas.
    *    @param    value    Your datas.
    *    @return    void
    **/

    public function __set( $name , $value )
    {
        $_SESSION[$name] = $value;
    }


    /**
    *    Gets datas from the session.
    *    Example: echo $instance->foo;
    *
    *    @param    name    Name of the datas to get.
    *    @return    mixed    Datas stored in session.
    **/

    public function __get( $name )
    {
        if ( isset($_SESSION[$name]))
        {
            return $_SESSION[$name];
        }
    }


    public function __isset( $name )
    {
        return isset($_SESSION[$name]);
    }


    public function __unset( $name )
    {
        unset( $_SESSION[$name] );
    }


    /**
    *    Destroys the current session.
    *
    *    @return    bool    TRUE is session has been deleted, else FALSE.
    **/

    public function destroy()
    {
        if ( $this->sessionState == self::SESSION_STARTED )
        {
            $this->sessionState = !session_destroy();
          //  unset( $_SESSION );
            session_unset();
        //    session_start();
        //    session_regenerate_id(true);
            return !$this->sessionState;
        }

        return FALSE;
    }

    public function getJson(){
        return  json_encode($_SESSION) ;
    }

    // public function getCookie(){
    //     setcookie("userid", $this->userid, time()+3600);
    //     setcookie("session", $this->SessionKey, time()+3600);
    // }

}

?>
