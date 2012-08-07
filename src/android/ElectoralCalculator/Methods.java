package android.ElectoralCalculator;

import java.util.HashMap;
import java.util.Map;

public enum Methods {
	DHONDT, SAINTE_LAGUE, MODIFIED_SAINTE_LAGUE, IMPERIALI, HARE_QUOTA, DROOP_QUOTA;

	private static Methods method = DHONDT;
	
	private static double MSLFD = 1.4;
	
	public static Methods getCalculationMethod()
	{
		return method;
	}
	
	public static void setCalculationMethod(Methods calculationMethod)
	{
		method = calculationMethod;
	}
	
    public static double  getDivisor(Integer numSeats) {
    	if (method.equals(DHONDT)) {
    		return numSeats + 1;
    	} else if (method.equals(SAINTE_LAGUE)){
    		return 2 * numSeats + 1;
    	} else if (method.equals(MODIFIED_SAINTE_LAGUE)){
    		if (numSeats.equals(0)) {
    			return MSLFD;
    		} else {
    			return 2 * numSeats + 1;
    		}
    	} else if (method.equals(IMPERIALI)){
    		return numSeats + 2;
    	} else {
    		return -1; // ERROR: Unknown method
    	}
    }
    
    public static Map<String, Integer> calculateHighestAverage(int seats, Map<String, Integer> votes) {
    	double highest = 0.0;
    	String seatTo = "";
    	Map<String, Double> lastQuot = new HashMap<String, Double>();
    	//Map<String, Integer> nextSeat = new HashMap<String, Integer>();
    	Map<String, Integer> results = new HashMap<String, Integer>();
    	
		// Initialize the results hash map for the parties in votes
		for (String s: votes.keySet())
		{
			// They start with 0 seats each
			results.put(s, 0);
		}
		
		// Calculate the number of seats for each party
		for (int i = 1; i <= seats; i++) {
			// Highest value in this round (reset it to 0 in each round)
			highest = 0.0;
			
			// Who gets the seat in this round (reset it to "" in each round)
			seatTo = "";
			
			for (String s: votes.keySet()) {
				// TODO: I should take into account if the party's vote percentage is bigger than the threshold
				// Calculate the quot for this party in this round
				double quot = votes.get(s).doubleValue() / Methods.getDivisor(results.get(s));
				
				// If the quot is bigger than the highest value in this round
				if (quot > highest) {
					// the party becomes the candidate to get this seat
					seatTo = s;
					// Save the quot to check it with the values for the rest of parties
					highest = quot;
				// else if the quot is equal to the highest value in this round
				} else if (quot == highest) {
					// The seat goes to the party that has more votes
					if (votes.get(s) > votes.get(seatTo)) {
						seatTo = s;
						highest = quot;
					}
				}
				
				// Save the last quots to calculate the number of extra votes needed to get the last seat
				if (i == seats) {
					lastQuot.put(s, quot);
				}
			}
			// The party with the highest quot gets another seat
			results.put(seatTo, results.get(seatTo) + 1);
		}
		
		return results;
		
		/* TODO: Complete this
		for (String s: PartyListActivity.votes.keySet()) {
			// The party that got the last seat needs 0 votes to get the last seat
			if (s == seatTo) {
				nextSeat.put(s, 0);
			// The rest of parties need to get a bigger quot than the party that got the last seat
			} else {
				nextSeat.put(s, );
			}
		}
		*/
    }
    
}
