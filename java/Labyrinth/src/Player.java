import java.io.*;
import java.util.*;

public class Player {
	public static Scanner keyboard = new Scanner(System.in).useDelimiter("\\\n");
	protected static final char PLAYER_SYM = 'O';
	protected static final char PLAYER_RIGHT = '>';
	protected static final char PLAYER_LEFT = '<';
	protected static final char PLAYER_UP = '^';
	protected static final char PLAYER_DOWN = 'v';
	protected static final char _VWALL_ = '|';
	protected static final char _HWALL_ = '=';
	protected static final char _JOINT_ = '+';
	protected static final char _ROOM_ = ' ';
	protected static final int HIT_WALL = 1;
	protected static final int WON = 0;
	protected static final int DONT_SHOW_WALLS = 0;
	protected static final int SHOW_WALLS = 1;
//	protected static final int HIT_WALL2 = -2;
//	protected static final int WON2 = -1;
	protected static final int LOST = -3;
	protected static final int PLAYER_LIVES = 2;
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
	}

	public int Move(char direction) {
		return 0;
	}
	
	public void askName()
	{
//		System.out.print("Username: ");
		this.Name = Main.AskUser(game, "Username: ");
		this.Own.SetPlayer(this);
	}
	
	public String getName()
	{
		return new String(this.Name);
	}
	
	public char getSymbol()
	{
		return this.symbol;
	}
	
	public coordinate getPos()
	{
		return new coordinate(this.pos);
	}
	
	public int getID()
	{
		return this.id;
	}
	
	public void setPos(coordinate p)
	{
		this.pos.copy(p);
	}
	
	public void setGameScreen(GameScreen p_game) {
		this.game = p_game;
		this.Own.setGameScreen(p_game);
	}
	
	public Labyrinth getLabyrinth()
	{
		return this.Own;
	}
	
	public void setLabyrinth(Labyrinth l) {
		this.Own = l;
	}
}
