package android.ElectoralCalculator;

import java.util.Comparator;

public class PartyListComparator implements Comparator<Party>{
	@Override
	public int compare(Party p1, Party p2) {
		return p2.getVotes() - p1.getVotes(); 
	}
}
