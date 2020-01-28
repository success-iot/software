
/**
 * Check session is started
 */

$(document).ready(function(){

      $('Button#log').click(function(){
          login();
      });

});





function login(){
  console.log("Getting Credencials...." + $("#login").serialize());
  $.ajax({
        url: "../login.php",
        data: $("#login").serialize()
    }).done(function(e) {
        console.log("Server response " + e);
        var json = JSON.parse(e);
        console.log("MEssage Code response " + json.code);
        if(json.code == "0"){
          $("#errorlogin").text(json.msg).show();
        }
        else{
            console.log("User logged" + JSON.stringify(json.session));
            if(json.session.userrol=="1"){
              window.location.href="main.php";//?p=" + JSON.stringify(json.session);
            }else{
              window.location.href="mains.php";
            }
        //  userLoged();
        }
    });
}
