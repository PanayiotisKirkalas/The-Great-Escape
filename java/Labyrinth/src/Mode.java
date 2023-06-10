/*
 * Its the super class to classes representing the different modes. It holds common members and methods 
 */

public class Mode {
	protected Player currPlayer;
	protected GameScreen game;
	public String Explanation = new String();
	
	public void PassToPlayer(char direction) {} //gets called by GameScreen to inform a player class about an input from the user
	
	public void Play() {}//it starts the mode
}
