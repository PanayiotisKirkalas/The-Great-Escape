import java.io.*;
import java.util.*;

public class Player {
	public static Scanner keyboard = new Scanner(System.in).useDelimiter("\\\n");
	
	protected int id;
	protected String symbol;
	protected coordinate pos;
	protected Labyrinth Own;
	protected static String PLAYER_SYM = "O";
	protected static String PLAYER_RIGHT = ">";
	protected static String PLAYER_LEFT = "<";
	protected static String PLAYER_UP = "^";
	protected static String PLAYER_DOWN = "v";
	protected static String _VWALL_ = "|";
	protected static String _HWALL_ = "=";
	protected static String _JOINT_ = "+";
	protected static String _ROOM_ = " ";
	protected static boolean HIT_WALL = true;
	protected static boolean WON = false;
	protected static boolean DONT_SHOW_WALLS = false;
	protected static boolean SHOW_WALLS = true;
	protected static int HIT_WALL2 = -2;
	protected static int WON2 = -1;
	protected static int LOST = -3;
	protected static int PLAYER_LIVES = 2;
	protected static int KEY_UP = 119; // ASCII w
	protected static int KEY_DOWN = 97; // ASCII a
	protected static int KEY_LEFT = 115; // ASCII s
	protected static int KEY_RIGHT = 100; // ASCII d
	
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
	
	/*
	public boolean Move()
	{
		int c, ex;
		
		this.Own.alter(this.pos, this.symbol);
		
		c = (int)keyboard.next().charAt(0);
		if (c != 0 & c != 224)
			return false;
		
		ex = (int)keyboard.next().charAt(0);
		while (!this.Own.isClosed(pos, ex))
		{
			switch (ex)
			{
				case KEY_UP:
					this.pos.first -= 1;
					break;
				case KEY_DOWN:
					this.pos.first += 1;
					break;
				case KEY_LEFT:
					this.pos.second -= 1;
					break;
				case KEY_RIGHT:
					this.pos.second += 1;
					break;
				default:
					break;
			}
			
			this.Own.alter(this.pos, this.symbol);
			System.out.print("\033[2J\033[1;1H");
			this.Own.printLabyrinth(DONT_SHOW_WALLS);
			
			
			if (this.pos == this.Own.getFinish())
				return WON;
			
			c = (int)keyboard.next().charAt(0);
			if (c != 0 & c != 224)
				return false;
			
			ex = (int)keyboard.next().charAt(0);
		}
		
		return HIT_WALL;
	}
	*/
	
	public String getName()
	{
		return this.Name;
	}
	
	public String getSymbol()
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
