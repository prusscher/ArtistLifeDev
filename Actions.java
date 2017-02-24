package edu.sadsnails.game;

public class Actions {
	//posting
	//sleeping
	
	//energy & time - usage of both
	
	private State state;
	
	public Actions(State state){
		this.state = state;
	}
	
	public void makeArt() {		//placeholder instructions
		System.out.println("I made art");
		state.energy -= 20;
		state.hour   += 2;
		state.money  += 10;

		incXP(30);
		
		state.printStates();
		
	}
	
	public void sleep() {		//placeholder instructions
		System.out.println("I slept");
		state.energy	= 100;
		state.date[2]	++;
		state.hour		= 1;
		
		state.printStates();
	}
	
	public void incXP(int xpAmount) { //method for incrementing the experience according to value passed
		if(xpAmount + state.xp == 30) {
			
			state.xp	+= 30;
			state.level	++;
			System.out.println("You've leveled up! You are now level " +state.level);
		}
		else
			state.xp 	+= xpAmount;
		
	}
	
}
