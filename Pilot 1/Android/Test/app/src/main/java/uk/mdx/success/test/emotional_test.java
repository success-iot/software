package uk.mdx.success.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import uk.mdx.success.Connection;
import uk.mdx.success.JSON;
import uk.mdx.success.outcome.Outcome;
import uk.mdx.success.test.R;

import static uk.mdx.success.JSON.*;

public class emotional_test extends AppCompatActivity {
    Connection con;
    Date currentTime;
    SimpleDateFormat dateFormat;
    String response;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotional_test);

        currentTime = Calendar.getInstance().getTime();
        dateFormat= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        con = new Connection(Connection.POST,"outcomes", null);

        TextView title = (TextView)findViewById(R.id.titletext);
        title.setText("Emotional Test");


        RelativeLayout happy = findViewById(R.id.happy_layout);
        happy.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
              //  con.execute("POST","1","Happy",dateFormat.format(currentTime));
                Outcome outcome = new Outcome(null,null,null,"Happy","1");
                try{
                    response = con.execute(outcome.toJson().toString()).get();
                    showResponse();
                }catch (Exception e){
                    Log.e("Emotional test Happy",e.toString());
                }



            }
        });
        RelativeLayout normal = findViewById(R.id.normal_layout);
        normal.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
              //  con.execute("POST","1","Normal",dateFormat.format(currentTime));
                Outcome outcome = new Outcome(null,null,null,"Normal","1");
                try{
                    response = con.execute(outcome.toJson().toString()).get();
                    showResponse();
                }catch (Exception e){
                    Log.e("Emotional test Normal",e.toString());
                }
            }
        });
        RelativeLayout sad = findViewById(R.id.sad_layout);
        sad.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
             //   con.execute("POST","1","Sad",dateFormat.format(currentTime));
                Outcome outcome = new Outcome(null,null,null,"Sad","1");
                try{
                    response = con.execute(outcome.toJson().toString()).get();
                    showResponse();
                }catch (Exception e){
                    Log.e("Emotional test Sad",e.toString());
                }

            }
        });



    }

    private void showResponse(){
        Toast toast = Toast.makeText(this, JSON.getValue(response, "msg"), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        try{
            Thread.sleep(1000);
        }catch (Exception e){

        }finally {
            finish();
        }

    }

}
