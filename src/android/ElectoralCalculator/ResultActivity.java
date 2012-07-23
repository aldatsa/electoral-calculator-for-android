package android.ElectoralCalculator;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ResultActivity extends Activity{

	ListView listResults;
	TextView textView1;
	ArrayList<String> listItems = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        
        textView1 = (TextView)findViewById(R.id.textView1);
     
        listResults = (ListView)findViewById(R.id.listResults);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        listResults.setAdapter(adapter);
        
        for (String s: MainActivity.results.keySet())
		{
			listItems.add(s + " (" + MainActivity.votes.get(s) + " votes (" + roundTo2Decimals(((double) MainActivity.votes.get(s) / MainActivity.totalVotes) * 100) + "%))" + ": " + MainActivity.results.get(s) + " seats\n");
		}
        adapter.notifyDataSetChanged();
	}
	
	private double roundTo2Decimals(double d)
    {
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
    		startActivity(new Intent(this, About.class));
    		return true;
    	// More items go here (if any) ...
    	}
    	return false;
    }
}
