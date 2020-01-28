package uk.mdx.success.activity;
import uk.mdx.success.Connection;
import uk.mdx.success.access.accessList;
import uk.mdx.success.accessRequest.accessRequestActivity;
import uk.mdx.success.accessRequest.accessRequestList;
import uk.mdx.success.cookies;
import uk.mdx.success.message.messagesAdapter;
import uk.mdx.success.message.messagesList;
import uk.mdx.success.outcome.outcomesList;
import uk.mdx.success.session;
import uk.mdx.success.test.R;
import uk.mdx.success.test.emotional_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView user = (TextView) findViewById(R.id.username);
        user.setText("User: " + session.username + " --  PID: " + session.SessionKey);
        Log.v("MAIN getcokies:", cookies.getCookiesTo());


        //Messages
        RelativeLayout messages = (RelativeLayout)findViewById(R.id.messages);
        messages.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, messagesList.class);
                startActivity(intent);
            }
        });
//DO a test Option
        LinearLayout test = (LinearLayout) findViewById(R.id.test_layout);
            test.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, emotional_test.class);
                    startActivity(intent);
                }
         });




        //VIEW Outcomes option
        LinearLayout outcomes = (LinearLayout)findViewById(R.id.outcomes_layout);
        outcomes.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, outcomesList.class);
                startActivity(intent);
            }
        });

        //VIEW Request Access option
        LinearLayout request = (LinearLayout)findViewById(R.id.request_layout);
        request.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, accessRequestList.class);
                startActivity(intent);
            }
        });

        //VIEW  Access option
        LinearLayout access = (LinearLayout)findViewById(R.id.access_layout);
        access.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, accessList.class);
                startActivity(intent);
            }
        });


        //Request Access to Patient
        LinearLayout requestaccess = (LinearLayout)findViewById(R.id.request_access_layout);
        requestaccess.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, accessRequestActivity.class);
                startActivity(intent);
            }
        });

        // If not is primary user hide the next activities
        if (session.userrol!=1) {
            test.setVisibility(View.GONE);
            access.setVisibility(View.GONE);
            request.setVisibility(View.GONE);
            messages.setVisibility(View.GONE);
            TextView outcomeSnd = (TextView) findViewById(R.id.textoutcomes);
            outcomeSnd.setText("View users outcomes");


        }else{
            requestaccess.setVisibility(View.GONE);
            checkMessages();
            checkRequest();
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.log_out:
                System.exit(1);
                break;

            default:
                break;
        }

        return true;
    }

    private void checkMessages(){
        Connection con = new Connection(Connection.GET,"messages", null);
        try{
            String r = con.execute().get();
            JSONObject jsonO = new JSONObject(r);
            JSONArray array = jsonO.getJSONArray("data");
            TextView msgNumber = (TextView) findViewById(R.id.message_number);
            Log.v("******************", Integer.toString(array.length()));
            msgNumber.setText(Integer.toString(array.length()));
        }catch(Exception e){
            Log.e("MainActivity",e.toString());
        }
    }

    private void checkRequest(){
        Connection con = new Connection(Connection.GET,"accessRequest", null);
        try{
            int count=0;
            String r = con.execute().get();
            JSONObject jsonO = new JSONObject(r);
            JSONArray array = jsonO.getJSONArray("data");
            for (int i=0; i<array.length(); i++) {
                JSONObject jObj2 = (JSONObject) array.get(i);
                if(jObj2.get("accept").equals("0")){
                    count++;
                }
            }
            TextView msgNumber = (TextView) findViewById(R.id.request_number);
            Log.v("*************** COUNT *", Integer.toString(count));
            msgNumber.setText(Integer.toString(count));
        }catch(Exception e){
            Log.e("MainActivity",e.toString());
        }


    }

    @Override
    protected void onResume() {
        if (session.userrol==1) {
            checkMessages();
            checkRequest();
        }

        super.onResume();
    }
}
