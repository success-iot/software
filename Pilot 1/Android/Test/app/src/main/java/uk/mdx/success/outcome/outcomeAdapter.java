package uk.mdx.success.outcome;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import uk.mdx.success.outcome.Outcome;
import uk.mdx.success.test.R;

/**
 * Created by Josegines1 on 21/03/2018.
 */

public class outcomeAdapter extends ArrayAdapter {
    private Context mContext;
    private List<Outcome> outcomeList = new ArrayList<>();

    public outcomeAdapter(@NonNull Context context, ArrayList<Outcome> list) {
        super(context, 0 , list);
        mContext = context;
        outcomeList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false);

        Outcome currentOutcome = outcomeList.get(position);

        String text = "";
        TextView datetime = (TextView) listItem.findViewById(R.id.date);
        datetime.setText(currentOutcome.getDateTime());
        TextView type = (TextView) listItem.findViewById(R.id.message);
        type.setText(currentOutcome.getContext());
        TextView value = (TextView) listItem.findViewById(R.id.state);
//        value.setText(currentOutcome.getValueMeaning());
        Log.v("Gines outcomes", currentOutcome.getType());
        switch(currentOutcome.getType()){
            case "1":
                if (currentOutcome.getValue().equalsIgnoreCase("1")){
                    text = " Activity Start ";
                } else {
                    text = " Activity Stops";
                }
                break;
            case "2":
                if (currentOutcome.getValue().equalsIgnoreCase("1")){
                    text = " Warning ";
                }
                break;
            case "3":
                if (currentOutcome.getValue().equalsIgnoreCase("1")){
                    text = " Alert ";
                }
                break;
            case "4":
                if (currentOutcome.getValue().equalsIgnoreCase("1")){
                    text = " User's feedback ";
                }
                break;
            default:
                text="--";
        }

        value.setText(text);
        ImageView img = (ImageView) listItem.findViewById(R.id.image);
        img.setImageResource(R.drawable.outcome_item);


        return listItem;
    }
}
