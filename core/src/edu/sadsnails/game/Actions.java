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
	
	public void makeArt() {		
		//placeholder instructions
		System.out.println("I made art");
		state.energy -= 20;
		state.hour   += 2;
		state.money  += 10;

		incXP(30);
		
		state.printStates();
		
	}
	
	public void sleep() {		
		//placeholder instructions
		System.out.println("I slept");
		state.energy	= 100;
		state.date[2]	++;
		state.hour		= 1;
		
		state.printStates();
	}
	
	public void incXP(int xpAmount) {
		//placeholder instructions		
		
		if(xpAmount + state.xp >= state.toNext) {
			
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
			
			System.out.println("You've leveled up! You are now level " +state.level + ", a " + state.title);
		}
		else
			state.xp 	+= xpAmount;
		
	}
	
}
