package android.ElectoralCalculator;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class OnMethodItemSelectedListener implements OnItemSelectedListener 
{	
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) 
	{
		OptionsActivity.setCalculationMethod(Methods.values()[pos]);
		
		Toast.makeText(parent.getContext(), "The selected method is " +
			OptionsActivity.getCalculationMethod(), Toast.LENGTH_LONG).show();
	}
	
	public void onNothingSelected(AdapterView<?> parent)
	{
		// Do nothing
	}
}
