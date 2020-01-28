<?php

class Logger {

    private path = "log/";
    private $handle;

    function __contruct(){
      $date = getdate();
      $name =  $date['year'] . "_" .  $date['month'] . "_" . $date['day'];
      $file = $this->path . $name . 'txt';
      $handle = fopen($my_file, 'a');

    }

    function addLine(){
      fwrite($this->$handle, $new_data);
    }

    function close(){
      fclose($this->$handle);
    }

    function __destruct() {
        $this->close();
    }

}
