<?php
require_once "resourceAbstract.php";
/**
 * [Messages manages the CRUD methods for Messages resource of DB. Which represents security messages for users.
 * Also it offers funcitons to allow kernel manage the messeges.]
 */
class Messages extends Resource
  {
    /**
     * [$table references the BD table name of resource]
     * @var [string]
     */
    public $table = "messages";
    /**
     * [$resourcekey the primary key of the db resource table]
     * @var [string]
     */
    public $resourcekey = "idmessage";
    /**
     * [$MSGType  Constant with the type of msg and integer asociate]
     * @var [Array]
     */
    public static $MSGType = Array("IP_ACCESS"=>0,
                                  "ACCESS_ATTEMPTS"=>1,
                                  "DATA_READ"=>2);

    /**
    * [GET Creates the correct query to retrieve the records. ]
    */
    function GET($key){
      if(!isPrimaryUser()){
              return 0;
       }
       $this->query = "select * from " .  $this->table .  " WHERE " . $this->userKey  . "=" . $this->session->userid . " and readed=0";
       if(isset($key)){
         $this->query = $this->query  . " and " . $this->resourcekey  . "=" . $key ;
       }
       return parent::GET(null);
   }

   /**
    * [POST creates a new record of this resource in DB
    *  NOTE:Users cannot create msg.]
    */
    function POST($input){
      return parent::POST(null);
    }

    /**
     * [PUT modifies the record $key with value.
     * NOTE: Users just can change READED=TRUE]
     */
    function PUT($key, $input){
         //Only the user affected whom reference the request can update the request
         if(!isPrimaryUser()){
            return 0;
         }

        //JUST leave mark message as read, no other modification are allowed
          $this->query =  "update " . $this->table . " SET readed=1 where " . $this->resourcekey . "=" .$key . " and " . $this->userKey  . "=" . $this->session->userid;
          echo $this->query;
          return parent::PUT(null,null);
     }

     /**
      * [DELETE not is allow for this resource]
      */
    public function DELETE($key){
        return 0;
    }

/** idUser is ommited for security reasons, getted from session*/
  /**
   * [createMessage creates a new record for a user]
   * @param  [String] $iduser [User id who will read/be affected  the msg]
   * @param  [$int] $type   [Type of message]
   * @param  [$string] $body   [Text adds info to message]
   * @return [JSON]         [Not be to be read.]
   */
   public function createMessage($iduser, $type, $body){
     $input = array("iduser"=>$iduser, "type" => $type, "body" => $body);
     $set = $this->getPOSTValues($input);
     $this->query =  "insert into " .  $this->table . " " . $set ;

     return $this->POST($input);

   }
   /**
    * [getPOSTValues Overwrite parent function allowing add iduser.]
    * @param  [JSON] $input [pair name:values to be saved in DB]
    * @return [String]        [Tail of insert query for the values from input]
    */
   function getPOSTValues($input){
           $set='(';
           $values = '(';
           foreach( $input as $key => $value){
             //discard the userid, for securtiy it set with session userid
                   $set = $set .  $key . ",";
                   $values = $values . "'" . $value . "',";
               }
           $set= rtrim($set,",") . ')';
           $values= rtrim($values,",") . ')';

           return $set . ' VALUES ' . $values;

   }

   /**
    * [broadCastRead creates a new record for each user whose data are accesses for current user]
    * @return [VOID] [description]
    */
   function broadCastRead(){
      $iduserAccess="Select iduser from access where id2user='". $this->session->userid . "' and value=1";
      echo $iduserAccess;
      $ids = array();
      $this->connection->query($iduserAccess);
      while ($row=$this->connection->getResult()->fetch_assoc()){
          array_push($ids, $row['iduser']);
      }
      foreach($ids as $id){
      //  echo "id $id *";
        $this->createMessage($id, 3, $this->session->username);
      }
   }
}

?>
