class Outcome {
  //{"idoutcome":"599","iduser":"1","datetime":"2019-02-28 14:44:58","context":"eating","type":"1","state":"unEat","value":"0","
  //{"":null}
  constructor(json){
       this.model="outcomes";
       this.id=json.idoutcome;
       this.user=json.iduser;
       this.datetime= json.datetime;
       this.context=json.context;
       this.state=json.state;
       this.value=json.value;
       this.type=json.type;
        this.info=json.info;
       switch (this.type) {
         case "1":
           this.typeText= "User's acivity detected: ";
           this.meaning = "The user " + ((this.value=="0")?"stops":"starts") + " the activtiy " + this.context;
           break;
         case "2":
             this.typeText= "Warning sent to the user: ";
             this.meaning = "The system sent the warning " + this.state + " to user device";
             break;
          case "3":
            this.typeText= "Alert sent to the caregivers: ";
            this.meaning = "The system sent an Alert about " + this.state + " to caregiver device";
            break;
          case "4":
             this.typeText= "User response: ";
             this.meaning = "The user sent the '" + this.info + "' information about " + this.context;
             break;
         default:

       }
       this.renderElement();

  }

  renderElement(){
    this.el=$("<div class='"+this.model+"'   id='" + this.id +  "'> </div>");
    var date = $("<p> This " + this.context + " activity was recorder at " + this.datetime + "</p>");

    var context = $("<p> " +  this.meaning+ "</p>");
      var info  = "";
    if (this.info != null){
        var info = $("<p>" + this.info +   "</p>");
    }
    this.el.append(date).append(context).append(info);
    this.el.hide();
  }

  renderList(){
    if(this.type!="1" && this.value==""){
      return null;
    }
    this.li =  $("<li class='"+this.model+"'  id='" +  this.id + "' >" + this.datetime + " " + this.typeText + " on " + this.context + "</li>");

    this.li.attr("iduser", this.user);
    this.li.attr("date", this.datetime.split(" ")[0]);
    this.li.attr("state", this.state);
    this.li.attr("context", this.context);
    this.li.attr("type", this.type);
    this.li.attr("value",this.value);

    this.addButtonDelete(this.el);
    this.li.append(this.el);
    this.addClick();
    return this.li;
  }

  addButtonDelete(e){
      var b = $("<button class=''> Delete </button>");
      var t = this;

      b.click(function(){
          t.delete();
          loadElements("outcomes");

        //  outcomes(true);
      });

      e.append(b);

  }
  addClick(){
    //  var t = this;
      this.li.click(function(){
           var content = $(this).children("div");
           $("li.outcomes.active ").removeClass("active").children("div").hide();
           content.toggle();
           if(content.is(":visible")){
             $(this).addClass("active");
           }else{
             $(this).removeClass("active");
           }
           //$("#iteminfo").text(this.el);
      });
  }


  delete(){
    $.ajax({
        url: "/success/server.php/" + this.model + "/" + this.id,
        type: "DELETE",
        xhrFields: {
            withCredentials: true
         }
    }).done(function(e) {
      //    console.log("loaded " + e);
          var json = JSON.parse(e);

          if (json.code==0){
            console.log(json.msg);
          }else{
            console.log(json.msg);
             data= json.data;
             $("#" + this.id + "." + this.model).fadeOut();
             $("#popup").text(json.msg).show().delay(2000).fadeOut("slow");
          }
    //    checkResponse(e);
    });
  }

}
