class Access {
  constructor(json){
    //{"idaccess":"38","iduser":"1","id2user":"3","value":"1","name":"Mark@mdx.uk"}
       this.model="access";
       this.id=json.idaccess;
       this.id2User = json.id2user;
       this.name= json.name;
       this.access=json.value;
    }

  renderElement(){
    this.el=$("<div class='" + this.model + "' id='" + this.id + "'> </div>");
  }
  renderList(){
    this.li =  $("<li  class='" + this.model + "' id='" +  this.id + "' > " + this.name + ( this.access=="1" ? " has " : " does not have ") +  " access. </li>");
    if(this.access=="1"){
      this.addButtonRevoke(this.li);
    }
    return this.li;
  }

  addButtonRevoke(e){
    var b = $("<button class=''> Revoke </button>");
    var t = this;

    b.click(function(){
        t.revoke();
        access(true);
    });

    e.append(b);
  }

  revoke(){
    var param = '{"value":"0"}';
    console.log("../server.php/" + this.model + "/" + this.id + " PARAMS: " + param);
    $.ajax({
        url: "../server.php/" + this.model + "/" + this.id,
        data: param,
        // contentType: "application/json; charset=utf-8",
        // dataType: "json",
        type: "PUT"
    }).done(function(e) {
           console.log("Revoked: " + e);
           var json = JSON.parse(e);

          if (json.code==0){
            console.log(json.msg);
          }else{
             data= json.data;
             $("#popup").text(json.msg).show().delay(2000).fadeOut("slow");
          }
    //    checkResponse(e);
    });
  }

}
