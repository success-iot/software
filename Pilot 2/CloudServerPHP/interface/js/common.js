/**
 * Check session is started
 */

// try{
//   var s = JSON.parse(decodeURI(window.location.search.substr(1).substr("2")));
// }catch(err){
//     console.log("There was a problem with JSON session." + err.message);
// }
window.history.replaceState({}, document.title, document.location.pathname);


function selectRowByDate(date){
    //  console.log("date " + date);
      if (date==""){
        $(".nofilter").removeClass("nofilter");
        //$("#info div.active ul").children().show();
        $("#info div.active .pager").show();
        var pageActive = $("#info div.active .pager a.active");
        $("#info div.active ul").children().hide();
        $("#info div.active ul").children().slice(pageActive.attr("from"), pageActive.attr("to")).show();
        showStatistic(0);
      }else{
        $(".nofilter").removeClass("nofilter");
        $("#info div.active ul").children().show();
        $("#info div.active ul").children('[date!="'  + date + '"]').addClass("nofilter");
        $("#info div.active .pager").hide();
      }
      showStatistic(1);
}

function loadElements(type){

  console.log("loading " + type + "... ")
  $.ajax({
      url: "../server.php/" + type,
      cache: false,
      xhrFields: { withCredentials: true }

  }).done(function(e) {
        console.log("loaded " + e);
        var json = JSON.parse(e);

        if (json.code==0){
          console.log(json.msg);
        }else{
           data= json.data;
           render(data, type);
        }
  //    checkResponse(e);
  });
}



function addPager(el, number, max){
      console.log("Adding pager to " + el);
      var pager = $("<p class='pager'></p>");
      var l = el.children().length;
      var pages = l/number;
      if (pages<0){
         pages=1;
      }
      for(var i=1; i<=pages; i++){
          var pageItem = $("<a class='page' href='#' page='" + i + "' from='" + i*number + "' to='" + (i*number + number) + "'> " + i + "</a>");
          pageItem.click(function(){
              $(".pager").children(".active").removeClass("active");
              el.children().hide();
              el.children().slice($(this).attr("from"), $(this).attr("to")).show();

              $(this).addClass("active");
              if(pages>max){
                  var current = parseInt($(this).attr("page"));
                  pager.children().hide();
                  pager.children().removeClass("points");

                  pager.children().slice(0, max/2-1).show(); //.last().addClass("points");

                  pager.children().slice(current-3, current+2).show(); //.last().addClass("points");
                  pager.children().slice(pages-max/2,pages).show();
                  if(max/2-1 < current-3-1 || current<3){
                    pager.children().slice(0, max/2-1).last().addClass("points");
                  }
                  if(current+2 < pages-max/2-1){
                        pager.children().slice(current-3, current+2).last().addClass("points");
                  }
              }
            });
          pager.append(pageItem);
       }

       el.after(pager);
       el.children().hide();
       el.children().slice(0, number).show();

       if(pages>max){
          pager.children().hide();
          pager.children().first().addClass("active");
          pager.children().slice(0, max/2-1).show().last().addClass("points");
          pager.children().slice(pages-max/2+1,pages).show();

       }
}

/////// ORDER
  // $(".listitems li").sort(sort_li).appendTo('.listitems');
  // function sort_li(a, b) {
  //   return ($(b).data('position')) < ($(a).data('position')) ? 1 : -1;
  // }

function showStatistic(show){

  if(show==0){
    $("#statistic").hide();
    return;
  }
  var state = {};
  var alerts = {};
  alerts['warnings'] = 0;
  alerts['alerts'] = 0;
  var userfeedback = 0;
  $("div#outcomes ul li:visible").each(function(i, el){
      switch (el.getAttribute("type")) {
        case "1":
          if(el.value=="1"){
            var s = el.getAttribute("state");
          //  console.log(el.getAttribute("state"));
            if (state[s]==undefined){
              state[s]=1;
            }else{
              state[s]++;
            }
          }
        break;
        case "2":
          alerts['warnings']++;
        break;
        case "3":
            alerts['alerts']++;
        break;
        case "4":
          userfeedback++;
        break;
        default:
      }
  });

  var act = $("<div id='activities'></div");
  Object.entries(state).forEach(([i, e]) => {
     act.append($("<p>" + i + ": " + e + " times </p>"));
  });

  var al = $("<div id='alert'></div");
  Object.entries(alerts).forEach(([i, e]) => {
     al.append($("<p>" + i + ": " + e + " times </p>"));
  });
  var p = $("<p> User's feedbacks: " + userfeedback + "</p>");

  $("#statistic div#data").text("");
  $("#statistic div#data").append(act).append(al).append(p);
    $("#statistic").show();
}

function outcomes(reload){
    if ($("#info div#outcomes ul").children().length ==0  || reload){
      $("#info div#outcomes .pager").remove();
      loadElements("outcomes");

    }
    $("#info div").removeClass("active");
    $("#info div#outcomes").addClass("active").show();
}

function access(reload){
  if ($("#info div#access ul").children().length ==0  || reload){
      $("#info div#access .pager").remove();
    loadElements("access");
  }

  $("#info div").removeClass("active");
  $("#info div#access").addClass("active").show();
}

function request(reload){
  if ($("#info div#accessrequest ul").children().length ==0  || reload){
    $("#info div#accessrequest .pager").remove();
    loadElements("accessrequest");
  }
  $("#info div").removeClass("active");
  $("#info div#accessrequest").addClass("active").show();
}

function messages(reload){
  if ($("#info div#messages ul").children().length ==0  || reload){
    $("#info div#messages .pager").remove(
    loadElements("messages");
  }
  $("#info div").removeClass("active");
  $("#info div#messages").addClass("active").show();
}

function logOut(){
  console.log("Loging Out....");
  $.ajax({
      url: "../logout.php",
      xhrFields: { withCredentials: true }

  }).done(function(e) {
      console.log("Logged Out....");
      window.location.href= "../interface/login.html";
  //    checkResponse(e);
  });
}
