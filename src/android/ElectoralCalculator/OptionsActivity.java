package android.ElectoralCalculator;

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
	EditText editSeats;
	Button buttonCalculate;
	
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
				if (strSeats.length() > 0 && !Data.votes.isEmpty())
				{
			        // Make sure that the hash map used for the results is empty
			        Data.results.clear();
			        // and the total number of votes is 0
			        Data.totalVotes = 0;
			        
			        // Get the number of seats
					Data.seats = Integer.parseInt(strSeats);
					
					// Calculate total votes
					Data.totalVotes = Data.calculateTotalVotes();
					
					// Calculate and show the results
					Data.results = Methods.calculateHighestAverage(Data.seats, Data.votes);
					
					// Launch ResultActivity to show the results
					Intent intent = new Intent(OptionsActivity.this, ResultActivity.class);
					startActivity(intent);
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
				else if (Data.votes.isEmpty())
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