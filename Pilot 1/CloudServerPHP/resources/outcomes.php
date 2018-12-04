<?php
require "resourceAbstract.php";
require_once "messages.php";
  /**
   * [Outcomes manages the CRUD methods for Outcomes resource of DB. Which represents the results from users applications (Mreasoner, mobile survey/state,etc)]
   */
  class Outcomes extends Resource
  {

    /**
     * [$table references the BD table name of resource]
     * @var [string]
     */
    public $table = "outcomes";
    /**
     * [$resourcekey the primary key of the db resource table]
     * @var [string]
     */
    public $resourcekey = 'idoutcome';

    /**
     * [GET Creates the correct query to retrieve the records. ]
     * @param [type] $key [Retrieve just the $key record comparing with $resourcekey var CLASS]
     */
    function GET($key){

        if(isPrimaryUser()){
          $this->query = "select * from " .  $this->table .  " WHERE " . $this->userKey  . "=" . $this->session->userid;
          if(isset($key)){
            $this->query = $this->query  . " and " .  $this->resourcekey . "=" . $key ;
          }
        }else{
            $this->query =  "select " . $this->table . ".* from " .  $this->table .  " join access on access.iduser=" .  $this->table . ".iduser Where access.id2user=" . $this->session->userid . " and access.value=1";
            if(isset($key)){
              $this->query = $this->query  . " and " . $this->resourcekey  . "=" . $key ;
            }

      //      echo $this->query;
            if($this->session->APIrequest==0){
              $msg= new Messages();
              $msg->broadCastRead();
            }
            $this->session->APIrequest++;
        }

    //  echo $this->query;
      return parent::GET($key);

    }

    /**
     * [POST Create the apropiate query to create a new element in DB of this resource ]
     * @param [String] $input [Parameters needed to create a new DB record]
     */
    function POST($input){
       /* get sql string of pairs 'column=value' */
        $set = $this->getPOSTValues($input);
        $this->query =  "insert into " .  $this->table . " " . $set ;
        return parent::POST(null);
    }

    /**
     * [PUT Not Outcome resource records can be modified.]
     */
    function PUT($key, $input){
        return "0";
    }
    /**
     * [DELETE erases the DB resource records.]
     * @param [String] $key [Erase just the record $key]
     */
    function DELETE($key){
      $this->query =  "delete from " .  $this->table . " where " . $this->userKey ."=". $this->session->userid;
      if(isset($key)){
        $this->query = $this->query . " and " . $this->resourcekey . "=" . $key;
      }

      return parent::DELETE(null);
    }



}

?>
