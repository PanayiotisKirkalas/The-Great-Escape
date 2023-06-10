/*
 * It represents the labyrinth. Each Player object contains one. 
 * It also allows for other classes to alter a labyrinth by providing appropriate methods
 */

import java.util.*;

class Labyrinth
{
	public static boolean inner = false; //flag for when one of its own methods calls the Alter
	private int n_walls;				 //number of walls
	private coordinate start, finish;	 //starting and finishing point
	private Player player;				 //the player that is either making the labyrinth or going to solve it
	private GameScreen game;			 //the window that will show the labyrinth to the player
	private ArrayList<ArrayList<Character>> map; //a matrix that internally represents the labyrinth

	private coordinate translate(coordinate c) { 
		//the other classes and this one refer to the same points on the labyrinth with different values
		//so a function was needed to translate the values from the external set to these of the internal
		coordinate temp = new coordinate(c);
		temp.y_axis = (temp.y_axis * 2) + 2;
		temp.x_axis = (temp.x_axis * 2) + 2;
		return temp;
	}
	
	public Labyrinth(GameScreen p_game) { //it sets the labyrinth on its initial state with only the very basic walls
		this.game = p_game;
		n_walls = 0;
		map = new ArrayList<ArrayList<Character>>();
		start = new coordinate();
		finish = new coordinate();
		char side = 'A', column = '1';
		ArrayList<Character> row = new ArrayList<Character>(); //it first readies a row and then add it to actual labyrinth

		//the first row that contains the numbers above is added
		row.add(' ');
		row.add('|');
		for (int i = 0; i < 6; ++i) {
			row.add(column);
			row.add(' ');
			++column;
		}
		map.add(new ArrayList<Character>(row));
		row.clear();

		//the second row that contains the upper walls to separate the top row from the labyrinth is added
		row.add('=');
		row.add('+');
		for (int i = 0; i < (6 * 2); ++i) {
			row.add('=');
		}
		map.add(new ArrayList<Character>(row));
		row.clear();

		//the rest of the rows are made and added
		for (int i = 0; i < (6 * 2); ++i) {
			if (i % 2 == 0) {
				row.add(side); //it adds the letter on the far left of some rows
				++side;
				row.add('|');
				for (int k = 0; k < 6; ++k) {
					row.add(' ');
					row.add(' ');
				}
			}
			else {
				row.add(' ');
				row.add('|');
				for (int k = 0; k < 6; ++k) {
					row.add(' ');
					row.add('+');
				}
			}
			map.add(new ArrayList<Character>(row));
			row.clear();
		}
	}

	public char at(coordinate pos) { //return the character representing the sate of a point in the labyrinth
		coordinate temp = (translate(pos));
		return map.get(temp.y_axis).get(temp.x_axis);
	}
	
	public char ingameAt(coordinate pos) { 
		//return the character representing the sate of a point in the labyrinth for when an internal method needs it
		//so no value translation is needed
		return map.get(pos.y_axis).get(pos.x_axis);
	}
	
	public void alter(coordinate pos, char c) { //changes the state of a point in labyrinth
		//translates the position values given if the call isn't made from an internal method
		coordinate temp = (inner) ? (pos) : translate(pos);
		map.get(temp.y_axis).set(temp.x_axis, c);
		temp = (!inner) ? translate(pos) : temp;
		game.Alter(temp, c);
		inner = false;
		return;
	}
	
	public boolean BuildWall(coordinate pos, int dir) {//it adds a wall given a position and a direction
		coordinate temp = (translate(pos));
		
		switch (dir) {
		case 1://Up
			--temp.y_axis;
			break;
		case 2://Right
			++temp.x_axis;
			break;
		case 3://Down
			++temp.y_axis;
			break;
		case 4://Left
			--temp.x_axis;
			break;
		}

		if (!map.get(temp.y_axis).get(temp.x_axis).equals(' '))
			return false;
		if (map.get(temp.y_axis).get(temp.x_axis).equals(' '))
			this.AddDelWall(1);
		if (dir == 1 || dir == 3) {
			inner = true;
			alter(temp, '=');
		}
		else if (dir == 2 || dir == 4) {
			inner = true;
			alter(temp, '|');
		}
		return true;
	}
	
	public boolean EraseWall(coordinate pos, int dir) {//it removes a wall given a position and a direction
		coordinate temp = (translate(pos));

		switch (dir) {
		case 1://Up
			--temp.y_axis;
			break;
		case 2://Right
			++temp.x_axis;
			break;
		case 3://Down
			++temp.y_axis;
			break;
		case 4://Left
			--temp.x_axis;
			break;
		}

		if ((temp.y_axis <= 2 && dir == 1) || (temp.x_axis <= 2 && dir == 4))
			return false;
		if (!map.get(temp.y_axis).get(temp.x_axis).equals(' '))
			this.AddDelWall(-1);
		inner = true;
		alter(temp, ' ');
		return true;
	}
	public boolean isClosed(coordinate pos, char dir) {
		//tells the caller class if there is wall somewhere given a position and a direction
		coordinate temp = translate(pos);
		switch (dir) {
		case 'w'://Up
			return (!map.get(temp.y_axis - 1).get(temp.x_axis).equals(' ') && temp.y_axis > 2 ? true : false);
		case 's'://Down
			return (!map.get(temp.y_axis + 1).get(temp.x_axis).equals(' ') && temp.y_axis < 12 ? true : false);
		case 'a'://Left
			return (!map.get(temp.y_axis).get(temp.x_axis - 1).equals(' ') && temp.x_axis > 2 ? true : false);
		case 'd'://Right
			return (!map.get(temp.y_axis).get(temp.x_axis + 1).equals(' ') && temp.x_axis < 12 ? true : false);
		default:
			return false;
		}
	}
	public coordinate getStart() {//returns staring point
		coordinate c = new coordinate(this.start);
		c.y_axis = (c.y_axis / 2) - 1;
		c.x_axis = (c.x_axis / 2) - 1;
		return c;
	}
	public coordinate getFinish() {//returns finishing point
		coordinate c = new coordinate(this.finish);
		c.y_axis = (c.y_axis / 2) - 1;
		c.x_axis = (c.x_axis / 2) - 1;
		return c;
	}
	void SetupLabyrinth() {//makes basic needed preparations for the labyrinth to be randomly generated
		Random t = new Random();
		coordinate start = new coordinate(), finish = new coordinate();

		for (int i = 0; i < 6; ++i) {
			for (int j = 0; j < 6; ++j) {
				this.BuildWall(coordinate.make_pair(i, j), 1);
				this.BuildWall(coordinate.make_pair(i, j), 4);
				if (i < 5)
					this.BuildWall(coordinate.make_pair(i, j), 3);
				if (j < 5)
					this.BuildWall(coordinate.make_pair(i, j), 2);
			}
		}
		
		//System.out.println("Checking distance");
		do {
			start.y_axis = t.nextInt(6);
			start.x_axis = t.nextInt(6);
			finish.y_axis = t.nextInt(6);
			finish.x_axis = t.nextInt(6);
		} while (Math.sqrt(Math.pow(start.y_axis - finish.y_axis, 2)+Math.pow(start.x_axis - finish.x_axis, 2)) < 4.75);

		this.setStart(start);
		this.setFinish(finish);
	}
	public coordinate GenerateLabyrinth() {//randomly generates a labyrinth
		int dir;
		coordinate pos = new coordinate(), temp = new coordinate();
		Grid grid = new Grid();
		Random t = new Random();

		SetupLabyrinth();
		
		pos.y_axis = t.nextInt(6);
		pos.x_axis = t.nextInt(6);

		grid.Open(pos);
		while (grid.getClosed().size() > 0) {
			dir = 1 + t.nextInt(4);
			temp.copy(pos);
			switch (dir % 2)
			{
			case 1:
				temp.y_axis += (dir == 1 ? -1 : 1);
				break;
			case 0:
				temp.x_axis += (dir == 2 ? 1 : -1);
				break;
			default:
				break;
			}
			if (grid.getFrontier().contains(temp)) {
				grid.Open(temp);
				this.EraseWall(pos, dir);
			}

			pos.copy(grid.getRandomOpen());
		}

		while (n_walls > 20) {
			dir = 1 + t.nextInt(4);
			pos = grid.getRandomOpen();
			this.EraseWall(pos, dir);
		}

		return this.getStart();
	}
	public void setStart(coordinate pos) {//sets starting point
		this.start.copy(translate(pos));
		inner = false;
		this.alter(pos, 'S');
	}
	public void setFinish(coordinate pos) {//sets finishing point
		this.finish.copy(translate(pos));
		inner = false;
		this.alter(pos, 'f');
	}
	private void AddDelWall(int condition) {//increases or decreases the recorded number of walls
		this.n_walls += condition;
	}
	public int getNofWalls() {//returns the number of walls
		return this.n_walls;
	}
	
	public void setGameScreen(GameScreen p_game) {//sets the window that the labyrinth will be shown
		this.game = p_game;
	}
	
	public void SetPlayer(Player p_player) {//sets the player that is either making the labyrinth or going to solve it
		player = p_player;
	}
	
	public Player getPlayer() {//returns the player that is either making the labyrinth or going to solve it
		return player;
	}
};
