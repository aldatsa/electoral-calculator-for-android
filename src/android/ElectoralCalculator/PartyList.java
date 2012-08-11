package android.ElectoralCalculator;

import java.util.ArrayList;
import java.util.Iterator;

public class PartyList {

    private ArrayList<Party> partyList;

    public PartyList(){
        partyList = new ArrayList<Party>();
    }

    public void getPartyName(int position){
         partyList.get(position).getName().toString();
    }

    public Iterator<Party> getIter(){
        return partyList.iterator();
    }
}
