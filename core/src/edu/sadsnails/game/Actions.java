package edu.sadsnails.game;

public class Actions {
	
	/* -----------------------------------------------------------------
	 * 	This class serves the purpose of maintaining all of the possible
	 *	actions within the game.
	 * -----------------------------------------------------------------*/
	
	private State state;
	
	private String d_type;
	private String d_subject;
	
	private int xp_gain;
	
	private int time_consum;		// time consumption of a task
	private int energy_consum;	// energy consumption of a task
	
	public Actions(State state){
		this.state = state;
		d_type = new String("type");
		d_subject = new String("subject");
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
		
		if(energy_consum >= state.energy) {
			System.out.println("This task will take more energy than you have");
			time_consum = 0;
			energy_consum = 0;
			xp_gain = 0;
		}
		
		// the most positive rating combinations
		// TODO
		// add the other combinations
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
			state.money += 40;
		}
		
		state.energy -= energy_consum;
		incXP(xp_gain);
		passTime(time_consum);
		state.printStates();
		
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
		if(state.energy == 100) {
			System.out.println("You can't sleep when you're wide awake!");
		}
		// try to sleep
		// if it's a nap, check to see if you've napped already
		// if it's a full night's sleep, it moves to the first hour
		// 	of the next day
		if(type == 2) {
			System.out.println("I successfully slept");
			passTime(-1);
			state.energy = 100;
		}

		// if you have napped, you can't nap again
		// if you have not napped, raise energy by 50
		// and move clock forward 5 hours
		else {
			if(state.has_napped)
				System.out.println("I have already napped today");

			else {
				if(state.energy + 50 >= 100) {
					state.energy = 100;
					state.has_napped = true;
				}
				else {
					state.energy += 50;
					state.has_napped = true;
				}
				passTime(5);
			}
		}
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
		else if(state.money < 5) {
			System.out.println("I do not have enough money for this");
		}
		else if(state.energy == 100) {
			System.out.println("I cannot drink coffee when I am wide awake");
		}
		else if((state.energy + 40) > 100) {
			System.out.println("I bought coffee!");
			state.energy = 100;
			state.money -= 5;
			state.coffee_used = true;
		}
		else {
			System.out.println("I bought coffee!");
			state.energy += 40;
			state.money -= 5;
			state.coffee_used = true;
		}
	}
	/* incXP method:
	 * 		This method serves the purpose of incrementing the player's
	 * 		experience according to the type of art the player chooses.
	 * 		Refer to the running document for data tables.
	 */
	public void incXP(int xpAmount) {
		
		if(xpAmount + state.xp >= state.toNext && state.level != 8) {
			//level up, if you're not max level
			
			state.xp	+= xpAmount;
			state.level	++;
			state.toNext *= 3;
			
			switch (state.level){
			case 1: state.title = "Stubborn Snail";
					break;
			case 2: state.title = "Determined Doodler";
					break;
			case 3: state.title = "Seasoned Sketcher";
					break;
			case 4: state.title = "Accomplished Artiste";
					break;
			case 5: state.title = "Level Five Clever Text";
					break;
			case 6: state.title = "Level Six Clever And Cool Text";
					break;
			case 7: state.title = "Actually Aesthetic";
					break;
			case 8: state.title = "A R T I S T";
					break;
			default: state.title = "you broke me. :(";
					 break;
			}
			
			System.out.println("You've leveled up! You are now level " +state.level + "! People have started to call you the " + state.title);
		}
		else
			state.xp 	+= xpAmount;
		}
	
	/* passTime method:
	 * 		This method increments the time. This handles checks for
	 * 		incrementing day at 24 hours,
	 * 		month at 30 days and incrementing year at 12
	 * 		months and 30 days.
	 */
	public void passTime(int hrs) {
		if(hrs == -1) {
			if(state.date[2] == 30) {
				if(state.date[1] == 12) 
					state.date[0] ++;
				state.date[1] ++;
				state.date[2] = 0;
			}
			else {
				state.date[2] ++;
			}
		 state.hour = 1;
		}
		else if((state.hour + hrs) > 24) {
			int hr = 24 - state.hour;
			if(state.date[2] == 30) {
				if(state.date[1] == 12) 
					state.date[0] ++;
				state.date[1] ++;
				state.date[2] = 0;
			}
			else {
				state.date[2] ++;
			}
			state.hour = hr + hrs;
			state.has_napped = false;
			state.coffee_used = false;
		}
		
		state.hour += hrs;
		
	}
	
}
	

