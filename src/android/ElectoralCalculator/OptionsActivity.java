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
				 * IT SHOULD GO BACK TO PARTYLISTACTIVITY!!
				 */
				else if (PartyListActivity.votes.isEmpty())
				{
					Context context = getApplicationContext();
					CharSequence text = getString(R.string.toastVotesMapEmpty);
					int duration = Toast.LENGTH_SHORT;
					
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
			}
		});
    }
    public void calculateAndShow()
    {
		Map<String, int[]> dhondt = new HashMap<String, int[]>();
		
		for (String s: PartyListActivity.votes.keySet())
		{
			int tempVotes = PartyListActivity.votes.get(s);
			int[] distributionFigures = new int[seats];
			
			PartyListActivity.totalVotes = PartyListActivity.totalVotes + tempVotes;
			
			for (int i = 0; i < seats; i++)
			{
				distributionFigures[i] = tempVotes / (i +1);
			}
			dhondt.put(s, distributionFigures);
		}
		
		for (String s: PartyListActivity.votes.keySet())
		{
			PartyListActivity.results.put(s, 0);
		}
		
		for (int allocated = 0; allocated < seats; allocated++)
		{
			
			int highest = 0;
			String highestParty = "";
			
			for (String s: dhondt.keySet())
			{	
				int next;
				
				next = dhondt.get(s)[0];
				
				if (next > highest)
				{
					highest = next;
					highestParty = s;
				}
			}
			
			PartyListActivity.results.put(highestParty, PartyListActivity.results.get(highestParty) + 1);
			
			int tempDistributionFigures[] = dhondt.get(highestParty);
			System.arraycopy(tempDistributionFigures, 1, tempDistributionFigures, 0, (seats - 1));
			
			dhondt.put(highestParty, tempDistributionFigures);			
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