package android.ElectoralCalculator;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class OptionsActivity extends Activity
{
	int seats;
	
	EditText editSeats;
	Button buttonCalculate;
	
	private int calculateTotalVotes() {
		int tmpTotalVotes = 0;
		for (String s: PartyListActivity.votes.keySet()) {
			tmpTotalVotes = tmpTotalVotes + PartyListActivity.votes.get(s);
		}
		return tmpTotalVotes;
	}
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);
        
        // Edit text view for the number of seats
		editSeats = (EditText)findViewById(R.id.editSeats);

		// Spinner to select the method of calculation
        Spinner spinnerMethods = (Spinner) findViewById(R.id.spinnerMethods);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
        		this, R.array.methods_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMethods.setAdapter(adapter);
        spinnerMethods.setOnItemSelectedListener(new OnMethodItemSelectedListener());

        // When pressed calculates the results and shows ResultActivity
		buttonCalculate = (Button)findViewById(R.id.buttonCalculate);

		buttonCalculate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String strSeats = editSeats.getText().toString();
				
				/* If EditSeats is not empty 
				 * and there are elements in the votes Map 
				 */
				if (strSeats.length() > 0 && !PartyListActivity.votes.isEmpty())
				{
			        // Make sure that the hash map used for the results is empty
			        PartyListActivity.results.clear();
			        // and the total number of votes is 0
			        PartyListActivity.totalVotes = 0;
			        
			        // Get the number of seats
					seats = Integer.parseInt(strSeats);
					
					// Calculate total votes
					PartyListActivity.totalVotes = calculateTotalVotes();
					
					// Calculate and show the results
					calculateAndShow();
				}
				/*
				 * else if the number of seats is empty show a toast
				 */
				else if (strSeats.length() == 0)
				{
					Context context = getApplicationContext();
					CharSequence text = getString(R.string.toastSeatsEmpty);
					int duration = Toast.LENGTH_SHORT;
					
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
				/*
				 * else if the list of parties (name -> votes) is empty show a toast
				 * and go back to PartyListActivity
				 */
				else if (PartyListActivity.votes.isEmpty())
				{
					Context context = getApplicationContext();
					CharSequence text = getString(R.string.toastVotesMapEmpty);
					int duration = Toast.LENGTH_SHORT;
					
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
					
					Intent intent = new Intent(OptionsActivity.this, PartyListActivity.class);
					startActivity(intent);
				}
			}
		});
    }

    public void calculateAndShow()
    {
    	double highest = 0.0;
    	String seatTo = "";
    	Map<String, Double> lastQuot = new HashMap<String, Double>();
    	Map<String, Integer> nextSeat = new HashMap<String, Integer>();
    	
		//Map<String, Integer> results = new HashMap<String, Integer>();
		
    	// Clear the hash map results
    	PartyListActivity.results.clear();
    	
		// Initialize the results hash map for the parties in votes
		for (String s: PartyListActivity.votes.keySet())
		{
			// They start with 0 seats each
			PartyListActivity.results.put(s, 0);
		}
		
		// Calculate the number of seats for each party
		for (int i = 1; i <= seats; i++) {
			// Highest value in this round (reset it to 0 in each round)
			highest = 0.0;
			
			// Who gets the seat in this round (reset it to "" in each round)
			seatTo = "";
			
			for (String s: PartyListActivity.votes.keySet()) {
				// TODO: I should take into account if the party's vote percentage is bigger than the threshold
				// Calculate the quot for this party in this round
				double quot = PartyListActivity.votes.get(s).doubleValue() / Methods.getDivisor(PartyListActivity.results.get(s), Methods.getCalculationMethod());
				
				// If the quot is bigger than the highest value in this round
				if (quot > highest) {
					// the party becomes the candidate to get this seat
					seatTo = s;
					// Save the quot to check it with the values for the rest of parties
					highest = quot;
				// else if the quot is equal to the highest value in this round
				} else if (quot == highest) {
					// The seat goes to the party that has more votes
					if (PartyListActivity.votes.get(s) > PartyListActivity.votes.get(seatTo)) {
						seatTo = s;
						highest = quot;
					}
				}
				
				// Save the last quots to calculate the number of extra votes needed to get the last seat
				if (i == seats) {
					lastQuot.put(s, quot);
				}
			}
			// The party with the highest quot gets another seat
			PartyListActivity.results.put(seatTo, PartyListActivity.results.get(seatTo) + 1);
		}
		
		/* TODO: Complete this
		for (String s: PartyListActivity.votes.keySet()) {
			// The party that got the last seat needs 0 votes to get the last seat
			if (s == seatTo) {
				nextSeat.put(s, 0);
			// The rest of parties need to get a bigger quot than the party that got the last seat
			} else {
				nextSeat.put(s, );
			}
		}
		*/
		Intent intent = new Intent(OptionsActivity.this, ResultActivity.class);
		startActivity(intent);
		
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