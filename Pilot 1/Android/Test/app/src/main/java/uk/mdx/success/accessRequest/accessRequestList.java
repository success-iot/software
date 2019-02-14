package uk.mdx.success.accessRequest;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import uk.mdx.success.Connection;
import uk.mdx.success.session;
import uk.mdx.success.test.R;

public class accessRequestList extends ListActivity  {
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<accessRequest> requests;

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;
    Connection con;
    static Activity activity ;
    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
    int clickCounter=0;
//    String userRequest ; //user request access
//    String userid ; //primary user
//    String name;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        TextView title = (TextView) findViewById(R.id.titletext);
        ListView listOut = (ListView) findViewById(android.R.id.list);
        activity = this;
       // con = new Connection("accessRequest", null);
        con = new Connection(Connection.GET, "accessRequest", null);
        try{
            String r = con.execute().get();
            title.setText("Access Requests");

          requests = new ArrayList<>();
          accessRequestAdapter requestAdapt = new accessRequestAdapter(this, requests);

          JSONObject jsonO = new JSONObject(r);
          JSONArray array = jsonO.getJSONArray("data");

          for (int i = 0; i < array.length(); i++) {
                JSONObject row = array.getJSONObject(i);
                String id = row.getString("idrequest");
                String userRequest = row.getString("iduser");
                String userid = row.getString("id1user");
                String name = row.getString("name");
                String state = row.getString("accept");
                String date = row.getString("datetime");
                Log.v("!!", "**" + state + "**");
                if(state.equals("0") && (session.userrol==1) ) {
                  //  Log.v("BIIIENNN ", state);
                    // Primary user see just the pending request
                    requestAdapt.add(new accessRequest(id, userid, userRequest, state, date,name));
                }else{
                    if ((session.userrol!=1)){
                        requestAdapt.add(new accessRequest(id, userid, userRequest, state, date,name));
                    }
                }
                //results.add("User " + user + " request view data of " + user1 + " at " + date + ". Estate: " + state);
          }
            // Set The Adapter
          listOut.setAdapter(requestAdapt);

          if (session.userrol==1) {
                listOut.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String idRequest = requests.get(position).getId();
                        Intent intent = new Intent(activity, accessRequestActivity.class);
                        intent.putExtra("idRequest", idRequest);
                       intent.putExtra("id2user", requests.get(position).getRequestUserid());
                        intent.putExtra("iduser",  requests.get(position).getUserid());
//                        intent.putExtra("usermane",  requests.get(position).getName());
                        //based on item add info to intent

                        startActivity(intent);

                    }
                });
            }


            // tv.setText(r);
        }catch(Exception e){
            Log.e("Request.java", e.getMessage());

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.onCreate(null);
    }


}
