<?php

require_once "resourceAbstract.php";
/**
 * [Access manages the CRUD methods for access resource of DB.
 *  This resource represents who users (secondary or so on) have access to other primary users outcomes.]
 */
  class Access extends Resource
  {
    /**
     * [$table references the BD table name of resource]
     * @var [string]
     */
     protected $table = "access";
     /**
      * [$resourcekey the primary key of the db resource table]
      * @var [string]
      */
     protected $resourcekey = 'idaccess';

     /**
      * [GET Creates the correct query to retrieve the records. ]
      * @param [type] $key [Retrieve just the $key record comparing with $resourcekey var CLASS]
      */
     public function GET($key){

        if(isPrimaryUser()){
          $this->query = "select r.*, u.name from " . $this->table . " r JOIN users u on u.iduser=r.id2user  WHERE r." . $this->userKey . "=" . $this->session->userid;
          if(isset($key)){
            $this->query = $this->query  . " and " . $this->resourcekey  . "=" . $key ;
          }
    //      echo $this->query;
          return parent::GET(null);
        }
        return 0;
     }
     /**
      * [POST creates or updated if exists new records  whether the user is primary.]
      */
     public function POST($input){
       if(isPrimaryUser()){
        // $value = $input["value"];
         $value = $this->connection->cleanString($input["value"]);
         $set=$this->getPOSTValues($input);
         if($value!=null){
           $this->query = " INSERT INTO " .  $this->table .  $set . " ON DUPLICATE KEY UPDATE  value='" . $value . "'";
           return parent::POST(null);
         }

       }
       return 0;
     }
     /**
      * [PUT Just Primary users can modify]
      */
     public function PUT($key, $input){
       if(isPrimaryUser()){
         $set=$this->getPUTValues($input);
         $this->query = "Update " . $this->table . " SET " . $set . " where " . $this->userKey . "=" . $this->session->userid . " and " . $this->resourcekey . "=" . $key;
         return  parent::PUT(null,null);
       }
        return 0;
     }
     /**
      * [DELETE Not access resource records can be deleted. See deleteRow]
      */
     public function DELETE($key){
        return 0;
     }


}

?>
