<?php

require_once "resourceAbstract.php";
/**
 * [Access manages the CRUD methods for access resource of DB.
 *  This resource represents who users (secondary or so on) have access to other primary users outcomes.]
 */
  class Users extends Resource
  {
    /**
     * [$table references the BD table name of resource]
     * @var [string]
     */
     protected $table = "users";
     /**
      * [$resourcekey the primary key of the db resource table]
      * @var [string]
      */
     protected $resourcekey = 'iduser';

     /**
      * [GET Creates the correct query to retrieve the records. ]
      * @param [type] $key [Retrieve just the $key record comparing with $resourcekey var CLASS]
      */
     public function GET($key){

        if(isPrimaryUser()){

          if(isset($key)){
            $this->query = "select name from " . $this->table . " where $this->resourcekey=$key";
        //    echo $this->query;
          }else{
            return 0;
          }
          return parent::GET(null);
        }
        return 0;
     }
     /**
      * [POST creates or updated if exists new records  whether the user is primary.]
      */
     public function POST($input){
        return 0;
     }
     /**
      * [PUT Just Primary users can modify]
      */
     public function PUT($key, $input){
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
