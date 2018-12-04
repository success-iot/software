package uk.mdx.success.access;

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
import uk.mdx.success.access.Access;
import uk.mdx.success.access.accessActivity;
import uk.mdx.success.access.accessAdapter;
import uk.mdx.success.session;
import uk.mdx.success.test.R;

public class accessList extends ListActivity  {
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
   // ArrayList<String> listOutcomes=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;
    Connection con;
    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
    int clickCounter=0;
    static Activity activity ;
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
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("Method", "First Value");
//        map.put("second", "Second Value");

        con = new Connection(Connection.GET,"access", null);
        try{
            String r = con.execute().get();
            title.setText("Access List");

            final ArrayList<Access> access = new ArrayList<>();
            accessAdapter accessAdapt = new accessAdapter(this, access);
            JSONObject jsonO = new JSONObject(r);
            JSONArray array = jsonO.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                JSONObject row = array.getJSONObject(i);
                String id = row.getString("idaccess");
                String user = row.getString("iduser");
                String user2 = row.getString("id2user");
                String value = row.getString("value");
                String name = row.getString("name");
                access.add(new Access(id, user, user2, value, name));
            }
            // Set The Adapter
            listOut.setAdapter(accessAdapt);

            if (session.userrol==1) {
                listOut.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String idaccess = access.get(position).getId();
                        Intent intent = new Intent(activity, accessActivity.class);
                        intent.putExtra("idaccess", idaccess);
                        intent.putExtra("userrequest", access.get(position).getid2user());
                        intent.putExtra("value",  access.get(position).getValue());
                        startActivity(intent);

                    }
                });
            }


            // tv.setText(r);
        }catch(Exception e){
            Log.e("accessList.java", e.getMessage());

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.onCreate(null);
    }
}
