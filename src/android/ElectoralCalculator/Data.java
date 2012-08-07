package android.ElectoralCalculator;

import java.util.HashMap;
import java.util.Map;

public class Data {
	// Number of seats
	public static int seats;
	
	// Used to store the list of parties (name of the party -> number of votes)
	public static Map<String, Integer> votes = new HashMap<String, Integer>();
}
