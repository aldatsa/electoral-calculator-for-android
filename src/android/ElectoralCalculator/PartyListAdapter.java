package android.ElectoralCalculator;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PartyListAdapter extends BaseAdapter implements OnClickListener {
    private Context context;

    private List<Party> listParties;

    public PartyListAdapter(Context context, List<Party> listParties) {
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

    @Override
    public void onClick(View view) {
        Party entry = (Party) view.getTag();
        listParties.remove(entry);
        notifyDataSetChanged();

    }

    private void showDialog(Party entry) {
        // Create and show your dialog
        // Depending on the Dialogs button clicks delete it or do nothing
    }

}
