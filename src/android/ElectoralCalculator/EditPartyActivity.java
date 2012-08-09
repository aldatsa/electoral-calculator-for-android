package android.ElectoralCalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// TODO: Create a parent class for EditPartyActivity and AddPartyActivity with the common functionality

public class EditPartyActivity extends Activity {
	
	EditText editParty;
	EditText editVotes;
	Button buttonAddNewParty;
	Button buttonCancelAddNewParty;
	int list_position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addparty);

		Bundle extras = getIntent().getExtras(); 
		if(extras !=null) {
			// Get the position of the selected party in the list listOfParties from PartyListActivity
		    list_position = extras.getInt("POS");
		}

        editParty = (EditText)findViewById(R.id.editParty);
        
        // Get the name of the party selected in PartyListActivity using its position on the list listOfParties
        editParty.setText(Data.listOfParties.get(list_position).getName().toString());
        
        editVotes = (EditText)findViewById(R.id.editVotes);
        
        // Get the name of the party selected in PartyListActivity using its position on the list listOfParties
        editVotes.setText(Integer.toString(Data.listOfParties.get(list_position).getVotes()));

        buttonAddNewParty = (Button)findViewById(R.id.buttonAddNewParty);
        
        buttonAddNewParty.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			String strNewParty = editParty.getText().toString();
    			String strNewVotes = editVotes.getText().toString();
    			int intNewVotes = Integer.parseInt(editVotes.getText().toString());
    			
    			/* If editParty and editVotes are not empty,
    			 * then edit the values of the selected party on the map and the arraylist
    			 * and notify the adapter that there is new data
    			 */
    			if (strNewParty.length() > 0 && strNewVotes.length() > 0)
    			{
    				Data.votes.put(strNewParty, Integer.parseInt(strNewVotes));

    				Data.listOfParties.set(list_position, new Party(strNewParty, intNewVotes));
    				
    				// Clean the EditTexts
    				editParty.setText("");
    				editVotes.setText("");
    				
    				editParty.requestFocus();
    				
    				Intent intent = new Intent(EditPartyActivity.this, PartyListActivity.class);
    				startActivity(intent);
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
    
        buttonCancelAddNewParty = (Button)findViewById(R.id.buttonCancelAddNewParty);
        
        buttonCancelAddNewParty.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
				Intent intent = new Intent(EditPartyActivity.this, PartyListActivity.class);
				startActivity(intent);
    		}
        });
	}
}