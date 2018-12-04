package uk.mdx.success.message;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import uk.mdx.success.Connection;
import uk.mdx.success.session;
import uk.mdx.success.test.R;

/**
 * Created by Josegines1 on 21/03/2018.
 */

public class messageActivity extends Activity {

    Connection con;
    String key;
    String body;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (session.userrol==1){
            setContentView(R.layout.message_item);
            TextView title = (TextView) findViewById(R.id.titletext);
            title.setText("Message");
            TextView body = (TextView) findViewById(R.id.message_body);

            TextView code = (TextView) findViewById(R.id.message_code);
            Bundle b = getIntent().getExtras();
            this.key = b.getString("idmessage");
            this.body = b.getString("body");
            this.type = b.getString("type");
            code.setText("Security Code: " + this.type);
            body.setText(this.body);

        }

    }


    protected void cancel(View v){
        finish();
    }

    protected void markReaded(View v){
        try {
            Message rac = new Message(this.key,null,null,null, null);
            Log.v("Reques PUT READED", rac.toJson().toString());
            this.con = new Connection(Connection.PUT,"messages",key);
            //this.con.setMethod(Connection.PUT);
            String response = con.execute(rac.toJson().toString()).get();
            Log.v("Connection PUT response", "************* " + response + " ***********");
            finish();
        }catch(Exception e){
            Log.e("messageActivity.java", e.getMessage());
        }
    }
}
