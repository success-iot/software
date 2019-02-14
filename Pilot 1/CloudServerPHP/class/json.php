<?php

class JsonResponse {

  public static  $ERROR=0;
  public static   $SUCCESS=1;

  private $json=array("code"=>null, "msg"=>"");

  function __contruct($code, $msg){

    $this->code=$code;
    $this->msg=$msg;

  }

  /**
  *    Stores datas in the session.
  *    Example: $instance->foo = 'bar';
  *
  *    @param    name    Name of the datas.
  *    @param    value    Your datas.
  *    @return    void
  **/

  public function __set( $name , $value )
  {
      $this->json[$name]= $value;
  }

  /**
  *    Gets datas from the session.
  *    Example: echo $instance->foo;
  *
  *    @param    name    Name of the datas to get.
  *    @return    mixed    Datas stored in session.
  **/

  public function __get( $name )
  {
      if ( isset($this->json[$name]))
      {
          return $this->json[$name];
      }
  }
  /**
   * [getJsonString description]
   * @return [type] [description]
   */
  function getJsonString(){

  }

 function addJsonObject($name, $json){

    $this->json[$name]= json_decode($json);
 }
/**
 * [getJson description]
 * @return [type] [description]
 */
  function getJson(){
      return json_encode($this->json);
  }

  function sendResponse($code, $msg){
    if(!is_null($code)){
      $this->code=$code;
      $this->msg=$msg;
    }

    echo json_encode($this->json);
    exit();
  }
}

 ?>
