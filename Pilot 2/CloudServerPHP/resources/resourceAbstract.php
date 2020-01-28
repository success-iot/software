<?php
require_once 'security/security.php';
require_once 'class/json.php';
//require_once 'class/log.php';
require_once 'dbConection/mysqlConection.php';

/**
 * [Model Resource relate DB and PHP Object.
 * Implements CRUD Methods necesaries to REST API. ]
 *
 */
abstract class Resource
{

  /**
   * [PrimaryKey to searh element in table. As it is center in security, default is iduser as the one to CRUD in DB]
   * @var [String]
   */
  protected $userKey="iduser";
  /**
   * [Query string used to run in DB]
   * @var [String]
   */
  protected $query;
  /**
   * [Connection to DB Mysql]
   * @var [MySqlConection]
   */
  protected $connection;

  /**
   * [__construct create a new connection for resource. Global session is used in all methods for secuerity reason. ]
   */
  function __construct(){

     global $session;
     $this->connection= new MySqlConection();
     $this->session=$session;
     if (!$this->connection->existTable($this->table)){
         return "Error 404: Resource db $this->table not found";
     }
  }

  /**
   * [__destruct ensures the DB connection is closed]
   */
  function __destruct(){
      $this->connection->desconn();
  //    $this->session->destroy();
  }
  /**
   * [GET retrieve the rows from a resource. Launch a query from child object and generate a JSON to response.
   * Childs use $key parameter to select one row.]
   *  @return [JSON]          [state operation]
   */
  protected function GET($key){
    //  echo $this->query;
       $this->connection->query($this->query);
       if (!$this->connection->getResult()) {
          return 0;
       }

       /** Generate a JSON response */
        $json='';
        if (!$key) $json='[';
        for ($i=0;$i<$this->connection->getNrows();$i++) {
          $json = $json . ($i>0?',':'').json_encode($this->connection->getResult()->fetch_object());
        }
        if (!$key) $json=$json . ']';
        $this->connection->desconn();
    //    echo $json;
        return $json;
  }


 /**
  * [PUT Implements PUT method to update a resource.]
  * @return [JSON]          [state operation]
  */
  protected  function PUT($key, $input){
          /* If is not resource id in URI -> Error */
          //WE HAVE TO GET A QUERY TO GET THE USER  OF RESOURCE AND CHECK in the class OUTCOMES
        /* generate the string query of pairs 'field=value' */
    //    echo $this->query;
        $this->connection->query($this->query);
        //$connection->update($table, $fields, $values)
        if (!$this->connection->getResult()) {
          return 0;
        }

        $nrow = $this->connection->getNrows();
        return $nrow;
   }

  /**
   * [POST Implements POST method to create new elements. ]
   *  @return [JSON]          [state operation]
   */
   protected function POST($input){
      // echo $this->query;
       $this->connection->query($this->query);
       if (!$this->connection->getResult()) {
         return 0;
       }
       $nrow = $this->connection->getNrows();
       $this->connection->desconn();
       return $nrow;
  }

  /**
  * Implements DELETE method to erase elements.
  *  @return [JSON]          [state operation]
  */
  protected function DELETE($key){
    //  echo $this->query;
      $this->connection->query($this->query);
      //$connection->update($table, $fields, $values)
      if (!$this->connection->getResult()) {
          return 0;
      }
      $nrow = $this->connection->getNrows();
      $this->connection->desconn();
      return $nrow;
    }

    /**
     * [getPUTValues creates an appropaite tail mysql query string to update database record.
     *    Each child class must to redefine this method and filter the fields can be modified
     *    However alwais discard the userid, for securtiy it set with session userid and the reiurce key thats is stract from URL.]
     * @param  [JSON] $input [fields:values to update database]
     * @return [string]        [tail of update query string pairs of fields-values]
     */
    protected function getPUTValues($input){
            $set='';
            $values = '';
            foreach( $input as $key => $value){
              if($key!=$this->userKey && $key!=$this->resourcekey){
                //discard the userid for securtiy it set with sessio userid
                $set = $set .  $this->connection->cleanString($key) . "='" . $this->connection->cleanString($value) . "',";
              }

            }
            $set= rtrim($set,",");
            return $set;
    }

    /**
    * Extract the field from input POST.
    * Extract the field from input POST
    * Each 'son' class must to redefine this method and filter the fields can be created.
    * However alwais discard the userid, for securtiy it set with session userid
    **/
    /**
     * [getPOSTValues creates an appropaite tail mysql query string to insert database record.]
     * @param  [JSON] $input [field:values for new record]
     * @return [string]        [tail of insert query string, pairs of fields-values]
     */
    protected function getPOSTValues($input){
            //userid from session
            $set='(' . $this->userKey . ",";
            $values = '(' . $this->session->userid . ",";
            foreach( $input as $key => $value){
              //discard the userid, for securtiy it set with session userid
                  if($key!=$this->userKey){
                    $set = $set .  $this->connection->cleanString($key)  . ",";
                    $values = $values . "'" . $this->connection->cleanString($value)  . "',";
                  }
              }
            $set= rtrim($set,",") . ')';
            $values= rtrim($values,",") . ')';

            return $set . ' VALUES ' . $values;

    }

    /**
     * [getINPUTValue find into JSON $input the value associated to key $field ]
     * @param  [JSON] $input [Input from request]
     * @param  [string] $field [name of the key of JSON to search]
     * @return [string/int]        [Value assoicate with key $field found in $input or null if does not exist]
     */
    protected function getINPUTValue($input, $field){
        foreach( $input as $key => $value){
        //discard the userid, for securtiy it set with session userid
            if($key==$field){
              return  $this->connection->cleanString($value);
            }
        }
        return null;
    }

    /**
     * [setUserkey changes $this->userkey for internal logical aims]
     * @param [string] $new [new name of id to be used to look for in current object resource]
     */
    protected function setUserkey($new){
      $this->userKey=$new;
    }

    /**
     * [getUserKey retrieve the userid for a user name.]
     * @param [string] $name [name of user whose id is wanted]
     * @return [string/int] Id of user $name
     */
    protected function getUserKey($name){
      $q="Select iduser from users where name='$name'";

      $this->connection->query($q);
      if ($this->connection->getNrows()>0){

        $row = $this->connection->getResult()->fetch_row();
        return $row[0];
      }else{
        return null;
      }
    }

    /**
     * [getValue retrieves a single value from a record chosose (the table is the current class which object belongs)]
     * @param  [$int] $key    [record to find]
     * @param  [$field] $search [field from record]
     * @return [$string]         [value from $serach column from record id=$key ]
     */
    protected function getValue($key, $search){
        $q="Select " . $search . " from " . $this->table . " where " . $this->resourcekey. "=" . $key;
  //    echo $q;
        $this->connection->query($q);
        if ($this->connection->getNrows()>0){

          $row = $this->connection->getResult()->fetch_row();
          return $row[0];
        }
    }
    /**
     * [checkRequestUser Checks if exist a request for the user from 2user and does not exist a row in access talbe for the couple user-2user]
     * @param  [String] $id2User [User who request for access]
     * @return [Boolean]         [Exist true or False]
     */
    protected function checkRequestUser($id2User){

        $query = "Select * From accessrequest ar left join access a on ar.iduser=a.id2user and ar.id1user=a.iduser where ar.iduser=" . $id2User  ." and ar.id1user=" . $this->session->userid . " and ar.accept=1 and a.idaccess is null";
    //    echo $query;
        $this->connection->query($query);
        return $this->connection->getNrows()>0;
    }
}

?>
