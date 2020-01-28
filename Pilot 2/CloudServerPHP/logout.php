<?php
/**
 * [FILE destroy current user session just the user was login. This checks avoid brutal force attacks.]
 *
 */
require_once 'security/security.php';

if($session->isActiveSession()){
    $session->destroy();
}
 session_destroy();


?>
