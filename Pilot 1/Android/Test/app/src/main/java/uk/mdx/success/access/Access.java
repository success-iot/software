package uk.mdx.success.access;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Josegines1 on 21/03/2018.
 */

public class Access {
    private String iduser;
    private String idaccess;
    private String id2user;
    private String value;
    private String name;

    public Access(String id, String iduser, String id2user, String value, String name){

        this.iduser=iduser;
        this.id2user=id2user;
        this.idaccess=id;
        this.value=value;
        this.name=name;

    }
    public String getiduser(){
        return this.iduser;
    }
    public String getid2user(){
        return this.id2user;
    }
    public String getId(){
        return this.idaccess;
    }
    public String getName(){
        return this.name;
    }
    public String getValue(){
        return this.value;
    }

    public String toString(){
        return this.id2user + ": " + this.value + " to " + this.iduser;
    }
    public JSONObject toJson() {
        JSONObject jsonParam=null;
        try {
            jsonParam = new JSONObject();
            jsonParam.put("idaccess", this.idaccess);
            jsonParam.put("iduser", this.iduser);
            jsonParam.put("id2user", this.id2user);
            jsonParam.put("value", this.value);

        }catch(Exception e){
            Log.e("Message: toJSon", e.toString());
        }finally {
            return jsonParam;
        }

    }

    public String getAccessState(){
        switch (this.value){
            case "1":
                return "Access Allowed";

            case "0":
                return "Access Revoked";

            default:
                return "Unknown(Not access)";

        }
    }
}
