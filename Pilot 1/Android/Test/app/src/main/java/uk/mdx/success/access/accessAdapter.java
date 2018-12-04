package uk.mdx.success.access;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uk.mdx.success.test.R;

/**
 * Created by Josegines1 on 21/03/2018.
 */

public class accessAdapter extends ArrayAdapter {
    private Context mContext;
    private List<Access> accessList = new ArrayList<>();

    public accessAdapter(@NonNull Context context, ArrayList<Access> list) {
        super(context, 0 , list);
        mContext = context;
        accessList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_list,parent,false);

        Access access = accessList.get(position);



//        TextView datetime = (TextView) listItem.findViewById(R.id.acces_user2);
//        datetime.setText(access.getid2user());
        TextView date = (TextView) listItem.findViewById(R.id.date);
      //  date.setVisibility(View.GONE);
        date.setText("Access:");
        TextView state = (TextView) listItem.findViewById(R.id.state);
        state.setText(access.getAccessState());
        TextView message = (TextView) listItem.findViewById(R.id.message);
        message.setText(access.getName());
        if(access.getValue().equals("1")){
            listItem.setBackgroundColor(Color.parseColor("#abff77"));
        }else{
            listItem.setBackgroundColor(Color.parseColor("#fe4b4b"));
        }
        ImageView img = (ImageView) listItem.findViewById(R.id.image);
        img.setImageResource(R.drawable.access_item);

       ;
        return listItem;
    }
}
