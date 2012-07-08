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


public class PartyListActivity extends Activity
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
        setContentView(R.layout.partylist);
        
		editSeats = (EditText)findViewById(R.id.editSeats);
		
		buttonCalculate = (Button)findViewById(R.id.buttonCalculate);

        Spinner spinnerMethods = (Spinner) findViewById(R.id.spinnerMethods);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
        		this, R.array.methods_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMethods.setAdapter(adapter);
        spinnerMethods.setOnItemSelectedListener(new OnMethodItemSelectedListener());

		buttonCalculate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String strSeats = editSeats.getText().toString();
				
				/* If EditSeats is not empty 
				 * and there are elements in the votes Map 
				 */
				if (strSeats.length() > 0 && !MainActivity.votes.isEmpty())
				{
					seats = Integer.parseInt(strSeats);
					calculateAndShow();
				}
				else if (strSeats.length() == 0)
				{
					Context context = getApplicationContext();
					CharSequence text = getString(R.string.toastSeatsEmpty);
					int duration = Toast.LENGTH_SHORT;
					
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
				else if (MainActivity.votes.isEmpty())
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
		
		for (String s: MainActivity.votes.keySet())
		{
			int tempVotes = MainActivity.votes.get(s);
			int[] distributionFigures = new int[seats];
			
			MainActivity.totalVotes = MainActivity.totalVotes + tempVotes;
			
			for (int i = 0; i < seats; i++)
			{
				distributionFigures[i] = tempVotes / (i +1);
			}
			dhondt.put(s, distributionFigures);
		}
		
		for (String s: MainActivity.votes.keySet())
		{
			MainActivity.results.put(s, 0);
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
			
			MainActivity.results.put(highestParty, MainActivity.results.get(highestParty) + 1);
			
			int tempDistributionFigures[] = dhondt.get(highestParty);
			System.arraycopy(tempDistributionFigures, 1, tempDistributionFigures, 0, (seats - 1));
			
			dhondt.put(highestParty, tempDistributionFigures);
			
		}
		
		Intent intent = new Intent(PartyListActivity.this, ResultActivity.class);
		startActivity(intent);
		
		//listItems.clear();
		/*
		for (String s: results.keySet())
		{
			listItems.add(s + " (" + votes.get(s) + " votes (" + roundTo2Decimals(((double) votes.get(s) / totalVotes) * 100) + "%))" + ": " + results.get(s) + " seats\n");
		}
		*/
		//adapter.notifyDataSetChanged();
    }
}