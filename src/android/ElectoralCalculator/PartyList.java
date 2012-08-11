package android.ElectoralCalculator;

import java.util.ArrayList;
import java.util.Iterator;

public class PartyList extends ArrayList<Party> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8207224441259052084L;
	
    public String getPartyName(int position) {
        return this.get(position).getName().toString();
    }

    public int getPartyVotes(int position) {
    	return this.get(position).getVotes();
    }
    
    public double getPartyVotePercent(int position) {
    	return this.get(position).getVotePercent();
    }
    
    public void setPartyVotePercent(int position, double votePercent) {
    	this.get(position).setVotePercent(votePercent);
    }

    public int getPartySeats(int position) {
    	return this.get(position).getSeats();
    }
    
    public void setPartySeats(int position, int seats) {
    	this.get(position).setSeats(0);
    }

    public void setSeatsPlusOne(int position) {
    	this.get(position).setSeatsPlusOne();
    }
    
    public int getVotesToNextSeat(int position) {
    	return this.get(position).getVotesToNextSeat();
    }
    
    public void setVotesToNextSeat(int position, int votes) {
    	this.get(position).setVotesToNextSeat(votes);
    }
    
    public Iterator<Party> getIter(){
        return this.iterator();
    }
}
