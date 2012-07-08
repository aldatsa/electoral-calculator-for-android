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

	//TextView textView1;
	EditText editParty;
	EditText editVotes;
	
	Button buttonContinue;
	Button buttonAddParty;
	
	public static Map<String, Integer> votes = new HashMap<String, Integer>();
	public static Map<String, Integer> results = new HashMap<String, Integer>();
	public static int totalVotes = 0;
	
	ListView listParties;
	public static ArrayList<String> listItems = new ArrayList<String>();
	public static ArrayAdapter<String> adapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //textView1 = (TextView)findViewById(R.id.textView1);
        editParty = (EditText)findViewById(R.id.editParty);
        editVotes = (EditText)findViewById(R.id.editVotes);
        
        listParties = (ListView)findViewById(R.id.listParties);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        listParties.setAdapter(adapter);
        
        buttonContinue = (Button)findViewById(R.id.buttonContinue);

        buttonContinue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, PartyListActivity.class);
				startActivity(intent);
			}
        });
        
        buttonAddParty = (Button)findViewById(R.id.buttonAddParty);
                    
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
					//PartyListActivity.adapter.notifyDataSetChanged();
					
					// Clean the EditTexts
					editParty.setText("");
					editVotes.setText("");
					
					editParty.requestFocus();
					
					//Intent intent = new Intent(MainActivity.this, PartyListActivity.class);
					//startActivity(intent);
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
    }
}