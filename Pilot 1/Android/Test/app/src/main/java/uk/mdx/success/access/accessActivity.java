package uk.mdx.success.access;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import uk.mdx.success.Connection;
import uk.mdx.success.session;
import uk.mdx.success.test.R;

/**
 * Created by Josegines1 on 21/03/2018.
 */

public class accessActivity extends Activity {

    Connection con;
    String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (session.userrol==1){
            setContentView(R.layout.access_item);
            TextView name = (TextView) findViewById(R.id.username);
            TextView title = (TextView) findViewById(R.id.titletext);
            title.setText("User access");
            TextView body = (TextView) findViewById(R.id.text);


            Bundle b = getIntent().getExtras();
            this.key = b.getString("idaccess");
            String value = b.getString("value");
            if(value.equals("1")){
                body.setText(" has access to view your outcomes.");
            }else{
                body.setText(" has NOT access to view your outcomes.");
                Button but = (Button) findViewById(R.id.revoke);
                but.setVisibility(View.GONE);
                TextView qtn = (TextView) findViewById(R.id.question);
                qtn.setVisibility(View.GONE);
            }


            String userrequest = b.getString("userrequest");
            Connection con = new Connection(Connection.GET,"users",userrequest);
            try{
                String response = con.execute().get();
                JSONObject jsonO = new JSONObject(response);
                JSONArray array = jsonO.getJSONArray("data");
                jsonO=array.getJSONObject(0);
                name.setText(jsonO.get("name").toString());
            //    Log.v("--------------->", userrequest + jsonO.toString());
            }catch(Exception e){
                Log.e("RequestAccessAct...java", e.getMessage());
            }

        }

    }


    protected void cancel(View v){
        finish();
    }

    protected void revokeGrant(View v){
        try {
            Access rac = new Access(this.key,null,null,"0", null);
            Log.v("ACCESS JSON REVOkE to " + this.key, rac.toJson().toString());
            this.con = new Connection(Connection.PUT,"access",this.key);
            String response = con.execute(rac.toJson().toString()).get();
            Log.v("Connection PUT response", "************* " + response + " ***********");
            finish();
        }catch(Exception e){
            Log.e("accessActivity.java", e.getMessage());
        }
    }
}
