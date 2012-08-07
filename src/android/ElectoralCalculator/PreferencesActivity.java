package android.ElectoralCalculator;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class PreferencesActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // register preference change listener
        prefs.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		// Update the value when the preferences change
		if (key.equals("MSLFDpref")) {
            Methods.setMSLFD(Double.parseDouble(sharedPreferences.getString("MSLFDpref", "1.4")));
        }
	}
}
