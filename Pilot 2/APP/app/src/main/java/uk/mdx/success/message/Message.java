package uk.mdx.success.message;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Josegines1 on 21/03/2018.
 */

public class Message {

    private String userid;
    private String dateTime;
    private String id;
    private String type;
    private String body;

    public Message(String id, String userid, String dateTime, String type, String value){

        this.userid=userid;
        this.dateTime=dateTime;
        this.id=id;
        this.type=type;
        this.body=value;

    }
    public String getUserid(){
        return this.userid;
    }
    public String getDateTime(){
        return this.dateTime;
    }
    public String getId(){
        return this.id;
    }
    public String getType(){
        return this.type;
    }
    public String getBody(){
        return this.body;
    }

    public String toString(){
        return this.dateTime + ": " + this.type + " -> " + this.body;
    }

    public JSONObject toJson() {
        JSONObject jsonParam=null;
        try {
            jsonParam = new JSONObject();
            jsonParam.put("idmessage", this.id);
            jsonParam.put("iduser", this.userid);
            jsonParam.put("type", this.type);
            jsonParam.put("date", this.dateTime);
            jsonParam.put("body", this.body);

        }catch(Exception e){
            Log.e("Message: toJSon", e.toString());
        }finally {
            return jsonParam;
        }

    }
}
