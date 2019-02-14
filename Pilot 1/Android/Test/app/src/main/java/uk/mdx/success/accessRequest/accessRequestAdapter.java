package uk.mdx.success.accessRequest;

import android.content.Context;
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

import uk.mdx.success.session;
import uk.mdx.success.test.R;

/**
 * Created by Josegines1 on 21/03/2018.
 */

public class accessRequestAdapter extends ArrayAdapter {
    private Context mContext;
    private List<accessRequest> requestList = new ArrayList<>();

    public accessRequestAdapter(@NonNull Context context, ArrayList<accessRequest> list) {
        super(context, 0 , list);
        mContext = context;
        requestList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_list,parent,false);

        accessRequest request = requestList.get(position);



        TextView datetime = (TextView) listItem.findViewById(R.id.date);
        datetime.setText(request.getDateTime());
        TextView type = (TextView) listItem.findViewById(R.id.state);
//        type.setText(request.getState());
        // If primary user show the name.
        type.setText(request.getStateMeaning());
        if(session.userrol==1){
            TextView value = (TextView) listItem.findViewById(R.id.message);
            value.setText(request.getName());
        }else{ //Second user not show the name
            TextView value = (TextView) listItem.findViewById(R.id.message);
            value.setText(request.getName());
        }
        ImageView img = (ImageView) listItem.findViewById(R.id.image);
        img.setImageResource(R.drawable.request_item);

        return listItem;
    }
}
