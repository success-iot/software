<?php
/**
 * [Server file contains funccions to manage and handle the requests to API.
 *  Checks session active.
 *  Checks url is well formed and the resoruce request file_exists
 *  Checks the input JSON in case the Method invoque need it.
 *  Load Resource file in runtime and create the object on Worker
 *  Run the CRUD method requested.]
 *
 */
require_once 'security/security.php';
require_once 'class/json.php';

$json = new JsonResponse(null, null);
if(!$session->isActiveSession()){
    $json->sendResponse($json::$ERROR,"User no logger" );
}

/**
 * [$method holds the CRUD method demanded by request]
 * @var [type]
 */
$method = $_SERVER['REQUEST_METHOD'];
/**
 * [Checks the URL include a Resurce name to access. If not return an error.]
 * @var [$_SERVER['PATH_INFO']]
 */
if (!isset($_SERVER['PATH_INFO'])){
    $json->sendResponse($json::$ERROR,"Resource not found");
    	// echo "Error 404: No Resource selecte222d";
    	// exit(0);
    }
    if ($_SERVER['PATH_INFO']=='/'){

      $json->sendResponse($json::$ERROR,"Resource not found");
    	// echo "Error 404: No Resource selected";
    	// exit(0);
    }
    $request = explode('/', trim($_SERVER['PATH_INFO'],'/'));
    /**
     * [$input holds the JSON parameter from user request ]
     * @var [JSON]
     */
    $input = json_decode(file_get_contents('php://input'),TRUE);

/**
 * [$resourceName holds the reource name]
 * @var [type]
 */
$resourceName = ucfirst($request[0]);
/**
 * [$resourcePath checks if resource exists into the system and load the file related
 *  Exit(0) if it is not found]
 * @var string
 */
$resourcePath = "resources/". $request[0] . ".php";
          if (file_exists($resourcePath)) {
              include  $resourcePath;
          }else{
              $json->sendResponse($json::$ERROR,"Resource not found");

          }
          if (isset($request[1])){  /* Resource id from  URI*/
             $key = $request[1];
             // key is numeric! Avoid SQL injection
             $key = preg_replace("/[^0-9]/", "", $key);
          }else{
             $key=NULL;
          }
/**
 * [$resource crates an object sort of resource requested]
 * @var $resourceName
 */
$resource = new $resourceName();
$response = "Success operation";
/**
 * [Runs the Method in the resource.]
 * @var [type]
 */
switch ($method) {
  case 'GET':
          $json->method="GET";
      //  echo "calling get";
          $data = $resource->GET($key);
          //ECHO "RESPONSEEEEE" + $response;
          if($data=="0"){
            $code=0;
            $response="No elements were found";
            break;
          }
          $code=1;
          $json->addJsonObject("data",$data);
          break;
  case 'PUT':
          $json->method="PUT";
      		if(!isset($input)){
            $code=0;
      			$response='Request error: Json malformed';
      			break;
      		}

          $data = $resource->PUT($key, $input);
          if($data==0){
            $code=0;
            $response="No elements update";
            break;
          }
          $code=1;

          $json->data=$data;
          break;

  case 'POST':
    $json->method="POST";
      		if(!isset($input)){
            $code=0;
      			$response='Error: Json malformed';
      			break;
      		}
          $data = $resource->POST($input);
          if($data==0){
            $code=0;
            $response="No element create";
            break;
          }
          $code=1;

          $json->data=$data;
          break;

  case 'DELETE':
  $json->method="DELETE";
      	$data = $resource->DELETE($key);
        if($data==0){
          $code=0;
          $response="No elements delete";
          break;
        }
    	  $code=1;
        $json->data=$data;
    	 	break;
	default:
        $code=0;
    	  $response = "Error 404: No command";
}
//Finalice conecction
unset($resource);

$json->sendResponse($code, $response);

?>
