package uk.mdx.success.message;

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

import uk.mdx.success.message.Message;
import uk.mdx.success.test.R;

/**
 * Created by Josegines1 on 21/03/2018.
 */

public class messagesAdapter extends ArrayAdapter {
    private Context mContext;
    private List<Message> messageList = new ArrayList<>();

    public messagesAdapter(@NonNull Context context, ArrayList<Message> list) {
        super(context, 0 , list);
        mContext = context;
        messageList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_list,parent,false);

        Message currentMessage = messageList.get(position);

//        ImageView image = (ImageView)listItem.findViewById(R.id.imageView_poster);
//        image.setImageResource(currentMovie.getmImageDrawable());
//
//        TextView name = (TextView) listItem.findViewById(R.id.textView_name);
//        name.setText(currentMovie.getmName());

        TextView datetime = (TextView) listItem.findViewById(R.id.date);
        datetime.setText(currentMessage.getDateTime());
        TextView type = (TextView) listItem.findViewById(R.id.state);
        type.setText(currentMessage.getType());
        TextView value = (TextView) listItem.findViewById(R.id.message);
        value.setText(currentMessage.getBody());
        ImageView img = (ImageView) listItem.findViewById(R.id.image);
        img.setImageResource(R.drawable.message_item);


        return listItem;
    }
}
