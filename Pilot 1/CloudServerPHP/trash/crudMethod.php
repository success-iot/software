<?php

  /** Methods Implements CRUD */

  /**
  * Extract the values from JSON and generate a string for SQL query.
  */
  function getValues($input, $method){
      //  var_dump($input);
        /*PUT*/
        if($method=='PUT'){ //Update
          $set='';
          $values = '';
          foreach( $input as $key => $value){
                $set = $set .  $key . "='" . $value . "',";
          }
          $set= rtrim($set,",");
          return $set;
        }
        /*POST*/
        if($method=='POST'){
          $set='(';
          $values = '(';
          foreach( $input as $key => $value){
                $set = $set .  $key . ",";
                $values = $values . "'" . $value . "',";
          }
          $set= rtrim($set,",") . ')';
          $values= rtrim($values,",") . ')';

          return $set . ' VALUES ' . $values;
        }

        return 'NULL';
  }

  /**
  * Implemnents GET method. Extract from request the table name and, if is the case, the element id to get
  */
  function methodGET($connection, $request){
        $table = $request[0];
        if (!$connection->existTable($table)){
            return "Error 404: Resource $table not found";
        }
    //  $table = getTable($request);
        $key=null;

        if (isset($request[1])){  /* Resource id from  URI*/
            $column = $connection->getNameIdColumn($table);
            $key = $request[1];
        }
        if(!$key ){ //&& $_SESSION['userid']!=0
          echo "No access to general data";
          exit();
        }
        if (!checkUserAccess($key)){
            echo "No access";
            exit();
        }
        $query = "select * from `$table`".($key?" WHERE $column=$key":' Order by datetime DESC');
        $connection->query($query);

        if (!$connection->getResult()) {
           http_response_code(404);
        	 return 'Error 404';
       }
       /** Generate a JSON response */
        $json='';
        if (!$key) $json='[';
        for ($i=0;$i<mysqli_num_rows($connection->getResult());$i++) {
           $json = $json . ($i>0?',':'').json_encode(mysqli_fetch_object($connection->getResult()));
        }
        if (!$key) $json=$json . ']';

        return $json;
  }

  /**
  * Implements PUT method to update a resource.
  */
    function methodPUT($connection, $request, $input){
        $table = $request[0];
        if (!$connection->existTable($table)){
            return "Error 404: Resource $table not found";
        }
        $key=null;
        /* If is not resource id in URI -> Error */
        if (isset($request[1])){
            $column = $connection->getNameIdColumn($table);
            $key = $request[1];
        }else{
              return "Error 405: Id item not specified";
        }
        /* generate the string query of pairs 'field=value' */
        $set = getValues($input, 'PUT');
        $query =  "update $table set $set where $column=$key";
        $connection->query($query);
        //$connection->update($table, $fields, $values)
        if (!$connection->getResult()) {
          return "Error 404";

        }

        return $connection->getNrows();
   }

   /**
   * Implements POST method to create new elements.
   */
   function methodPOST($connection, $request, $input){
       $table = $request[0];
       if (!$connection->existTable($table)){
           return "Error 404: Resource $table not found";
       }
       /* get sql string of pairs 'column=value' */
       $set = getValues($input, 'POST');
       $query =  "insert into $table " . $set;
       $connection->query($query);
       //$connection->insert($table, $fields, $values)
       if (!$connection->getResult()) {
         return "Error 404";
       }
       return $connection->getNrows();
  }

  /**
  * Implements DELETE method to erase elements.
  */
  function methodDELETE($connection, $request){
      $table = $request[0];
      if (!$connection->existTable($table)){
          return "Error 404: Resource $table not found";
      }
      $key=null;
      /* get the id of item resource */
      if (isset($request[1])){
          $column = $connection->getNameIdColumn($table);
          $key = $request[1];
      }else{
            return "Error 405: Id item not specified.";
      }
      $query =  "delete from $table where $column=$key";
      $connection->query($query);
      //$connection->update($table, $fields, $values)
      if (!$connection->getResult()) {
          return "Error 404";
      }

      return $connection->getNrows();
 }

?>
