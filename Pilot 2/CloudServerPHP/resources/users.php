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
        }else{
          if(isset($key)){
            $this->query = "select name from access a left join " . $this->table . " u on a.iduser=u.iduser where " . $key . "=u.iduser and a.value=1";
            return parent::GET(null);
          }else{
            return 0;
          }
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

     public function getName2UserAccess($key){
        $query = "select u.name as name  from access a left join users u on a.id2user=u.iduser where a.idaccess=$key";
        $this->connection->query($query);

        if(!$this->connection->getResult()){
          return "0";
        }
        $row=$this->connection->getResult()->fetch_assoc();
        return $row['name'];

     }

}

?>
