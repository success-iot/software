package uk.mdx.success.accessRequest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import uk.mdx.success.Connection;
import uk.mdx.success.JSON;
import uk.mdx.success.access.Access;
import uk.mdx.success.session;
import uk.mdx.success.test.R;

/**
 * Created by Josegines1 on 21/03/2018.
 */

public class accessRequestActivity extends Activity {

    private Connection con;
    private String key;
    private String primaryuser;
    private String userrequest;
    private String  username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (session.userrol==1){
            setContentView(R.layout.request_item_primary);
            TextView title = (TextView) findViewById(R.id.titletext);
            title.setText("Request Access");
            Bundle b = getIntent().getExtras();
            key = b.getString("idRequest");
            primaryuser = b.getString("iduser");
            userrequest= b.getString("id2user");


        }
        else {
            setContentView(R.layout.request_item_seconday);
            TextView title = (TextView) findViewById(R.id.titletext);
            title.setText("Request access ");
            TextView text = (TextView) findViewById(R.id.request_text);
            text.setText("Ask for access to user: ");
//            Bundle b = getIntent().getExtras();
//           String key = b.getString("idRequest");



        }
        TextView name = (TextView) findViewById(R.id.username);
        Connection con = new Connection(Connection.GET,"users",userrequest);
        try{
            String response = con.execute().get();
            JSONObject jsonO = new JSONObject(response);
            JSONArray array = jsonO.getJSONArray("data");
            jsonO=array.getJSONObject(0);
            name.setText(jsonO.get("name").toString());

        }catch(Exception e){
            Log.e("RequestAccessAct...java", e.getMessage());
        }

    }

    protected void send(View v){

        Connection con = new Connection(Connection.POST,"accessRequest", null);
        TextView mail = (EditText) findViewById(R.id.usermail);
        String usermail = mail.getText().toString();
        Toast toast;
        if (usermail.isEmpty()){

            toast = Toast.makeText(this,"Email user is empty", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        try {
            accessRequest rac = new accessRequest(null,usermail,null,null,null,null);
 //           con.setMethod(Connection.POST);
//            String response = con.execute(rac.toJson().toString()).get();
            String response = con.execute(rac.toJson().toString()).get();

            toast = Toast.makeText(this, JSON.getValue(response, "msg"), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            if (JSON.getValue(response, "code").equals("1")){
                finish();
            }
            mail.setText("");
        }catch(Exception e){
            Log.e("RequestAccessAct...java", e.getMessage());
        }
    }

    protected void cancel(View v){
        finish();
    }
    protected void deny(View v){
        try {
            this.con = new Connection(Connection.PUT,"accessRequest",key);
            accessRequest rac = new accessRequest(null,null,null,"-1",null,null);
            String response = con.execute(rac.toJson().toString()).get();
            finish();
        }catch(Exception e){
            Log.e("RequestAccessAct...java", e.getMessage());
        }
    }

    protected void accept(View v){
        String response="";
        try {
            this.con = new Connection(Connection.PUT,"accessRequest",key);
            accessRequest rac = new accessRequest(null,null,null,"1",null,null);
            response = con.execute(rac.toJson().toString()).get();

            if (response=="0"){
                Log.v("RequestActivity","Modify accessRequest: " + response + " ");
                finish();
            }
            this.con.DELETE();

            Access acs = new Access(null, primaryuser, userrequest, "1", null);
            Log.v("JSON DE ACCESS", acs.toJson().toString());
            this.con = new Connection(Connection.POST,"access",null);
          //  con.setMethod(Connection.POST);
            response = con.execute(acs.toJson().toString()).get();
            Log.v("RequestActivity", "Modify access: " + response + " ");
            finish();

        }catch(Exception e){
            Log.e("RequestAccessAct...java", e.getMessage());
        }
    }

}
