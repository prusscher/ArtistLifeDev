package edu.sadsnails.game;

public class State {

	// Experience
	protected int xp; 			// the current amount of experience we have

	// Level
	protected int level;		// current level in drawing skill [0 -> 8]
	
	// Popularity
	protected float popularity;	// current popularity [-1 <-> 1]
	
	// Time
	protected int [] date;		// the current date: year(0) | month(1) | day(2)	
	protected int hour;			// current time: 24-hour clock
	
	// Energy
	protected int energy;		// current amount of energy: maybe exceed 100
	
	// Money
	protected double money;		
	protected double spent_money;
	protected double earned_money;
	
	// ---
	
	public State() {
		xp = 0;
		
		level = 0;
		
		popularity = 0;
		
		date = new int[3];
			date[0] = 1;
			date[1] = 1;
			date[2] = 1;
			
		hour = 0;
		
		energy = 100;
		
		money = 0; spent_money = 0; earned_money = 0;
	}
	
	public void printStates() {
		System.out.println("XP: " + xp);
		System.out.println("Level: " + level);
		System.out.println("Popularity: " + popularity);
		System.out.println("Year " + date[0] + " Month " + date[1] + " Day " + date[2]);
		System.out.println("Hour: " + hour);
		System.out.println("Energy: " + energy);
		System.out.println("Money : " + money);
		
	}
}
