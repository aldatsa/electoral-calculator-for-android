package android.ElectoralCalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PartyListAdapter extends BaseAdapter {
    private Context context;

    private PartyList listParties;

    public PartyListAdapter(Context context, PartyList listParties) {
        this.context = context;
        this.listParties = listParties;
    }

    public int getCount() {
        return listParties.size();
    }

    public Object getItem(int position) {
        return listParties.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Party entry = listParties.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listpartiesrow, null);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.txtvwPartyList_party);
        tvName.setText(entry.getName());

        TextView tvVotes = (TextView) convertView.findViewById(R.id.txtvwPartyList_votes);
        tvVotes.setText(String.valueOf(entry.getVotes()));        

        return convertView;
    }
}
