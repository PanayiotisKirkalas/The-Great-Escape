import java.util.*;

class Labyrinth
{
	public static boolean show_walls = false, inner = false;
	private int n_walls;
	private coordinate start, finish;
	private Player player;
	private GameScreen game;
	private ArrayList<ArrayList<Character>> map;

	private coordinate translate(coordinate c) {
		coordinate temp = new coordinate(c);
		temp.y_axis = (temp.y_axis * 2) + 2;
		temp.x_axis = (temp.x_axis * 2) + 2;
		return temp;
	}
	
public Labyrinth(GameScreen p_game) {
		this.game = p_game;
		n_walls = 0;
		map = new ArrayList<ArrayList<Character>>();
		start = new coordinate();
		finish = new coordinate();
		char side = 'A', column = '1';
		ArrayList<Character> row = new ArrayList<Character>();

		row.add(' ');
		row.add('|');
		for (int i = 0; i < 6; ++i) {
			row.add(column);
			row.add(' ');
			++column;
		}
		map.add(new ArrayList<Character>(row));
		row.clear();

		row.add('=');
		row.add('+');
		for (int i = 0; i < (6 * 2); ++i) {
			row.add('=');
		}
		map.add(new ArrayList<Character>(row));
		row.clear();

		for (int i = 0; i < (6 * 2); ++i) {
			if (i % 2 == 0) {
				row.add(side);
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

	char at(coordinate pos) {
		coordinate temp = (translate(pos));
		return map.get(temp.y_axis).get(temp.x_axis);
	}
	
	char ingameAt(coordinate pos) {
		return map.get(pos.y_axis).get(pos.x_axis);
	}
	
	void alter(coordinate pos, char c) {
		coordinate temp = (inner) ? (pos) : translate(pos);
		map.get(temp.y_axis).set(temp.x_axis, c);
		temp = (!inner) ? translate(pos) : temp;
		game.Alter(temp, c);
		inner = false;
		return;
	}
	
	boolean BuildWall(coordinate pos, int dir) {
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
	
	boolean EraseWall(coordinate pos, int dir) {
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
	boolean isClosed(coordinate pos, char dir) {
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
	coordinate getStart() {
		coordinate c = new coordinate(this.start);
		c.y_axis = (c.y_axis / 2) - 1;
		c.x_axis = (c.x_axis / 2) - 1;
		return c;
	}
	coordinate getFinish() {
		coordinate c = new coordinate(this.finish);
		c.y_axis = (c.y_axis / 2) - 1;
		c.x_axis = (c.x_axis / 2) - 1;
		return c;
	}
	void SetupLabyrinth() {
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
	coordinate GenerateLabyrinth() {
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
	void setStart(coordinate pos) {
		this.start.copy(translate(pos));
		inner = false;
		this.alter(pos, 'S');
	}
	void setFinish(coordinate pos) {
		this.finish.copy(translate(pos));
		inner = false;
		this.alter(pos, 'f');
	}
	void AddDelWall(int condition) {
		this.n_walls += condition;
	}
	int getNofWalls() {
		return this.n_walls;
	}
	
	public void setGameScreen(GameScreen p_game) {
		this.game = p_game;
	}
	
	public void SetPlayer(Player p_player) {
		player = p_player;
	}
	
	public Player getPlayer() {
		return player;
	}
};
