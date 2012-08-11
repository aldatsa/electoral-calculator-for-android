package android.ElectoralCalculator;

public class Data {
	// Number of seats
	public static int seats;
	
	// Used to store the total number of votes
	public static int totalVotes;
	
	// Used to store the list of parties using Party objects(name of the party, number of votes)
	public static PartyList listOfParties = new PartyList();
	
	public static int calculateTotalVotes() {
		int tmpTotalVotes = 0;

		for (int pos = 0; pos < listOfParties.size(); pos++) {
			tmpTotalVotes = tmpTotalVotes + listOfParties.get(pos).getVotes();
		}
		return tmpTotalVotes;
	}
	
	public static void resetSeats() {
		for (int pos = 0; pos < listOfParties.size(); pos++) {
			listOfParties.get(pos).setSeats(0);
		}
	}
}
