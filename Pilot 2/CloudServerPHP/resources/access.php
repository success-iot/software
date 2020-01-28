<?php

require_once "resourceAbstract.php";
require_once "messages.php";
require_once "users.php";
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
          return parent::GET(null);
        }
        return 0;
     }
     /**
      * [POST creates or updated if exists new records  whether the user is primary.]
      */
     public function POST($input){
         //Just use id2user to create. By default 'value' is 0.
         $id2user = $this->connection->cleanString($input["id2user"]);
         if (!isset($id2user)){
           return 0;
         }
         if(isPrimaryUser() & $this->checkRequestUser($id2user)){
           $set=$this->getPOSTValues($input);
           $this->query = " INSERT INTO " .  $this->table .  $set; //. " ON DUPLICATE KEY UPDATE  value='" . $value . "'";
           return parent::POST(null);
         }
         return 0;
     }
     /**
      * [PUT Just Primary users can modify just the value]
      */
     public function PUT($key, $input){
       if(isPrimaryUser()){
         $value = $input['value'];
         if($value>1 || $value<0){
           return 0;
         }

         $this->query = "Update " . $this->table . " SET value=$value where " . $this->userKey . "=" . $this->session->userid . " and " . $this->resourcekey . "=" . $key;

         $user = new Users();
         $name2user = $user->getName2UserAccess($key);
         if($value==1){
            $msgString = "Access granted to user " . $name2user ;
         }else{
            $msgString = "Access revoked to user " . $name2user;
         }

         $msg= new Messages();
         $msg->createMessage($this->session->userid, 3, $msgString);
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
