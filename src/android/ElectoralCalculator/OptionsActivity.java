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
	
	private static String method = "D'Hondt";
	
	public static String getCalculationMethod()
	{
		return method;
	}
	
	public static void setCalculationMethod(String calculationMethod)
	{
		method = calculationMethod;
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
		Map<String, double[]> distributionFigures = new HashMap<String, double[]>();
		
		for (String s: PartyListActivity.votes.keySet())
		{
			// Get the number of votes of the current party
			int tempVotes = PartyListActivity.votes.get(s);
			
			// Create an array for the distribution figures of the current party
			double[] tmpDistributionFigures = new double[seats];
			
			// Add the votes of the current party to totalVotes
			PartyListActivity.totalVotes = PartyListActivity.totalVotes + tempVotes;
			
			// Calculate the distribution figures for the current party
			for (int i = 0; i < seats; i++)
			{
				// tempVotes and i are ints, I use 1.0 to make sure that the result
				// of the division is a double
				tmpDistributionFigures[i] = tempVotes / (i + 1.0); 
			}
			
			// Put the calculated distribution figures in the distributionFigures hash map 
			distributionFigures.put(s, tmpDistributionFigures);
		}
		
		// Reset to 0 the seats of all the parties
		for (String s: PartyListActivity.votes.keySet())
		{
			PartyListActivity.results.put(s, 0);
		}
		
		// Variables to store the name and the votes of the
		// most voted party in each round
		double highest = 0.0;
		String highestParty = "";
		
		// Allocate the seats that correspond to each party
		for (int allocated = 0; allocated < seats; allocated++)
		{
			// Reset the temporal variables
			highest = 0;
			highestParty = "";
			
			// Find the highest value in this round
			for (String s: distributionFigures.keySet())
			{	
				double next;
				
				// The next distribution figure of the current party
				next = distributionFigures.get(s)[0];
				
				// If the distribution figure is bigger than the highest
				// it becomes the new highest value/party
				if (next > highest)
				{
					highest = next;
					highestParty = s;
				}
			}
			
			// Add one seat to the party that got the highest distribution figure in this round
			PartyListActivity.results.put(highestParty, PartyListActivity.results.get(highestParty) + 1);
			
			// NOTE: To remove the highest distribution figure from the party that got the last seat
			// I have to use arraycopy, as arrays are fixed in size in Java. 
			// It would be better to use ListArrays
			
			// Copy the distribution figures of the party that got the highest distribution in this round
			// to a temporal array
			double[] tempDistributionFigures = distributionFigures.get(highestParty);
			
			// Use arraycopy to create a new array with the highest value removed
			System.arraycopy(tempDistributionFigures, 1, tempDistributionFigures, 0, (seats - 1));
			
			// Substitute the last value with 0, else is equal to the previous value in the array
			tempDistributionFigures[seats - 1] = 0.0;
			
			// Put the modified array back in the distributionFigures array
			distributionFigures.put(highestParty, tempDistributionFigures);			
		}
		
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
    	// More items go here (if any) ...
    	}
    	return false;
    }
}