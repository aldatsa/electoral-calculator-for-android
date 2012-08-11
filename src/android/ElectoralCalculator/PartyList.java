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

    public void getPartyName(int position){
         partyList.get(position).getName().toString();
    }

    public Iterator<Party> getIter(){
        return partyList.iterator();
    }
}
