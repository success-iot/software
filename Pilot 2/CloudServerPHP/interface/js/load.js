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

    $("#refresh").click(function(){
          outcomes(true);
    })
    $("#dateSelect").change(function(){
        selectRowByDate($(this).val());
    });
    outcomes(true);

});
// function selectRowByDate(date){
//       console.log("date " + date);
//       if (date==""){
//         $(".nofilter").removeClass("nofilter");
//         //$("#info div.active ul").children().show();
//         $("#info div.active .pager").show();
//         var pageActive = $("#info div.active .pager a.active");
//         $("#info div.active ul").children().hide();
//         $("#info div.active ul").children().slice(pageActive.attr("from"), pageActive.attr("to")).show();
//       }else{
//         $(".nofilter").removeClass("nofilter");
//         $("#info div.active ul").children().show();
//         $("#info div.active ul").children('[date!="'  + date + '"]').addClass("nofilter");
//         $("#info div.active .pager").hide();
//       }
// }
//
// function loadElements(type){
//
//   console.log("loading " + type + "... ")
//   $.ajax({
//       url: "../server.php/" + type,
//       cache: false,
//       xhrFields: { withCredentials: true }
//
//   }).done(function(e) {
//         console.log("loaded " + e);
//         var json = JSON.parse(e);
//
//         if (json.code==0){
//           console.log(json.msg);
//         }else{
//            data= json.data;
//            render(data, type);
//         }
//   //    checkResponse(e);
//   });
// }


var t0;
var t1;


function render(list, type){
  t0 = performance.now();
    var tag;
    switch (type) {
      case "outcomes":
        console.log("Rendering " + type);
        tag = $('div#'+type + " ul");
        tag.text("");
        list.forEach(function(i,e){
            var o = new Outcome(i);
            tag.append(o.renderList());
          // count ++;
        });
        break;
      case "accessrequest":
          tag = $('div#'+type + " ul");
          tag.text("");
          list.forEach(function(i,e){
              var o = new Request(i);
              tag.append(o.renderList());
            // count ++;
      });
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

    addPager(tag, 20, 10);
//////////////
    t1=performance.now();
    console.log("time " + (t1 - t0) + " milliseconds.");
}


// function addPager(el, number, max){
//       console.log("Adding pager to " + el);
//       var pager = $("<p class='pager'></p>");
//       var l = el.children().length;
//       var pages = l/number;
//       if (pages<0){
//          pages=1;
//       }
//       for(var i=1; i<=pages; i++){
//           var pageItem = $("<a class='page' href='#' page='" + i + "' from='" + i*number + "' to='" + (i*number + number) + "'> " + i + "</a>");
//           pageItem.click(function(){
//               $(".pager").children(".active").removeClass("active");
//               el.children().hide();
//               el.children().slice($(this).attr("from"), $(this).attr("to")).show();
//
//               $(this).addClass("active");
//               if(pages>max){
//                   var current = parseInt($(this).attr("page"));
//                   pager.children().hide();
//                   pager.children().removeClass("points");
//
//                   pager.children().slice(0, max/2-1).show(); //.last().addClass("points");
//
//                   pager.children().slice(current-3, current+2).show(); //.last().addClass("points");
//                   pager.children().slice(pages-max/2,pages).show();
//                   if(max/2-1 < current-3-1 || current<3){
//                     pager.children().slice(0, max/2-1).last().addClass("points");
//                   }
//                   if(current+2 < pages-max/2-1){
//                         pager.children().slice(current-3, current+2).last().addClass("points");
//                   }
//               }
//             });
//           pager.append(pageItem);
//        }
//
//        el.after(pager);
//        el.children().hide();
//        el.children().slice(0, number).show();
//
//        if(pages>max){
//           pager.children().hide();
//           pager.children().first().addClass("active");
//           pager.children().slice(0, max/2-1).show().last().addClass("points");
//           pager.children().slice(pages-max/2+1,pages).show();
//
//        }
// }



/////// ORDER
  // $(".listitems li").sort(sort_li).appendTo('.listitems');
  // function sort_li(a, b) {
  //   return ($(b).data('position')) < ($(a).data('position')) ? 1 : -1;
  // }
//
//
// function outcomes(reload){
//     if ($("#info div#outcomes ul").children().length ==0  || reload){
//       loadElements("outcomes");
//     }
//     $("#info div").removeClass("active");
//     $("#info div#outcomes").addClass("active").show();
// }
//
// function access(reload){
//   if ($("#info div#access ul").children().length ==0  || reload){
//     loadElements("access");
//   }
//
//   $("#info div").removeClass("active");
//   $("#info div#access").addClass("active").show();
// }
//
// function request(reload){
//   if ($("#info div#accessrequest ul").children().length ==0  || reload){
//     loadElements("accessrequest");
//   }
//   $("#info div").removeClass("active");
//   $("#info div#accessrequest").addClass("active").show();
// }
//
// function messages(reload){
//   if ($("#info div#messages ul").children().length ==0  || reload){
//     loadElements("messages");
//   }
//   $("#info div").removeClass("active");
//   $("#info div#messages").addClass("active").show();
// }
//
// function logOut(){
//   console.log("Loging Out....");
//   $.ajax({
//       url: "../logOut.php",
//       xhrFields: { withCredentials: true }
//
//   }).done(function(e) {
//       console.log("Logged Out....");
//       window.location.href= "../interface/login.html";
//   //    checkResponse(e);
//   });
// }
