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
import android.widget.ListView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
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
        
        listParties = (ListView)findViewById(R.id.listParties);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        listParties.setAdapter(adapter);
        
        buttonContinue = (Button)findViewById(R.id.buttonContinue);

        buttonContinue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (votes.isEmpty()) {
    				Context context = getApplicationContext();
    				CharSequence text = getString(R.string.toastPartyListEmpty);
    				int duration = Toast.LENGTH_SHORT;
    				
    				Toast toast = Toast.makeText(context, text, duration);
    				toast.show();
				} else {
					Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
					startActivity(intent);
				}
			}
        });
        
        buttonAddParty = (Button)findViewById(R.id.buttonAddParty);
                    
        buttonAddParty.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, AddParty.class);
				startActivity(intent);
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
    		startActivity(new Intent(this, About.class));
    		return true;
    	// More items go here (if any) ...
    	}
    	return false;
    }
}