package android.ElectoralCalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
	// Number of seats
	public static int seats;
	
	// Used to store the total number of votes
	public static int totalVotes;

	// Used to store the list of parties (name of the party -> number of votes)
	public static Map<String, Integer> votes = new HashMap<String, Integer>();
	
	// Used to store the results (name of the party -> number of seats)
	public static Map<String, Integer> results = new HashMap<String, Integer>();
	
	// Used to store the list of parties using Party objects(name of the party, number of votes)
	public static List<Party> listOfParties = new ArrayList<Party>();
	
	public static int calculateTotalVotes() {
		int tmpTotalVotes = 0;

		for (int pos = 0; pos < listOfParties.size(); pos++) {
			tmpTotalVotes = tmpTotalVotes + listOfParties.get(pos).getVotes();
		}
		return tmpTotalVotes;
	}
}
