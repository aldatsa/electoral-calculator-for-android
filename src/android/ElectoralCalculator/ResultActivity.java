package android.ElectoralCalculator;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ResultActivity extends Activity{

	ListView listResults;
	ArrayList<String> listItems = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
     
        listResults = (ListView)findViewById(R.id.listResults);
        
        // Inflate the header for listParties
        LayoutInflater inflater = getLayoutInflater();
        View header = inflater.inflate(R.layout.listresultsheader, (ViewGroup) findViewById(R.id.result_list_header));
        
        // Add the header to listParties
        listResults.addHeaderView(header, null, false); // addHeaderView(view, data, isSelectable);
        
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        listResults.setAdapter(adapter);
        
        for (int pos = 0; pos < Data.listOfParties.size(); pos++) {
        	listItems.add(Data.listOfParties.getPartyName(pos) + " (" + Data.listOfParties.getPartyVotes(pos) +
        			" votes (" + roundTo2Decimals(((double) Data.listOfParties.getPartyVotes(pos) / Data.totalVotes) * 100) + "%))" +
        			": " + Data.listOfParties.getPartySeats(pos) + " seats\n" + "Votes to next seat: " + Data.listOfParties.getVotesToNextSeat(pos));
		}
        adapter.notifyDataSetChanged();
	}
	
	private double roundTo2Decimals(double d) {
    	DecimalFormat twoDForm = new DecimalFormat(getString(R.string.twoDecimalFormat)); // #.## or #,##
    	return Double.valueOf(twoDForm.format(d));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.about_menu:
    		startActivity(new Intent(this, AboutActivity.class));
    		return true;
    	case R.id.settings_menu:
    		startActivity(new Intent(this, PreferencesActivity.class));
    		return true;
    	// More items go here (if any) ...
    	}
    	return false;
    }
}
