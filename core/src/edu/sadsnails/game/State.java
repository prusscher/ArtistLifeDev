package edu.sadsnails.game;

import java.io.File;
import java.util.Random;

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
	private int level;		// current level in drawing skill [0 -> 8]
	private String title;		//the title associated with your current level
	
	// Popularity
	private float popularity;	// current popularity [-1 <-> 1]
	
	// Time
	private int [] date;		// the current date: year(0) | month(1) | day(2)	
	private int hour;			// current time: 24-hour clock
	
	// Energy
	private int energy;		// current amount of energy: maybe exceed 100
	
	// Money
	private float money;		
	private float spent_money;
	private float earned_money;
	
	protected String pop_title;
	
	// Items
	private int bedIndex;
	private int deskIndex;
	private int roomIndex;
	
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
	
	private String log;
	
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
		setLevel(0);
		setTitle("1");
		setPopularity(0);
		setDate(new int[3]);
			getDate()[0] = 1;
			getDate()[1] = 1;
			getDate()[2] = 1;
		setHour(1);
		setEnergy(100);
		setMoney(0); setSpent_money(0); setEarned_money(0);
		pop_title = "Not popular or unpopular";	
    	
		setBedIndex(0);
		setDeskIndex(0);
		setRoomIndex(0);
		
		// Read File and reset variables to the save file
    	if(fileExists && pair.size() == 17) {
    		System.out.println("Loading Player Save File");
    		
    		// Load previous state
    		setXp(pair.getInt("xp"));
    		toNext = pair.getInt("toNext");
    		setLevel(pair.getInt("level"));
    		setTitle(pair.getString("title"));
    		setPopularity(pair.getFloat("popularity"));
    		getDate()[0] = pair.getInt("date0");
    		getDate()[1] = pair.getInt("date1");
    		getDate()[2] = pair.getInt("date2");
    		setHour(pair.getInt("hour"));
    		setEnergy(pair.getInt("energy"));
    		setMoney(pair.getFloat("money"));
    		setSpent_money(pair.getFloat("spent_money"));
    		setEarned_money(pair.getFloat("earned_money"));
    		pop_title 		= pair.getString("pop_title");	
    		
    		setBedIndex(pair.getInt("bedIndex"));
    		setDeskIndex(pair.getInt("deskIndex"));
    		setRoomIndex(pair.getInt("roomIndex"));
    		
    		if(bedIndex == -1) bedIndex = 0;
    		if(deskIndex == -1) deskIndex = 0;
    		if(roomIndex == -1) roomIndex = 0;
    	}
    	
    	log = "\n";
    	
		save(); // Save the file
	}
	
	public void save() {
		System.out.println("Saving Player File");
		pair.putInt("xp", getXp());
		pair.putInt("toNext", toNext);
		pair.putInt("level", getLevel());
		pair.putFloat("popularity", getPopularity());
		pair.putString("title", getTitle());
		pair.putInt("date0", getDate()[0]);
		pair.putInt("date1", getDate()[1]);
		pair.putInt("date2", getDate()[2]);
		pair.putInt("hour", 1);
		pair.putInt("energy", 100);
		pair.putFloat("money", getMoney());
		pair.putFloat("spent_money", getSpent_money());
		pair.putFloat("earned_money", getEarned_money());
		pair.putString("pop_title", pop_title);
		
		pair.putInt("bedIndex", getBedIndex());
		pair.putInt("deskIndex", getDeskIndex());
		pair.putInt("roomIndex", getRoomIndex());
		
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
	
	public void spendMoney(float money) {
		this.money -= money;
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
	
	public void clearLog() {
		log = "\n";
	}
	
	public String getLog() {
		return log;
	}
	
	/**
	 * Add a string to the current text log. Adds a newline after every new string
	 * @param logText String to add to text log
	 */
	public void log(String logText) {
		log += logText + "\n\n";
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public float getPopularity() {
		return popularity;
	}

	public void setPopularity(float popularity) {
		this.popularity = popularity;
	}

	public float getEarned_money() {
		return earned_money;
	}

	public void setEarned_money(float earned_money) {
		this.earned_money = earned_money;
	}

	public float getSpent_money() {
		return spent_money;
	}

	public void setSpent_money(float spent_money) {
		this.spent_money = spent_money;
	}

	public int getBedIndex() {
		return bedIndex;
	}

	public void setBedIndex(int bedIndex) {
		this.bedIndex = bedIndex;
	}

	public int getDeskIndex() {
		return deskIndex;
	}

	public void setDeskIndex(int deskIndex) {
		this.deskIndex = deskIndex;
	}

	public int getRoomIndex() {
		return roomIndex;
	}

	public void setRoomIndex(int roomIndex) {
		this.roomIndex = roomIndex;
	}
}
