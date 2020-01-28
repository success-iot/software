package uk.mdx.success.outcome;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Josegines1 on 21/03/2018.
 */

public class Outcome {

    private String userid;
    private String dateTime;
    private String id;
    private String type;
    private String context;
    private String state;
    private String value;

    public Outcome(String id,  String userid, String dateTime,  String type, String value, String context, String state){

        this.userid=userid;
        this.dateTime=dateTime;
        this.id=id;
        this.type=type;
        this.value=value;
        this.context=context;
        this.state=state;

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
    public String getValue(){
        return this.value;
    }

    public String getContext(){ return this.context;}
    public String getState(){ return this.state;}

    public String getValueMeaning(){
        String str="Test";
        switch (this.getType()){
            case "wandering":
                if (getValue().equals("1")){
                    return "Start";
                }else{
                    return "Stop";
                }
        }


        return str;
    }

    public String toString(){
        return this.dateTime + ": " + this.type + " -> " + this.value;
    }

    public JSONObject toJson() {
        JSONObject jsonParam=null;
        try {
            jsonParam = new JSONObject();
            jsonParam.put("idoutcome", this.id);
            jsonParam.put("iduser", this.userid);
            jsonParam.put("type", this.type);
            jsonParam.put("dateTime", this.dateTime);
            jsonParam.put("value", this.value);
            jsonParam.put("context", this.context);
            jsonParam.put("state", this.state);

        }catch(Exception e){
            Log.e("Outcome: toJSon", e.toString());
        }finally {
            return jsonParam;
        }

    }
}
