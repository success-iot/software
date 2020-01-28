<?php
/**
 * [MySqlConection provides functions to interacts with DB]
 */
class  MySqlConection {

    /**
     * [$connection keeps the current object connection]
     * @var [mysqli]
     */
    private $connection = null;
    /**
     * [$result keeps the last query returned results]
     * @var [mysqli results]
     */
    private $result = null;
    /**
     * [$error keeps the last query returned error]
     * @var [type]
     */
    private $error = null;
    /**
     * [$nrows keeps number of rows affected/returned by the last query]
     * @var [type]
     */
    private $nrows = null;
    /**
     * [$errorMsg no used]
     * @var [type]
     */
    private $errorMsg = null;

    /**
     * [CONTS connection parameters: $ip, $user, $pass, $dbname]
     * @var [string]
     */
    private  $ip='localhost';
    private  $user='root';
    private  $pass='1234';
    private  $dbname='server_cloud';

    /**
     * [__construct stablishes a connection with DB]
     */
    function __construct(){

      $this->connection = new mysqli($this->ip, $this->user, $this->pass, $this->dbname);
        if ($this->connection->connect_errno){
          $this->error = $this->connection->connect_errno;
          $this->errorMsg = $this->connection->connect_error;
        }
      //  mysqli_set_charset($this->connection,'utf8');
        $this->connection->set_charset("utf8");
    }

    /**
     * [__destruct frees the connection and memory resources]
     */
    function __destruct(){
    		if(!$this->connection){
    			//mysqli_close($this->connection);
          if(isset($this->result)){
            $this->result->close();
          }
          $this->connection->close();
    		}
    }

    /**
     * [getConnection return the mysqli object]
     * @return [mysqli] [myslqi connection object]
     */
    function getConnection(){
        return $this->connection;
    }

    /**
     * [getResult from last query]
     * @return [mysqli results] [$this->result]
     */
    function getResult(){
        return $this->result;
    }
    /**
     * [getAffectedRow gets the row affected/returned by the last query]
     * @return [int] [$this->nrows]
     */
    function getAffectedRow(){
      return $this->nrows;
    }
    /**
     * [getNrows gets $this->affected_rows (deprecated) ]
     * @return [type] [description]
     */
    function getNrows(){
        return $this->connection->affected_rows;
    }
    /**
     * [query launchs a query statement in DB. Set $this->nrows nad $this->result]
     * @param  [string] $query [mysql query]
     * @return [type]        [description]
     */
    function query($query){
      //  $this->result = mysqli_query($this->connection, $query);
        if(is_null($query)){
          $this->result=0;
          $this->nrows=0;
          return null;
        }
        $type= strtoupper(substr($query, 0, 3 ));
  //      echo " ************** " . $type . " ****************** ";
        $this->result = $this->connection->query($query);
        if(!$this->result){
            $this->error = "Empty query";
            return;

        }
        switch ($type) {
          case 'SEL':
            $this->nrows = $this->result->num_rows;
            break;
          // case 'UPD':
          //     break;
           case 'INS':
               $this->nrows =  $this->connection->affected_rows;
               break;
          // case 'DEL':
          //     $this->nrows = $this->result->affected_rows;
          //       break;
          default:
           $this->nrows = 0;
        //    $this->nrows = $this->result->affected_rows;
            break;
        }

    }

    /**
     * [desconn not used]
     * @return [VOID] []
     */
    function desconn(){
		    //mysqli_close($this->connection);
        //$this->connection->close();
        // $this.__destruct();
    }

    // function getNameIdColumn($table){
    //         $query = "SHOW COLUMNS FROM $table";
    //       //  $query = "select TABLE_NAME from server_cloud.INFORMATION_SCHEMA.COLUMNS  WHERE TABLE_NAME = '" . $table . "'";
    //         $this->query($query);
    //         $row =  $this->getResult()->fetch_assoc();
    //         return $row['Field'];
    // }

    /**
     * [error gets the last query error if it was]
     * @return [string] [$this->error]
     */
    function error(){
        return $this->error;
    }

    /**
     * [existTable checks if $table exists in DB]
     * @param  [string] $table [name of table to check]
     * @return [int]          [number of tables with the name=$table (1 or 0)]
     */
    function existTable($table){
        $query = "SELECT * FROM information_schema.tables  WHERE table_name = '$table'  LIMIT 1";
        $this->query($query);
      //  return mysqli_num_rows($this->getResult());
        return $this->nrows;
    }

    /**
     * [cleanString description]
     * @param  [type] $string [description]
     * @return [type]         [description]
     */
    function cleanString($string){
        return $this->connection->real_escape_string($string);
    }

}
?>
