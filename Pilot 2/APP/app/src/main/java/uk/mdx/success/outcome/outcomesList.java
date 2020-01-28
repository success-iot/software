package uk.mdx.success.outcome;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import  org.json.JSONObject;
import  org.json.JSONArray;
import android.widget.ListView;
import 	android.widget.ArrayAdapter;
import android.app.ListActivity;
import java.util.ArrayList;
import android.util.Log;
import android.widget.Toast;

import uk.mdx.success.Connection;
import uk.mdx.success.JSON;
import uk.mdx.success.outcome.Outcome;
import uk.mdx.success.outcome.outcomeAdapter;
import uk.mdx.success.test.R;

public class outcomesList extends ListActivity  {
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listOutcomes=new ArrayList<String>();

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

        con = new Connection(Connection.GET,"outcomes", null);
        try{
            String r = con.execute().get();
            title.setText("Outcomes");

            ArrayList<Outcome> outcomes = new ArrayList<>();
            outcomeAdapter outcomesAdapt = new outcomeAdapter(this, outcomes);
            JSONObject jsonO = new JSONObject(r);
            JSONArray array = jsonO.getJSONArray("data");
            if (array.length()==0){
                Toast toast = Toast.makeText(this, "There are not elements to show.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }else{
                for (int i = 0; i < array.length(); i++) {
                    JSONObject row = array.getJSONObject(i);
                    String id = row.getString("idoutcome");
                    String user = row.getString("iduser");
                    String type = row.getString("type");
                    String date = row.getString("datetime");
                    String value = row.getString("value");
                    String context = row.getString("context");
                    String state = row.getString("state");
                    outcomesAdapt.add(new Outcome(id, user, date, type, value, context, state));
                }
                // Set The Adapter
                listOut.setAdapter(outcomesAdapt);
            }
        }catch(Exception e){
            Log.e("Outcomes.java", e.getMessage());
        }
    }




}
