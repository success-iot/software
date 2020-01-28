/**
 * Request Class: Js model of request and action avalibles
 */
class Request {

  constructor(json){
       this.model="accessrequest";
       this.id=json.idrequest;
       this.datetime= json.datetime;
       this.state = json.accept;
       this.namefrom = json.name;
       this.nameto = json.name1user;
       if (this.state == "0"){
         this.stateText="Pending";
       }else if(this.state=="-1"){
         this.stateText="Denied";
       }else{
          this.stateText="Accepted";
       }
  }
/**
 * Generate a full element to show a wider information
 * @return {[type]} [description]
 */
  renderElement(){
    this.el=$("<div class='"+ this.model + "' id='" + this.id + "'> </div>");
  }
/**
 * Generate the element li for the lists
 * @return {[type]} [description]
 */
  renderList(){
    this.li =  $("<li class='" + this.model + "' id='" +  this.id + "' > " + this.datetime + " Request from " + this.namefrom  + " to " + this.nameto + " is " + this.stateText +  "</li>");
    if(this.state=="0"){
      this.addButtonAccept(this.li);
      this.addButtonDeny(this.li);
    }
    return this.li;
  }

  addButtonAccept(e){
    var b = $("<button class=''> Accept </button>");
    var t = this;

    b.click(function(){
        t.put(1);
        request(true);
    });

    e.append(b);
  }

  addButtonDeny(e){
    var b = $("<button class=''> Deny </button>");
    var t = this;

    b.click(function(){
        t.put(-1);

    });

    e.append(b);
  }

  put(value){
    var param = '{"accept":"' + value + '"}';

    $.ajax({
        url: "../server.php/" + this.model + "/" + this.id,
        data: param,
        // contentType: "application/json; charset=utf-8",
        // dataType: "json",
        type: "PUT"
    }).done(function(e) {
      //    console.log("loaded " + e);
           var json = JSON.parse(e);

          if (json.code==0){
            console.log(json.msg);
          }else{
             data= json.data;
             $("#popup").text(json.msg).show().delay(2000).fadeOut("slow");
             request(true);
          }
    //    checkResponse(e);
    });
  }
}
