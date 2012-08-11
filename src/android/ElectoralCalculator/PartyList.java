package android.ElectoralCalculator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PartyList extends ArrayList<Party> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8207224441259052084L;
	
	private List<Party> partyList = new ArrayList<Party>();

    public String getPartyName(int position) {
        return partyList.get(position).getName().toString();
    }

    public int getPartyVotes(int position) {
    	return partyList.get(position).getVotes();
    }
    
    public double getPartyVotePercent(int position) {
    	return partyList.get(position).getVotePercent();
    }
    
    public int getPartySeats(int position) {
    	return partyList.get(position).getSeats();
    }
    
    public int getVotesToNextSeat(int position) {
    	return partyList.get(position).getVotesToNextSeat();
    }
    
    public Iterator<Party> getIter(){
        return partyList.iterator();
    }
}
