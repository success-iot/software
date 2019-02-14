<?php

require "resourceAbstract.php";
require "access.php";
/**
 * [Accessrequest manages the CRUD methods for Accessrequest resource of DB, which are the request from users to access other primary users resources]
 */
  class Accessrequest extends Resource
  {
    /**
     * [$table references the BD table name of resource]
     * @var [string]
     */
    public $table = "accessrequest";
    /**
     * [$resourcekey the primary key of the db resource table]
     * @var [string]
     */
    public $resourcekey = 'idrequest';

    /**
     * [GET Creates the correct query to retrieve the records from DB. ]
     * @param [type] $key [Retrieve just the $key record comparing with $resourcekey var CLASS]
     */
    public function GET($key){
      //var_dump($session);
      if(isPrimaryUser()){
        $this->setUserkey('id1user');
      }
      $this->query = "select r.*, u.name from " .  $this->table .  " r JOIN users u on u.iduser=r.iduser  WHERE r." . $this->userKey  . "=" . $this->session->userid;

  //    echo $this->query;
      if(isset($key)){
            $this->query = $this->query  . " and " . $this->resourcekey  . "=" . $key ;
      }
      return parent::GET(null);
    }
    /**
     * [POST Create the apropiate query to create a new element in DB of this resource.
     * NOTE: a primary user can not create this sort of resoruce]
     * @param [String] $input [Parameters needed to create a new DB record]
     */
    public function POST($input){
        //for security a first user can't create a request
        if(isPrimaryUser()){
          return 0;
        }

        $namePrimary = $this->getINPUTValue($input, "id1user");
        if($namePrimary==null){
           return 0;
        }

        $idPrimary = $this->getUserKey($namePrimary);
        if($idPrimary==null){
           return 0;
        }

        $this->query =  "insert into " . $this->table . " (iduser,id1user) VALUES (" . $this->session->userid . "," . $idPrimary .")" ;
      //  echo $this->query;
        return parent::POST(null,null);
    }

    /**
     * [PUT modifies the resource $key with the $inputs values, thus delete or create a Access record whether neccesary.
     * NOTE: just primary users can run this methods]
     * @param [interface] $key   [id of db resource table to upgrade]
     * @param [JSON] $input [fields to modify]
     */
    public function PUT($key, $input){
        //Only the user affected whom reference the request can update the request
        if(!isPrimaryUser()){
          return 0;
        }
        /* Lookup for 1st user (affected user)  */
        $this->setUserkey('id1user');
        $set = $this->getPUTValues($input);
        //Set user id and resource key
        $this->query =  "update " . $this->table . " SET " . $set . " where " . $this->resourcekey . "=" .$key . " and " . $this->userKey  . "=" . $this->session->userid;
        return parent::PUT(null,null);

    }
    /**
     * [DELETE removes requests $key form DB.
     * NOTE: just user who creates it can delete.]
     * @param [type] $key [description]
     */
    public function DELETE($key){
      //Only the user who created can remove

        if(isPrimaryUser()){
            $this->setUserkey('id1user'); //change the column to find
        }
        $this->query =  "delete from " .  $this->table . " where " . $this->userKey ."=". $this->session->userid;
        if(isset($key)){
          $this->query = $this->query . " and " . $this->resourcekey . "=" . $key;
        }
        //only 2nd, 3rd.. can create request to access
        return parent::DELETE(null);
    }
}

?>
