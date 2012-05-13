package android.ElectoralCalculator;

import java.util.ArrayList;
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
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	int seats;
	
	//TextView textView1;
	EditText editParty;
	EditText editVotes;
	EditText editSeats;
	
	Button buttonAddParty;
	Button buttonCalculate;
	
	ListView listParties;
	ArrayList<String> listItems = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	
	public static Map<String, Integer> votes = new HashMap<String, Integer>();
	public static Map<String, Integer> results = new HashMap<String, Integer>();
	public static int totalVotes = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //textView1 = (TextView)findViewById(R.id.textView1);
        editParty = (EditText)findViewById(R.id.editParty);
        editVotes = (EditText)findViewById(R.id.editVotes);
        editSeats = (EditText)findViewById(R.id.editSeats);
        
        buttonAddParty = (Button)findViewById(R.id.buttonAddParty);
        buttonCalculate = (Button)findViewById(R.id.buttonCalculate);
            
        listParties = (ListView)findViewById(R.id.listParties);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        listParties.setAdapter(adapter);
        
        buttonAddParty.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String strNewParty = editParty.getText().toString();
				String strNewVotes = editVotes.getText().toString();
				
				/* If editParty and editVotes are not empty,
				 * then add the party to the map and to the arraylist
				 * and notify the adapter that there is new data
				 */
				if (strNewParty.length() > 0 && strNewVotes.length() > 0)
				{
					votes.put(strNewParty, Integer.parseInt(strNewVotes));
				
					listItems.add(strNewParty + ": " + strNewVotes);
					adapter.notifyDataSetChanged();
					
					// Clean the EditTexts
					editParty.setText("");
					editVotes.setText("");
					
					editParty.requestFocus();
				}
				/* Else if both EditTexts are empty
				 * use a toast notification to warn the user
				 */
				else if (strNewParty.length() == 0 && strNewVotes.length() == 0)
				{
					Context context = getApplicationContext();
					CharSequence text = getString(R.string.toastPartyVotesEmpty);
					int duration = Toast.LENGTH_SHORT;
					
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
				/* Else if editParty is empty
				 * use a toast notification to warn the user
				 */
				else if (strNewParty.length() == 0)
				{
					Context context = getApplicationContext();
					CharSequence text = getString(R.string.toastPartyEmpty);
					int duration = Toast.LENGTH_SHORT;
					
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
				/* Else if editVotes is empty
				 * use a toast notification to warn the user
				 */
				else if (strNewVotes.length() == 0)
				{
					Context context = getApplicationContext();
					CharSequence text = getString(R.string.toastVotesEmpty);
					int duration = Toast.LENGTH_SHORT;
					
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
			}
		});
        
        buttonCalculate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String strSeats = editSeats.getText().toString();
				
				/* If EditSeats is not empty 
				 * and there are elements in the votes Map 
				 */
				if (strSeats.length() > 0 && !votes.isEmpty())
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
				else if (votes.isEmpty())
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
		
		for (String s: votes.keySet())
		{
			int tempVotes = votes.get(s);
			int[] distributionFigures = new int[seats];
			
			totalVotes = totalVotes + tempVotes;
			
			for (int i = 0; i < seats; i++)
			{
				distributionFigures[i] = tempVotes / (i +1);
			}
			dhondt.put(s, distributionFigures);
		}
		
		for (String s: votes.keySet())
		{
			results.put(s, 0);
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
			
			results.put(highestParty, results.get(highestParty) + 1);
			
			int tempDistributionFigures[] = dhondt.get(highestParty);
			System.arraycopy(tempDistributionFigures, 1, tempDistributionFigures, 0, (seats - 1));
			
			dhondt.put(highestParty, tempDistributionFigures);
			
		}
		
		Intent intent = new Intent(MainActivity.this, ResultActivity.class);
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