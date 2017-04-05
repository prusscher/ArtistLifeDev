package edu.sadsnails.game;

import java.io.File;

public class State {
	
	/* -----------------------------------------------------------------
	 *	This class serves the purpose of being a container for 
	 *	game variables. Through this class, one may alter the various
	 *	statistics and variables within the game.
	 * 
	 *	For debugging purposes, the printStates
	 *	method prints all of the current states of the variables
	 *	within this class.
	 * -----------------------------------------------------------------*/

	// Experience
	private int xp; 			// the current amount of experience we have
	protected int toNext;		// the next XP milestone before your level increases

	// Level
	protected int level;		// current level in drawing skill [0 -> 8]
	private String title;		//the title associated with your current level
	
	// Popularity
	protected float popularity;	// current popularity [-1 <-> 1]
	
	// Time
	private int [] date;		// the current date: year(0) | month(1) | day(2)	
	private int hour;			// current time: 24-hour clock
	
	// Energy
	private int energy;		// current amount of energy: maybe exceed 100
	
	// Money
	private float money;		
	protected float spent_money;
	protected float earned_money;
	
	protected String pop_title;
	
	// Booster
	protected boolean coffee_used;
	protected boolean has_napped;
	
	// stack backlog implementation (?)
	// if things from the backlog are repeated,
	// player is penalized and xp is halved, 
	// popularity goes down if possible
	protected String [][] backlog = 
		{ {"1", "2"} , 
		  {"3", "4"} , 
		  {"5", "6"} };
	
	// ---
	private String prefsFolder;
	private String fileName;
	private Pair pair;
	
	public State() {
		
		// Get to the settings directory and create a new file
    	prefsFolder = System.getenv("LOCALAPPDATA") + "\\sadsnails\\saves";
    	fileName = prefsFolder + "\\save.txt";
    	File dir = new File(prefsFolder);
    	boolean result = false;
		
    	// Check if the directory exists
    	if(!dir.exists()) {
    		// Can't find the directory, creating it
    		System.out.println("Creating Directory: " + prefsFolder);
    		
    		try {
	    		dir.mkdirs();
	    		result = true;
    		} catch (SecurityException e) { 
    			System.out.println("PROBLEM: Can't create the directory, creating default settings");
    		}
    		
    		if(result) {
    			System.out.println("We Good: " + prefsFolder + " created");
    		}
    	}
    	
    	// Create the settings object and read the settings config file
    	pair = new Pair();
    	boolean fileExists = pair.read(fileName);
    	
    	System.out.println(pair);
    	
    	// Set Initial state
    	// Default State
		setXp(0);
		toNext = 30;
		level = 0;
		setTitle("1");
		popularity = 0;
		setDate(new int[3]);
			getDate()[0] = 1;
			getDate()[1] = 1;
			getDate()[2] = 1;
		setHour(1);
		setEnergy(100);
		setMoney(0); spent_money = 0; earned_money = 0;
		pop_title = "Not popular or unpopular";	
    	
		// Read File and reset variables to the save file
    	if(fileExists && pair.size() == 14) {
    		// Load previous state
    		setXp(pair.getInt("xp"));
    		toNext 			= pair.getInt("toNext");
    		level 			= pair.getInt("level");
    		setTitle(pair.getString("title"));
    		popularity 		= pair.getFloat("popularity");
    		getDate()[0] 		= pair.getInt("date0");
    		getDate()[1] 		= pair.getInt("date1");
    		getDate()[2] 		= pair.getInt("date2");
    		setHour(pair.getInt("hour"));
    		setEnergy(pair.getInt("energy"));
    		setMoney(pair.getFloat("money"));
    		spent_money 	= pair.getFloat("spent_money");
    		earned_money 	= pair.getFloat("earned_money");
    		pop_title 		= pair.getString("pop_title");	
    	}
    	
		save(); // Save the file
	}
	
	public void save() {
		pair.putInt("xp", getXp());
		pair.putInt("toNext", toNext);
		pair.putInt("level", level);
		pair.putFloat("popularity", popularity);
		pair.putString("title", getTitle());
		pair.putInt("date0", getDate()[0]);
		pair.putInt("date1", getDate()[1]);
		pair.putInt("date2", getDate()[2]);
		pair.putInt("hour", 1);
		pair.putInt("energy", 100);
		pair.putFloat("money", getMoney());
		pair.putFloat("spent_money", spent_money);
		pair.putFloat("earned_money", earned_money);
		pair.putString("pop_title", pop_title);
		
		pair.write(fileName);
	}
	
	public void printStates() {
//		System.out.println("XP: " + xp);
//		System.out.println("Level: " + level);
//		System.out.println("Title: " + title);
//		System.out.println("Popularity: " + popularity);
//		System.out.println("Year " + date[0] + " Month " + date[1] + " Day " + date[2]);
//		System.out.println("Hour: " + hour);
//		System.out.println("Energy: " + energy);
//		System.out.println("Money : " + money);
//		System.out.println("Money Earned : " + earned_money);
//		System.out.println("Money Spent : " + spent_money);
		
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int [] getDate() {
		return date;
	}

	public void setDate(int [] date) {
		this.date = date;
	}
}
