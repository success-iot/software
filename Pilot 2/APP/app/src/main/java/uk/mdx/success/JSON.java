package uk.mdx.success;

import org.json.JSONObject;

/**
 * Created by Josegines1 on 24/10/2018.
 */

 public class JSON {

    public static String getValue(String json, String key){
        try {
            JSONObject js = new JSONObject(json);
            return js.get(key).toString();
        }catch (Exception e){

        }

        return "";
    }
}
