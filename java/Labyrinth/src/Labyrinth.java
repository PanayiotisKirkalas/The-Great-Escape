//import java.io.*;
import java.util.*;
//import javafx.util.*;
//import java.lang.*;
//import java.math.*;

class Labyrinth
{
	public static boolean show_walls = false;
	private int n_walls;
	private coordinate start, finish;
	private CharSequence pName;
	private ArrayList<ArrayList<Character>> map;

	private coordinate translate(coordinate c) {
		c.y_axis = (c.y_axis * 2) + 2;
		c.x_axis = (c.x_axis * 2) + 2;
		return c;
	}

public
	Labyrinth() {
		n_walls = 0;
		char side = 'A', column = '1';
		ArrayList<Character> row = new ArrayList<Character>();

		row.add(' ');
		row.add('|');
		for (int i = 0; i < (6 * 2); ++i) {
			row.add(column);
			row.add(' ');
			++column;
		}
		map.add(row);
		row.clear();

		row.add('=');
		row.add('+');
		for (int i = 0; i < (6 * 2); ++i) {
			row.add('=');
		}
		map.add(row);
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

			map.add(row);
			row.clear();
		}
	}

	void SetName(CharSequence name) {
		this.pName = name;
	}
	
	char at(coordinate pos) {
		pos = translate(pos);
		return map.get(pos.y_axis).get(pos.x_axis);
	}
	
	void alter(coordinate pos, char c) {
		pos = translate(pos);
		map.get(pos.y_axis).set(pos.x_axis, c);
		return;
	}
	
	boolean BuildWall(coordinate pos, int dir) {
		pos = translate(pos);
		
		switch (dir) {
		case 1://Up
			--pos.y_axis;
			break;
		case 2://Right
			++pos.x_axis;
			break;
		case 3://Down
			++pos.y_axis;
			break;
		case 4://Left
			--pos.x_axis;
			break;
		}

		if (!map.get(pos.y_axis).get(pos.x_axis).equals(' '))
			return false;
		if (map.get(pos.y_axis).get(pos.x_axis).equals(' '))
			this.AddDelWall(1);
		if (dir == 1 || dir == 3) {
			map.get(pos.y_axis).set(pos.x_axis, '|');
		}
		else if (dir == 2 || dir == 4) {
			map.get(pos.y_axis).set(pos.x_axis, '=');
		}
		return true;
	}
	
	boolean EraseWall(coordinate pos, int dir) {
		pos = translate(pos);

		switch (dir) {
		case 1://Up
			--pos.y_axis;
			break;
		case 2://Right
			++pos.x_axis;
			break;
		case 3://Down
			++pos.y_axis;
			break;
		case 4://Left
			--pos.x_axis;
			break;
		}

		if ((pos.y_axis <= 2 && dir == 1) || (pos.x_axis <= 2 && dir == 4))
			return false;
		if (!map.get(pos.y_axis).get(pos.x_axis).equals(' '))
			this.AddDelWall(-1);
		map.get(pos.y_axis).set(pos.x_axis, ' ');
		return true;
	}
	boolean isClosed(coordinate pos, char dir) {
		pos = translate(pos);
		switch (dir) {
		case 1://Up
			return (!map.get(pos.y_axis - 1).get(pos.x_axis).equals(' ') && pos.y_axis > 2 ? true : false);
		case 3://Down
			return (!map.get(pos.y_axis + 1).get(pos.x_axis).equals(' ') && pos.y_axis < 12 ? true : false);
		case 4://Left
			return (!map.get(pos.y_axis).get(pos.x_axis - 1).equals(' ') && pos.x_axis > 2 ? true : false);
		case 2://Right
			return (!map.get(pos.y_axis).get(pos.x_axis + 1).equals(' ') && pos.x_axis < 12 ? true : false);
		default:
			return false;
		}
	}
	void printLabyrinth(boolean show_walls) {
		System.out.println("Player: " + pName);
		char c;
		for (int i = 0; i < 14; ++i) {
			for (int j = 0; j < 14; ++j) {
				c = map.get(i).get(j);
				System.out.println((i >= 2 && j >= 2) && (c == '|' || c == '=') && !show_walls ? ' ' : c);
			}
			System.out.println();
		}
	}
	void printRow(int index) {
		for (int i = 0; i < 14; ++i) {
			System.out.println(map.get(index).get(i));
		}
	}
	coordinate getStart() {
		coordinate c = this.start;
		c.y_axis = (c.y_axis / 2) - 1;
		c.x_axis = (c.x_axis / 2) - 1;
		return c;
	}
	coordinate getFinish() {
		coordinate c = this.finish;
		c.y_axis = (c.y_axis / 2) - 1;
		c.x_axis = (c.x_axis / 2) - 1;
		return c;
	}
	coordinate SetupLabyrinth() {
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

		do {
			start.y_axis = t.nextInt(6);
			start.x_axis = t.nextInt(6);
			finish.y_axis = t.nextInt(6);
			finish.x_axis = t.nextInt(6);
		} while (Math.sqrt(Math.pow(start.y_axis - finish.y_axis, 2)+Math.pow(start.x_axis - finish.x_axis, 2)) < 4.75);

		this.setStart(start);
		this.setFinish(finish);
		return start;
	}
	void GenerateLabyrinth() {
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
			temp = pos;
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

			pos = grid.getRandomOpen();
		}

		while (n_walls > 20) {
			dir = 1 + t.nextInt(4);
			pos = grid.getRandomOpen();
			this.EraseWall(pos, dir);
		}
	}
	void setStart(coordinate pos) {
		this.start = translate(pos);
		this.alter(pos, 'S');
	}
	void setFinish(coordinate pos) {
		this.finish = translate(pos);
		this.alter(pos, 'F');
	}
	void AddDelWall(int condition) {
		this.n_walls += condition;
	}
	int getNofWalls() {
		return this.n_walls;
	}
};
