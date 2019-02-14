package uk.mdx.success.message;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import uk.mdx.success.Connection;
import uk.mdx.success.message.Message;
import uk.mdx.success.message.messageActivity;
import uk.mdx.success.message.messagesAdapter;
import uk.mdx.success.test.R;

public class messagesList extends ListActivity  {
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<Message> messages;
    static Activity activity ;
    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;
    Connection con;
    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
    int clickCounter=0;

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
        title.setText("Messages Unreaded");
        activity = this;
        con = new Connection(Connection.GET,"messages", null);
        try{
            String r = con.execute().get();
         //   title.setText("Messages");

            messages = new ArrayList<>();

            messagesAdapter messagesAdapt = new messagesAdapter(this, messages);

            JSONObject jsonO = new JSONObject(r);
            JSONArray array = jsonO.getJSONArray("data");
            if (array.length()==0){
                Toast toast = Toast.makeText(this, "There are not elements to show.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }else {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject row = array.getJSONObject(i);
                    String id = row.getString("idmessage");
                    String user = row.getString("iduser");
                    String type = row.getString("type");
                    String date = row.getString("date");
                    String body = row.getString("body");

                    messagesAdapt.add(new Message(id, user, date, type, body));
                    // String check = id + " " + user + " " + type + " " + type + " " + date + " " + body;
                    //  Log.v("meirda",check);
                }
                // Set The Adapter
                listOut.setAdapter(messagesAdapt);
                listOut.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String idmessage = messages.get(position).getId();
                        String body = messages.get(position).getBody();
                        String type = messages.get(position).getType();
                        Intent intent = new Intent(activity, messageActivity.class);
                        intent.putExtra("idmessage", idmessage);
                        intent.putExtra("type", type);
                        intent.putExtra("body", body);
                        //based on item add info to intent
                        startActivity(intent);

                    }
                });
            }
        }catch(Exception e){
            Log.e("messageList.java", e.getMessage());

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        this.onCreate(null);
    }


}
