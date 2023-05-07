import java.io.*;
import java.util.*;

public class Player {
	public static Scanner keyboard = new Scanner(System.in).useDelimiter("\\\n");
	
	protected int id;
	protected char symbol;
	protected coordinate pos;
	protected Labyrinth Own;
	protected static final char PLAYER_SYM = 'O';
	protected static final char PLAYER_RIGHT = '>';
	protected static final char PLAYER_LEFT = '<';
	protected static final char PLAYER_UP = '^';
	protected static final char PLAYER_DOWN = 'v';
	protected static final char _VWALL_ = '|';
	protected static final char _HWALL_ = '=';
	protected static final char _JOINT_ = '+';
	protected static final char _ROOM_ = ' ';
	protected static final boolean HIT_WALL = true;
	protected static final boolean WON = false;
	protected static final boolean DONT_SHOW_WALLS = false;
	protected static final boolean SHOW_WALLS = true;
	protected static final int HIT_WALL2 = -2;
	protected static final int WON2 = -1;
	protected static final int LOST = -3;
	protected static final int PLAYER_LIVES = 2;
	protected static final int KEY_UP = 119; // ASCII w
	protected static final int KEY_DOWN = 97; // ASCII a
	protected static final int KEY_LEFT = 115; // ASCII s
	protected static final int KEY_RIGHT = 100; // ASCII d
	
	public String Name;
	
	public Player(int id)
	{
		this.id = id;
		this.symbol = PLAYER_SYM;
	}

	public void askName()
	{
		System.out.print("Username: ");
		this.Name = keyboard.nextLine();
		this.Own.setName(this.Name);
	}
	
	public String getName()
	{
		return this.Name;
	}
	
	public char getSymbol()
	{
		return this.symbol;
	}
	
	public coordinate getPos()
	{
		return this.pos;
	}
	
	public int getID()
	{
		return this.id;
	}
	
	public void setPos(coordinate p)
	{
		this.pos = p;
	}
	
	public Labyrinth getLabyrinth()
	{
		return this.Own;
	}
}
