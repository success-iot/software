package uk.mdx.success.accessRequest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import uk.mdx.success.test.R;

/**
 * Created by Josegines1 on 21/03/2018.
 */

public class accessRequest {

    private String userid; // the user whose RequestUserid wants access
    private String dateTime;
    private String id;
    private String state;
    private String name;
    private String RequestUserid; //the user who request

    public accessRequest(String id, String userid, String RequestUserid, String state, String dateTime, String name){

        this.userid=userid;
        this.dateTime=dateTime;
        this.id=id;
        this.RequestUserid=RequestUserid;
        this.state=state;
        this.name=name;

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
    public String getState(){
        return this.state;
    }
    public String getRequestUserid(){
        return this.RequestUserid;
    }
    public String getName(){
        return this.name;
    }

    public String getStateMeaning(){
       return "Pending";
    }

    public String toString(){
        return this.dateTime + ": " + this.RequestUserid + " -> " + this.state;
    }

    public JSONObject toJson() {
        JSONObject jsonParam=null;
        try {
            jsonParam = new JSONObject();
            jsonParam.put("idrequest", this.id);
            jsonParam.put("iduser", this.RequestUserid);
            jsonParam.put("id1user",this.userid);
            jsonParam.put("datetime", this.dateTime);
            jsonParam.put("accept", this.state);

        }catch(Exception e){
            Log.e("Outcome: toJSon", e.toString());
        }finally {
            return jsonParam;
        }

    }

}
