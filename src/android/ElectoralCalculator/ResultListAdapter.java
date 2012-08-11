package android.ElectoralCalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ResultListAdapter extends BaseAdapter implements OnClickListener {
    private Context context;

    private PartyList listResults;

    public ResultListAdapter(Context context, PartyList listResults) {
        this.context = context;
        this.listResults = listResults;
    }

    public int getCount() {
        return listResults.size();
    }

    public Object getItem(int position) {
        return listResults.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Party entry = listResults.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listresultsrow, null);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.result_list_row_name);
        tvName.setText(entry.getName());

        TextView tvVotes = (TextView) convertView.findViewById(R.id.result_list_row_votes);
        tvVotes.setText(String.valueOf(entry.getVotes()));
        
        TextView tvVotePercent = (TextView) convertView.findViewById(R.id.result_list_row_vote_percents);
        tvVotePercent.setText(String.valueOf(entry.getVotePercent()));

        TextView tvSeats = (TextView) convertView.findViewById(R.id.result_list_row_seats);
        tvSeats.setText(String.valueOf(entry.getSeats()));

        TextView tvNextSeat = (TextView) convertView.findViewById(R.id.result_list_row_next_seat);
        tvNextSeat.setText(String.valueOf(entry.getVotesToNextSeat()));
        
        return convertView;
    }
	
    @Override
    public void onClick(View view) {
        Party entry = (Party) view.getTag();
        listResults.remove(entry);
        notifyDataSetChanged();

    }

    private void showDialog(Party entry) {
        // Create and show your dialog
        // Depending on the Dialogs button clicks delete it or do nothing
    }

}
