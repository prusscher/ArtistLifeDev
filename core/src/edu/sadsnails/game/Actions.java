package edu.sadsnails.game;

public class Actions {
	
	/* -----------------------------------------------------------------
	 * 	This class serves the purpose of maintaining all of the possible
	 *	actions within the game.
	 *	
	 *	For now, the methods contain placeholder instructions
	 *	that do basic versions of the tasks they are to perform.
	 *
	 *	These placeholder instructions will hopefully be replaced
	 *	with better versions in sprint 2.
	 * -----------------------------------------------------------------*/
	
	private State state;
	
	public Actions(State state){
		this.state = state;
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
	public void makeArt() {		//placeholder instructions
		if((state.hour + 2) >= 24) {
			nextDay();
		}
		if(state.energy >= 20) {
			System.out.println("I made art");
			state.energy -= 20;
			state.money  += 10;
			state.earned_money += 10;
	
			incXP(30);
		} 
		if(state.energy == 0) {
			sleep(2);
		}
		state.hour   += 2;
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
		System.out.println("I attempted to sleep");
		if(state.energy == 0) {
			System.out.println("You passed out from exhaustion");
			nextDay();
			state.energy	= 80;
		}
		else {
			System.out.println("I successfully slept");
			nextDay();
			state.energy = 100;
		}
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

				if((state.hour + 12) >= 24) {
					nextDay();
				}
				else
					state.hour += 12;
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
		//placeholder instructions
		if(state.coffee_used) 
			System.out.println("I have already had coffee today");
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
		//placeholder instructions		
		
		if(xpAmount + state.xp >= state.toNext && state.level != 8) {
			//level up, if you're not max level
			
			state.xp	+= xpAmount;
			state.level	++;
			state.toNext *= 2;
			
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
	
	/* nextDay method:
	 * 		This method increments the day. This handles checks for
	 * 		incrementing month at 30 days and incrementing year at 12
	 * 		months and 30 days.
	 */
	public void nextDay() {
		state.coffee_used = false;
		if(state.date[2] == 30) {
			if(state.date[1] == 12) {
				state.date[0]++; state.date[1] = 1; state.date[2] = 1;
			}
			else {
				state.date[1]++; state.date[2] = 1;
			}
		}
		else
			state.date[2]++;
		state.hour = 1;
	}
	
}
	

