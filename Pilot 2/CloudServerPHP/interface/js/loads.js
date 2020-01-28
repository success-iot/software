/**
 * Check session is started
 */

// try{
//   var s = JSON.parse(decodeURI(window.location.search.substr(1).substr("2")));
// }catch(err){
//     console.log("There was a problem with JSON session." + err.message);
// }
window.history.replaceState({}, document.title, document.location.pathname);

$(document).ready(function(){

    $("#myout").click(function(){
        outcomes(false);
    })

    $("#myaccess").click(function(){
        access(false);
    })

    $("#myrequest").click(function(){
        request(false);
    })

    $("#mysmsg").click(function(){
        messages(false);
    })

    $("#logout").click(function(){
        logOut();
    })

    $("#requestAccess").click(function(){
        $("#formRequest .msg").text("");
          $("#formRequest").show();
    });
    $("#request").click(function(){
          requesForAccess($("#userToRequest").val());
    });

    $("#selectUser").change(function(){
        var id = $("#selectUser option:selected" ).val();
        $("#usersName").children().hide();
        $("#usersName").children("[iduser=" + id + "]").show().next("p").show();
        $("#dateSelect").val("");
    });

    $("#dateSelect").change(function(){
        selectRowByDate($(this).val());
    });

    $(".close").click(function(){
        $(this).parent().hide();
    });

    outcomes(true);

});



function requesForAccess(email){

  var json = '{"name":"'+ email + '"}';
  $.ajax({
      url: "../server.php/accessrequest",
      type: "POST",
      data:   json,
      cache: false,
      xhrFields: { withCredentials: true }

  }).done(function(e) {

        var json = JSON.parse(e);

        if (json.code==0){
            console.log("Request " + json.msg);
            $("#formRequest .msg").text(" Request has not been created, check user name ");
        }else{

           $("#formRequest .msg").text("Request has not been created, check user name");
           $("#formRequest").delay(2000).hide();
        }
  //    checkResponse(e);
  });
}
var t0;
var t1;


function render(list, type){
  t0 = performance.now();
    var tag;
    switch (type) {
      case "outcomes":
    //    console.log("Rendering " + type);
        tag = $('div#'+type +  " div#usersName");
        tag.text("");
        list.forEach(function(i,e){
            var o = new Outcome(i);
            addElementToUser(tag, o);
          // count ++;
        });
        $("div#usersName ul").each(function(index, el){
            addPager($(el), 15, 10);
        });
        $("li button").hide();
          break;
      case "accessrequest":
          tag = $('div#'+type + " ul");
          tag.text("");
          list.forEach(function(i,e){
            console.log(e);
              var o = new Request(i);
              tag.append(o.renderList());
            // count ++;
      });
      $("li button").hide();
        break;
      case "access":
        tag = $('div#'+type + " ul");
        tag.text("");
        list.forEach(function(i,e){
            var o = new Access(i);
            tag.append(o.renderList());
          // count ++;
        });
        break;
      case "messages":
          tag = $('div#'+type + " ul");
          tag.text("");
          list.forEach(function(i,e){
              var o = new Message(i);

              tag.append(o.renderList());
            // count ++;
          });
          break;
      default:
          break;
    }

//    addPager(tag, 20, 10);
//////////////
    t1=performance.now();
    console.log("time " + (t1 - t0) + " milliseconds.");
}

function addElementToUser(tag, el){
    var ul;

    if(tag.children("[iduser='" + el.user + "']").length==0){
        ul = $("<ul iduser='" + el.user + "'></ul>");
        ul.append(el.renderList());
        tag.append(ul);
        addUserName(el.user);
    }else{
        ul = tag.children("[iduser='" + el.user + "']");
        ul.append(el.renderList());
    }

}

function addUserName(id){
  $.ajax({
      url: "../server.php/users/" + id,
      cache: false,
      xhrFields: { withCredentials: true }

  }).done(function(e) {

        var json = JSON.parse(e);

        if (json.code==0){
          console.log(json.msg);
        }else{
           data= json.data;
        //   console.log("Username " + id + " " + data.name);
           $("#selectUser").append(new Option(data[0].name, id));
           $("#usersName").children().hide();
           $("#usersName").children("[iduser=" + id + "]").show().next("p").show();
        }


  //    checkResponse(e);
  });
}
