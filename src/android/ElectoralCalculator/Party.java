package android.ElectoralCalculator;

public class Party {
	private String name;
	private int votes;
	
	// Constructor for the Party class
	public Party(String name, int votes) {
		super();
		this.name = name;
		this.votes = votes;
	}
	
	// Getter and setter methods for all the fields.
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getVotes() {
		return votes;
	}
	public void setVotes(int votes) {
		this.votes = votes;
	}
}
