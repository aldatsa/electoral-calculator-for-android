package android.ElectoralCalculator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Methods {
	DHONDT, SAINTE_LAGUE, MODIFIED_SAINTE_LAGUE, IMPERIALI, HARE_QUOTA, DROOP_QUOTA;

	private static Methods method = DHONDT;
	
	private static double MSLFD = 1.4;
	
	public static double getMSLFD() {
		return MSLFD;
	}
	
	public static void setMSLFD(double value) {
		MSLFD = value;
	}
	
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
    
    public static void calculate(int seats, List<Party> votes) {
    	if (method.equals(DHONDT) || method.equals(SAINTE_LAGUE) || method.equals(MODIFIED_SAINTE_LAGUE) || method.equals(IMPERIALI)) {
    		calculateHighestAverage(seats, votes);
    	} else { //if (method.equals(HARE_QUOTA) || method.equals(DROOP_QUOTA)){
    		calculateLargestRemainder(seats, votes);
    	}
    }
    private static void calculateHighestAverage(int seats, List<Party> votes) {
    	double highest = 0.0;
    	int seatToPos = 0; // To store the position in the list votes of the party that is currently going to get the next seat 
    	
    	Map<String, Double> lastQuot = new HashMap<String, Double>();
    	
		// Reset the seats of all parties to zero
		Data.resetSeats();
		
		// Calculate the number of seats for each party
		for (int i = 1; i <= seats; i++) {
			// Highest value in this round (reset it to 0 in each round)
			highest = 0.0;
			
			// Who gets the seat in this round (reset it to 0 in each round)
			seatToPos = 0;
			int tmpPartyVotes = 0;
			for (int pos = 0; pos < votes.size(); pos++) {
				// TODO: I should take into account if the party's vote percentage is bigger than the threshold
				tmpPartyVotes = votes.get(pos).getVotes();
				
				// Calculate the quot for this party in this round
				double quot = (double) tmpPartyVotes / Methods.getDivisor(Data.listOfParties.getPartySeats(pos));
				
				// If the quot is bigger than the highest value in this round
				if (quot > highest) {
					// the party becomes the candidate to get this seat
					seatToPos = pos;
					// Save the quot to check it with the values for the rest of parties
					highest = quot;
				// else if the quot is equal to the highest value in this round
				} else if (quot == highest) {
					// The seat goes to the party that has more votes
					if (tmpPartyVotes > Data.listOfParties.getPartyVotes(seatToPos)) {
						seatToPos = pos;
						highest = quot;
					}
				}
				
				// Save the last quots to calculate the number of extra votes needed to get the last seat
				if (i == seats) {
					lastQuot.put(Data.listOfParties.getPartyName(seatToPos), quot);
				}
			}
			// The party with the highest quot gets another seat
			Data.listOfParties.setSeatsPlusOne(seatToPos);
		}
		
		// Calculate the extra votes that each party needs to get another seat
		for (int pos = 0; pos < Data.listOfParties.size(); pos++) {
			// The party that got the last seat needs 0 votes to get the last seat
			if (pos == seatToPos) {
				Data.listOfParties.setVotesToNextSeat(pos, 0);
			// The rest of parties need to get a bigger quot than the party that got the last seat
			} else {
				//nextSeat.put(s, );
				Double tmpLastQuotSeatToPos = lastQuot.get(Data.listOfParties.getPartyName(seatToPos));
				double tmpDivisorPos = Methods.getDivisor(Data.listOfParties.getPartySeats(pos));
				int tmpVotesPos = Data.listOfParties.getPartyVotes(pos);
				int tmpVotesToNextSeatPos = Data.listOfParties.getVotesToNextSeat(pos);
				Integer tmpVotesSeatToPos = Data.listOfParties.getPartyVotes(seatToPos);
				
				Data.listOfParties.setVotesToNextSeat(pos, (int) (Math.ceil(tmpLastQuotSeatToPos * tmpDivisorPos) - tmpVotesPos));
				
				if ((tmpLastQuotSeatToPos.equals(tmpVotesPos + tmpVotesToNextSeatPos) ||
					 tmpLastQuotSeatToPos.equals((tmpVotesPos + tmpVotesToNextSeatPos) / Methods.getDivisor(Data.listOfParties.getPartySeats(seatToPos))))
					&&  tmpVotesSeatToPos.compareTo(tmpVotesPos) > 0) {
						Data.listOfParties.setSeatsPlusOne(pos);
				}
			}
		}
    }

    private static void calculateLargestRemainder(int numSeats, List<Party> votes) {
    	Map<String, Double> remainder = new HashMap<String, Double>();
    	Double quota = 0.0;
    	int tempSeats = 0;
    	double tempVQ = 0.0;
    	
    	/*
    	 * The largest remainder method requires the numbers of votes for each party
    	 * to be divided by a quota representing the number of votes required for a
    	 * seat (i.e. usually the total number of votes cast divided by the number
    	 * of seats, or some similar formula).
    	 * The result for each party will usually consist of an integer part
    	 * plus a fractional remainder. 
    	 */

    	// Calculate the quota for the current method
    	if (method.equals(HARE_QUOTA)) {
    		quota = ((double) Data.totalVotes / numSeats); // TODO: Should be validVotes instead of totalVotes
    	} else if (method.equals(DROOP_QUOTA)) {
    		quota = (1 + ((double) Data.totalVotes / (1 + numSeats))); // TODO: Should be validVotes instead of totalVotes
    	} else {
    		// ERROR: Not a valid method, show a toast?
    	}
    	
    	String tmpPartyName = "";
		int tmpPartyVotes = 0;
    	/*
    	 *  Each party is first allocated a number of seats equal to their integer. 
    	 *  This will generally leave some seats unallocated.
    	 */
    	for (int pos = 0; pos < votes.size(); pos++) {
    		tmpPartyName = votes.get(pos).getName();
    		tmpPartyVotes = votes.get(pos).getVotes();
    		tempVQ = tmpPartyVotes / quota;
    		Data.listOfParties.setPartySeats(pos, (int)tempVQ);
    		remainder.put(tmpPartyName, tempVQ - Data.listOfParties.getPartySeats(pos));
    		tempSeats = tempSeats + Data.listOfParties.getPartySeats(pos);
    	}
    	
    	/*
    	 * The parties are then ranked on the basis of the fractional remainders,
    	 * and the parties with the largest remainders are each allocated one
    	 * additional seat until all the seats have been allocated.
    	 * This gives the method its name.
    	 */
    	while (tempSeats < numSeats) {
    		String tmpHighestRemainderParty = "";
    		int tmpHighestRemainderPos = 0;
    		double tmpHighestRemainder = 0.0;
    		Double currentRemainder = 0.0;
    		
    		// Find the highest remainder in this round
    		for (int pos = 0; pos < Data.listOfParties.size(); pos++) {
    			currentRemainder = remainder.get(Data.listOfParties.getPartyName(pos));
    			if (currentRemainder.compareTo(tmpHighestRemainder) > 0) {
    				tmpHighestRemainder = currentRemainder;
    				tmpHighestRemainderPos = pos;
    				tmpHighestRemainderParty = Data.listOfParties.getPartyName(pos);
    			}
    		}
    		
    		// The highest remainder gets another seat
    		Data.listOfParties.setSeatsPlusOne(tmpHighestRemainderPos);

    		// Add one to tempSeats
    		tempSeats = tempSeats + 1;

    		// Remove the highest remainder from the map
    		remainder.remove(tmpHighestRemainderParty);
    	}
    }
}
