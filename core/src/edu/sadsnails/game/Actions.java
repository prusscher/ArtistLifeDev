package edu.sadsnails.game;

import java.util.Random;

public class Actions {
	
	/* -----------------------------------------------------------------
	 * 	This class serves the purpose of maintaining all of the possible
	 *	actions within the game.
	 * -----------------------------------------------------------------*/
	private MyGdxGame game;
	private State state;
	
	private String d_type;
	private String d_subject;
	
	private int xp_gain;
	
	private int time_consum;		// time consumption of a task
	private int energy_consum;	// energy consumption of a task
	
	private int art_rank;
	
	public static final int NAP = 1;
	public static final int SLEEP = 2;
	
	public Actions(MyGdxGame game, State state){
		this.game = game;
		this.state = state;
		d_type = new String("type");
		d_subject = new String("subject");
		state.log("Well, you're an artist now! Check here for further updates on your grueling trudge towards making a cool picture or two.");
	}
	
	/*	makeArt method:
	 * 		This method serves the purpose of allowing the player
	 * 		to create art.
	 * 
	 * 	When this option is selected, another menu will appear
	 * 	allowing the player to choose what type and genre they would
	 * 	like for their drawing to be. This drawing will then take a certain
	 * 	amount of time to complete and will consume energy depending on the
	 * 	type of drawing it is. Refer to the running document for data tables.
	 * 
	 */
	public void makeArt(int type, int subject) {
		System.out.println("I made art");
		state.log("I made art");
		type++; subject++;
		switch(type){
		case 1:
			d_type = "Pixel";
			time_consum = 2;
			energy_consum = 25;
			xp_gain = 25;
			break;
		case 2:
			d_type = "Sketch";
			time_consum = 1;
			energy_consum = 30;
			xp_gain = 20;
			break;
		case 3:
			d_type = "Abstract";
			time_consum = 3;
			energy_consum = 40;
			xp_gain = 30;
			break;
		case 4:
			d_type = "Surreal";
			time_consum = 5;
			energy_consum = 50;
			xp_gain = 50;
			break;
		case 5:
			d_type = "Realistic";
			time_consum = 7;
			energy_consum = 70;
			xp_gain = 70;
			break;
		case 6:
			d_type = "Painting";
			time_consum = 10;
			energy_consum = 95;
			xp_gain = 100;
		}
		
		switch(subject){
		case 1:
			d_subject = "Anime";
			break;
		case 2:
			d_subject = "Retro";
			break;
		case 3:
			d_subject = "Funny";
			break;
		case 4:
			d_subject = "Fantasy";
			break;
		case 5:
			d_subject ="Animal";
			break;
		case 6:
			d_subject = "Nature";
			break;
		case 7:
			d_subject = "Human";
			break;
		}
		
		System.out.println("Type: " + d_type + " \nSubject: " + d_subject);
		state.log("Type: " + d_type + " \nSubject: " + d_subject);
		
		if(energy_consum >= state.getEnergy()) {
			System.out.println("This task will take more energy than you have");
			state.log("This task will take more energy than you have");
			time_consum = 0;
			energy_consum = 0;
			xp_gain = 0;
		}
		else {
		
		// the most positive rating combinations
		// TODO
		// include money gain for each popularity bracket
		// include popularity variable
		if((d_type.equals("Pixel") && d_subject.equals("Retro"))
		|| (d_type.equals("Sketch") && d_subject.equals("People"))
		|| (d_type.equals("Abstract") && d_subject.equals("Funny"))
		|| (d_type.equals("Abstract") && d_subject.equals("Nature"))
		|| (d_type.equals("Abstract") && d_subject.equals("People"))
		|| (d_type.equals("Surreal") && d_subject.equals("Fantasy"))
		|| (d_type.equals("Surreal") && d_subject.equals("People"))
		|| (d_type.equals("Realistic") && d_subject.equals("Animal"))
		|| (d_type.equals("Realistic") && d_subject.equals("Nature"))
		|| (d_type.equals("Painting") && d_subject.equals("Retro"))
		|| (d_type.equals("Painting") && d_subject.equals("Animal"))
		|| (d_type.equals("Painting") && d_subject.equals("Nature"))
		|| (d_type.equals("Painting") && d_subject.equals("People")))
		{
			System.out.println("The audience really liked your drawing");
			state.log("The audience really liked your drawing");
			art_rank = 2;
			alterPopularity(2);
		}
		
		if((d_type.equals("Pixel") && d_subject.equals("Anime"))
		|| (d_type.equals("Pixel") && d_subject.equals("Fantasy"))
		|| (d_type.equals("Pixel") && d_subject.equals("Nature"))
		|| (d_type.equals("Sketch") && d_subject.equals("Anime"))
		|| (d_type.equals("Sketch") && d_subject.equals("Funny"))
		|| (d_type.equals("Sketch") && d_subject.equals("Animal"))
		|| (d_type.equals("Sketch") && d_subject.equals("Nature"))
		|| (d_type.equals("Realistic") && d_subject.equals("Anime"))
		|| (d_type.equals("Realistic") && d_subject.equals("Funny"))
		|| (d_type.equals("Realistic") && d_subject.equals("Fantasy"))
		|| (d_type.equals("Realistic") && d_subject.equals("People"))
		|| (d_type.equals("Painting") && d_subject.equals("Anime"))
		|| (d_type.equals("Painting") && d_subject.equals("Fantasy")))
		{
			System.out.println("The audience liked your drawing a little");
			state.log("The audience liked your drawing a little");
			art_rank = 1;
			alterPopularity(1);
		}

		if((d_type.equals("Pixel") && d_subject.equals("Animal"))
		|| (d_type.equals("Pixel") && d_subject.equals("People"))
		|| (d_type.equals("Surreal") && d_subject.equals("Retro"))
		|| (d_type.equals("Surreal") && d_subject.equals("Funny"))
		|| (d_type.equals("Surreal") && d_subject.equals("Animal"))
		|| (d_type.equals("Surreal") && d_subject.equals("Nature")))
		{
			System.out.println("The audience did not feel strongly one way or the other about your drawing");
			state.log("The audience did not feel strongly one way or the other about your drawing");
			art_rank = 0;
			alterPopularity(0);
		}
		
		if((d_type.equals("Pixel") && d_subject.equals("Funny"))
		|| (d_type.equals("Sketch") && d_subject.equals("Retro"))
		|| (d_type.equals("Sketch") && d_subject.equals("Fantasy"))
		|| (d_type.equals("Realistic") && d_subject.equals("Retro"))
		|| (d_type.equals("Painting") && d_subject.equals("Funny")))
		{
			System.out.println("The audience did not really like your drawing");
			state.log("The audience did not really like your drawing");
			art_rank = -1;
			alterPopularity(-1);
		}
		
		if((d_type.equals("Abstract") && d_subject.equals("Anime"))
		|| (d_type.equals("Abstract") && d_subject.equals("Retro"))
		|| (d_type.equals("Abstract") && d_subject.equals("Fantasy"))
		|| (d_type.equals("Abstract") && d_subject.equals("Animal"))
		|| (d_type.equals("Surreal") && d_subject.equals("Anime")))
		{
			System.out.println("The audience hated your drawing");
			state.log("The audience hated your drawing");
			art_rank = -2;
			alterPopularity(-2);
		}
		incMoney(state.popularity, art_rank);
		state.setEnergy(state.getEnergy() - energy_consum);
		incXP(xp_gain);
		passTime(time_consum);
		state.printStates();
		}
	}
	
	public int getTime(int type) {
		type++;
		switch(type){
			case 1:
				return 2;
			case 2:
				return 1;
			case 3:
				return 3;
			case 4:
				return 5;
			case 5:
				return 7;
			case 6:
				return 10;
			default:
				return 1;
		}
	}
	
	public boolean canMakeArt(int type) {
		int eToConsume = 0;
		
		switch(type){
		case 1:
			eToConsume = 25;
			break;
		case 2:
			eToConsume = 30;
			break;
		case 3:
			eToConsume = 40;
			break;
		case 4:
			eToConsume = 50;
			break;
		case 5:
			eToConsume = 70;
			break;
		case 6:
			eToConsume = 95;
		}
		
		if(eToConsume >= state.getEnergy())
			return false;
		return true;
	}
	
	/*	alterPopularity method:
	 * 		This method serves the purpose of altering the player's popularity
	 * 		according to their most recent piece of art.
	 * 
	 * 	At 1, the user is the most popular they can be.
	 * 	At 0, the user is neither popular nor unpopular.
	 * 	At -1, the user is as unpopular as they can be.
	 * 
	 */
	public void alterPopularity(int val) {
		// if 0, don't change popularity
		// if 1, increase popularity by .5
		// if 2, increase popularity by 1
		// if -1, decrease popularity by .5
		// if -2, decrease popularity by 1
		
		
		switch(val) {
		case -2:
			if((state.popularity - 1) < -1)
				state.popularity = -1;
			else
				state.popularity -= 1;
			break;
		case -1:
			if((state.popularity - 0.5) < -1)
				state.popularity = -1;
			else
				state.popularity -= 0.5;
			break;
		case 0:
			//nothin
			break;
		case 1:
			if((state.popularity + 0.5) > 1)
				state.popularity = 1;
			else
				state.popularity += 0.5;
			break;
		case 2:
			if((state.popularity + 1) > 1)
				state.popularity = 1;
			else
				state.popularity += 1;
			break;
			
			
		}

		if(state.popularity == -1) 
			state.pop_title = "Very unpopular";
		else if(state.popularity == -0.5) 
			state.pop_title = "Moderately unpopular";
		else if(state.popularity == 0) 
			state.pop_title = "Not popular or unpopular";
		else if(state.popularity == 0.5) 
			state.pop_title = "Moderately popular";
		else if(state.popularity == 1) 
			state.pop_title = "Very popular";
		
	}
	
	/*	sleep method:
	 * 		This method (obviously) serves the purpose of allowing
	 * 		the player character to sleep for a period of ingame time.
	 * 
	 * 	The sleep action will cause the rest of the day to pass.
	 * 	The clock will go to the first hour of the next day, and
	 * 	the player's energy will return to its full value.
	 * 	If the player is at full energy they may not sleep.
	 * 
	 * Type: 1 for a nap, 2 for a full night's sleep
	 */
	public void sleep(int type) {		
		// if energy is 100 you cannot sleep
		if(state.getEnergy() == 100) {
			System.out.println("You can't sleep when you're wide awake!");
			state.log("You can't sleep when you're wide awake!");
		}
		// try to sleep
		// if it's a nap, check to see if you've napped already
		// if it's a full night's sleep, it moves to the first hour
		// 	of the next day
		else{
			
			if(type == 2) {
				System.out.println("I successfully slept");
				state.log("I successfully slept");
				passTime(-1);
				state.setEnergy(100);
			}
		
			// if you have napped, you can't nap again
			// if you have not napped, raise energy by 50
			// and move clock forward 5 hours
			else {
				if(state.has_napped) {
					System.out.println("I have already napped today");
					state.log("I have already napped today");
				} else {
					if(state.getEnergy() + 50 >= 100) {
						state.setEnergy(100);
						state.has_napped = true;
					}
					else {
						state.setEnergy(state.getEnergy() + 50);
						state.has_napped = true;
					}
					passTime(5);
				}
			}
		}
	}
		
	public boolean canSleep() {
		if(state.getEnergy() == 100)
			return false;
		return true;
	}
	
	/* useBooster method:
	 * 		This method is for the various items you can purchase
	 * 		with in-game currency to boost target statistics for a period of time.
	 * 
	 * 	These booster items can include:
	 *  coffee (increased energy)
	 *  tutor  (increased skill)
	 *  etc.
	 */
	public void buyBooster() {
		if(state.coffee_used) 
			System.out.println("I have already had coffee today");
		else if(state.getMoney() < 5) {
			System.out.println("I do not have enough money for this");
		}
		else if(state.getEnergy() == 100) {
			System.out.println("I cannot drink coffee when I am wide awake");
		}
		else if((state.getEnergy() + 40) > 100) {
			System.out.println("I bought coffee!");
			state.setEnergy(100);
			state.setMoney(state.getMoney() - 5);
			state.spent_money += 5;
			state.coffee_used = true;
		}
		else {
			System.out.println("I bought coffee!");
			state.setEnergy(state.getEnergy() + 40);
			state.setMoney(state.getMoney() - 5);
			state.spent_money += 5;
			state.coffee_used = true;
		}
	}
	/* incXP method:
	 * 		This method serves the purpose of incrementing the player's
	 * 		experience according to the type of art the player chooses.
	 * 		Refer to the running document for data tables.
	 */
	public void incXP(int xpAmount) {
		
		if(xpAmount + state.getXp() >= state.toNext && state.level != 8) {
			//level up, if you're not max level
			
			state.setXp(state.getXp() + xpAmount);
			state.level	++;
			state.toNext *= 3;
			
			switch (state.level){
			case 1: state.setTitle("1");
					break;
			case 2: state.setTitle("2");
					break;
			case 3: state.setTitle("3");
					break;
			case 4: state.setTitle("4");
					break;
			case 5: state.setTitle("5");
					break;
			case 6: state.setTitle("6");
					break;
			case 7: state.setTitle("7");
					break;
			case 8: state.setTitle("8");
					break;
			default: state.setTitle("0 WRONG");
					 break;
			}
			
			System.out.println("You've leveled up! You are now level " +state.level + "! People have started to call you the " + state.getTitle());
		}
		else
			state.setXp(state.getXp() + xpAmount);
		}
	
	/* passTime method:
	 * 		This method increments the time. This handles checks for
	 * 		incrementing day at 24 hours,
	 * 		month at 30 days and incrementing year at 12
	 * 		months and 30 days.
	 */
	public void passTime(int hrs) {
		if(hrs == -1) {
			if(state.getDate()[2] == 30) {
				if(state.getDate()[1] == 12) 
					state.getDate()[0] ++;
				state.getDate()[1] ++;
				state.getDate()[2] = 1;
				randEvent();
			}
			else {
				state.getDate()[2] ++;
				randEvent();
			}
		 state.has_napped = false;
		 state.coffee_used = false;
		 state.setHour(1);
		}
		else if((state.getHour() + hrs) > 24) {
			int hr = 24 - state.getHour();
			if(state.getDate()[2] == 30) {
				if(state.getDate()[1] == 12) 
					state.getDate()[0] ++;
				state.getDate()[1] ++;
				state.getDate()[2] = 1;
				randEvent();
			}
			else {
				state.getDate()[2] ++;
				randEvent();
			}
			state.setHour(hr + hrs);
			state.has_napped = false;
			state.coffee_used = false;
		}
		
		state.setHour(state.getHour() + hrs);
		
	}
	/*
	 * When you roll over to a new day, generates a random number to see if any random events happen
	 * in the morning.
	 */
	public void randEvent(){
		int eventCheck = game.rng.nextInt(101);
		System.out.print("event number " + eventCheck);
		if (eventCheck <= 5){
			state.setMoney(state.getMoney() + 20);
			state.earned_money += 20;
			state.log("On your way to the store to buy Artist Fuel(tm) you found twenty dollars! What a luckster you are!"); 
			((GameScreen) game.getScreen()).getUI().updateState();
		}
		else if (eventCheck > 5 && eventCheck <= 10){
			alterPopularity(1);
			state.log("A cool and good artist reblogged one of your pictures! You get a small boost in popularity.");
			((GameScreen) game.getScreen()).getUI().updateState();
		}
		else if (eventCheck >= 90 && eventCheck < 94){
			alterPopularity(-1);
			state.log("You got into an online argument and called someone a 'canvas licking paintspiller'. Everyone was aghast.");
			((GameScreen) game.getScreen()).getUI().updateState();
		}
		else if (eventCheck >= 95){
			state.log("A hip new indie pixel retro throwback survival game was released! You bought it without even LOOKING at your wallet.");
			if (state.getMoney() > 10){
				state.setMoney(state.getMoney() - 10);
				((GameScreen) game.getScreen()).getUI().updateState();
			}
			else{
				state.setMoney(0);
				((GameScreen) game.getScreen()).getUI().updateState();
			}
		}
	}
	
	/* incMoney method:
	 * 
	 * 		This method serves the purpose of increasing the player's
	 * 		money, according to how popular they are and how well
	 * 		received their art was.
	 */
	public void incMoney(float popularity, int ranking) {
		
		if(popularity == -1) {
			switch(ranking) {
			case -2:
				state.setMoney(state.getMoney() + 0);
				state.earned_money += 0;
				break;
			case -1:
				state.setMoney(state.getMoney() + 0);
				state.earned_money += 0;
				break;
			case 0:
				state.setMoney(state.getMoney() + 1);
				state.earned_money += 1;
				break;
			case 1:
				state.setMoney(state.getMoney() + 5);
				state.earned_money += 5;
				break;
			case 2:
				state.setMoney(state.getMoney() + 10);
				state.earned_money += 10;
				break;
			}
		}
		if(popularity == -0.5) {
			switch(ranking) {
			case -2:
				state.setMoney(state.getMoney() + 0);
				state.earned_money += 0;
				break;
			case -1:
				state.setMoney(state.getMoney() + 1);
				state.earned_money += 1;
				break;
			case 0:
				state.setMoney(state.getMoney() + 2);
				state.earned_money += 2;
				break;
			case 1:
				state.setMoney(state.getMoney() + 7);
				state.earned_money += 7;
				break;
			case 2:
				state.setMoney(state.getMoney() + 8);
				state.earned_money += 8;
				break;
			}
		}
		if(popularity == 0) {
			switch(ranking) {
			case -2:
				state.setMoney(state.getMoney() + 1);
				state.earned_money += 1;
				break;
			case -1:
				state.setMoney(state.getMoney() + 5);
				state.earned_money += 5;
				break;
			case 0:
				state.setMoney(state.getMoney() + 10);
				state.earned_money += 10;
				break;
			case 1:
				state.setMoney(state.getMoney() + 12);
				state.earned_money += 12;
				break;
			case 2:
				state.setMoney(state.getMoney() + 15);
				state.earned_money += 15;
				break;
			}
		}
		if(popularity == 0.5) {
			switch(ranking) {
			case -2:
				state.setMoney(state.getMoney() + 2);
				state.earned_money += 2;
				break;
			case -1:
				state.setMoney(state.getMoney() + 5);
				state.earned_money += 5;
				break;
			case 0:
				state.setMoney(state.getMoney() + 15);
				state.earned_money += 15;
				break;
			case 1:
				state.setMoney(state.getMoney() + 20);
				state.earned_money += 20;
				break;
			case 2:
				state.setMoney(state.getMoney() + 25);
				state.earned_money += 25;
				break;
			}
		}
		if(popularity == 1) {
			switch(ranking) {
			case -2:
				state.setMoney(state.getMoney() + 3);
				state.earned_money += 3;
				break;
			case -1:
				state.setMoney(state.getMoney() + 6);
				state.earned_money += 6;
				break;
			case 0:
				state.setMoney(state.getMoney() + 20);
				state.earned_money += 20;
				break;
			case 1:
				state.setMoney(state.getMoney() + 25);
				state.earned_money += 25;
				break;
			case 2:
				state.setMoney(state.getMoney() + 30);
				state.earned_money += 30;
				break;
			}
		}
	}
	
}
	

