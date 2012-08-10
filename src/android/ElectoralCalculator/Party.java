package android.ElectoralCalculator;

public class Party {
	private String name;
	private int votes;
	private double votePercent;
	private int seats;
	
	// General constructor for the Party class
	public Party(String name, int votes, double votePercent, int seats) {
		super();
		this.name = name;
		this.votes = votes;
		this.votePercent = votePercent;
		this.seats = seats;
	}
	
	// Constructor for the Party class with name and votes parameters
	public Party(String name, int votes) {
		this(name, votes, 0.0, 0);
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
	public double getVotePercent() {
		return this.votePercent;
	}
	public void setVotePercent(double votePercent) {
		this.votePercent = votePercent;
	}
	public int getSeats() {
		return this.seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	public void setSeatsPlusOne() {
		this.seats = this.seats + 1;
	}
}
