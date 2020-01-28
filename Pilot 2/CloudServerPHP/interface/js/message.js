class Message {
  //{"iduser":"1","type":"3","body":"Paul@mdx.uk","idmessage":"74","readed":"1","date":"2019-02-07 11:15:30"}
  constructor(json){
       this.model="messages";
       this.id=json.idmessage;
       this.datetime= json.date;
       this.body=json.body;
       this.readed=json.readed;
       this.type=json.type;
       this.renderElement();
  }

  renderElement(){
    this.el=$("<div class='"+this.model+"'   id='" + this.id +  "'> </div>");
    var body;
    switch (this.type) {
      case "1":
        body = "The IP " + this.body + ". Someone tryed to login with your user.</br> Contact to the administrator if the IP is correct";
        break;
      case "0":
        body = "You have started a new session from " + this.body + ".</br>  if it is you ok, if not contact to the administrator. ";
        break;
      case "2":
          body = "User " + this.body + " has been accessed to your data. ";
          break;
      case "3":
          body =  this.body;
          break;
      default:
          break;
      }

    var context = $("<p> " + body + "</p>");

    this.el.append(context);
    this.el.hide();
  }
  renderList(){
    var body="";
    var r = this.readed=="1"?"read":"notread" ;
    switch (this.type) {
      case "1":
        body = "Security Alert";
        break;
      case "0":
        body = "New Session device";
        break;
      case "2":
          body = "Data accessed";
          break;
      case "3":
          body = "Modfication on accesses";
          break;
      default:
          break;
      }
    this.li =  $("<li class='" + r + "' id='" +  this.id + "' >" + this.datetime + " " + body + "</li>");
    this.li.append(this.el);
    this.addClick();
    return this.li;
  }

  addClick(){
    //  var t = this;
      this.li.click(function(){
           $(this).removeClass("notread");
           var content = $(this).children("div");
           $("li.messages.active ").removeClass("active").children("div").hide();
           content.toggle();
           if(content.is(":visible")){
             $(this).addClass("active");
           }else{
             $(this).removeClass("active");
           }
           //$("#iteminfo").text(this.el);
      });
  }
}
