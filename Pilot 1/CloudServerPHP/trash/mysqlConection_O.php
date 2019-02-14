<?php


class  MySqlConection {

    private $connection = null; //mysql
    private $result = null;
    private $error = null;
    private $nrows = null;
    private $errorMsg = null;

    private $ip='localhost';
    private $user='root';
    private $pass='1234';
    private $dbname='server_cloud';

    function __construct(){
      //echo $this->ip .'/'. $this->user . '/' .  $this->pass . '/' . $this->dbname;
        $this->connection = new mysqli($this->ip, $this->user, $this->pass, $this->dbname);
        if ($this->connection->connect_errno){
          $this->error = $this->connection->connect_errno;
          $this->errorMsg = $this->connection->connect_error;
        }
         $this->connection->set_charset("utf8");
    }


    function __destruct(){
    		if(!$this->connection){
    			   $this->connection->close();
    		}
    }

    function getConnection(){
        return $this->connection;
    }
    function getResult(){
        return $this->result;
    }


    function query($query){
        $this->resutl = $this->connection->query($query);
        if(!$this->result){
            $this->error = "Empty query";
            return;
        }

        $this->nrows = $this->connection->affected_rows;
    }

    function desconn(){
		    $this->connection->close();

    }

    function getNameIdColumn($table){
            $query = "SHOW COLUMNS FROM $table";
          //  $query = "select TABLE_NAME from server_cloud.INFORMATION_SCHEMA.COLUMNS  WHERE TABLE_NAME = '" . $table . "'";
            $this->query($query);
            $row = mysqli_fetch_assoc($this->getResult());
            return $row['Field'];
    }

    function error(){
            return $this->error;
    }

    function getNrows(){
        return $this->nrows;
    }

    function existTable($table){
        $query = "SELECT * FROM information_schema.tables  WHERE table_name = '$table'  LIMIT 1";
        $this->query($query);
        return mysqli_num_rows($this->getResult());
    }




}
?>
