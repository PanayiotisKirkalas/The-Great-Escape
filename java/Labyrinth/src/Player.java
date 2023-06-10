/*
 * Is the super class for Mode1Player and Mode2Player. It contains common attributes and methods
 */

public class Player {
	protected static final char PLAYER_SYM = 'O';
	protected static final char _VWALL_ = '|';
	protected static final char _HWALL_ = '=';
	protected static final char _JOINT_ = '+';
	protected static final char _ROOM_ = ' ';
	protected static final int HIT_WALL = 1;
	protected static final int WON = 0;
	protected static final char KEY_UP = 'w'; // ASCII w
	protected static final char KEY_DOWN = 's'; // ASCII a
	protected static final char KEY_LEFT = 'a'; // ASCII s
	protected static final char KEY_RIGHT = 'd'; // ASCII d
	
	protected int id;
	protected char symbol;
	protected coordinate pos;
	protected Labyrinth Own;
	public String Name;
	protected GameScreen game;
	
	public Player(int id, GameScreen game)
	{
		this.id = id;
		this.symbol = PLAYER_SYM;
		this.game = game;
		this.Own = new Labyrinth(game);
		this.pos = new coordinate();
	}

	public int Move(char direction)//Moves to a given direction. Is called by the Mode.PassToPlayer method
	{
		coordinate temp = new coordinate(this.pos);
		
		if(!Own.isClosed(this.pos, direction))//If there is no wall to that direction
		{
			switch (direction)//Move
			{
				case KEY_UP:
					if (this.pos.y_axis > 0) {
						this.pos.y_axis -= 1;
					}
					break;
				case KEY_DOWN:
					if (this.pos.y_axis < 5) {
						this.pos.y_axis += 1;
					}
					break;
				case KEY_LEFT:
					if (this.pos.x_axis > 0) {
						this.pos.x_axis -= 1;
					}
					break;
				case KEY_RIGHT:
					if (this.pos.x_axis < 5) {
						this.pos.x_axis += 1;
					}
					break;
			}
			
			Own.alter(temp, GameScreen.prev);//Sets its previous position as previous in the labyrinth
			Own.alter(this.pos, this.symbol);//Sets its current position in the labyrinth
			return 0;
		}
		return HIT_WALL;//If there was a wall it returns a specific value to let the mode know
	}
	
	public void askName()//Asks the user the name they want to have in the game
	{
		this.Name = Main.AskUser(game, "Username: ");
		this.Own.SetPlayer(this);
	}
	
	public String getName()//return the name of the player
	{
		return new String(this.Name);
	}
	
	public char getSymbol()
	{
		return this.symbol;
	}
	
	public coordinate getPos()//returns the position of the player
	{
		return new coordinate(this.pos);
	}
	
	public int getID()//returns the id of the player
	{
		return this.id;
	}
	
	public void setPos(coordinate p)//sets the position of the player
	{
		this.pos.copy(p);
	}
	
	public void setGameScreen(GameScreen p_game) {//sets the game window the player will work with
		this.game = p_game;
		this.Own.setGameScreen(p_game);
	}
	
	public Labyrinth getLabyrinth()//returns the labyrinth of the player
	{
		return this.Own;
	}
	
	public void setLabyrinth(Labyrinth l) {//sets the labyrinth of the player
		this.Own = l;
	}
}
