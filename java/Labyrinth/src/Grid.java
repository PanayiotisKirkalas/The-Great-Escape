/*
 * it represents a smaller version of the labyrinth and is used to randomly generate one by the Mode1Playr class
 */

import java.util.LinkedList;
import java.util.*;

class Grid {
	LinkedList<coordinate> closed, open, frontier;
	Room[][] rooms;

	public Grid() {
		closed = new LinkedList<coordinate>();
		open = new LinkedList<coordinate>();
		frontier = new LinkedList<coordinate>();
		rooms = new Room[6][6];
		for (int i = 0; i < 6; ++i) {
			for (int j = 0; j < 6; ++j) {
				rooms[i][j] = new Room();
				closed.add(coordinate.make_pair(i, j));//new Pair<Integer, Integer>
			}
		}
	}
	int Open(coordinate pos) {
		//it flags a location on the labyrinth as open, and its neighboring locations as "to be opened"
		//opening to neighboring locations removes the wall between them
		rooms[pos.y_axis][pos.x_axis].open = true;
		rooms[pos.y_axis][pos.x_axis].closed = false;
		rooms[pos.y_axis][pos.x_axis].frontier = false;

		this.open.add(coordinate.make_pair(pos.y_axis, pos.x_axis));
		this.frontier.remove(new coordinate(pos));
		this.closed.remove(new coordinate(pos));

		coordinate temp = new coordinate(pos);
		if (pos.y_axis > 0 && rooms[pos.y_axis - 1][pos.x_axis].closed == true) {
			--temp.y_axis;
			rooms[pos.y_axis - 1][pos.x_axis].frontier = true;
			this.frontier.add(new coordinate(temp));
		}
		if (pos.y_axis < 5 && rooms[pos.y_axis + 1][pos.x_axis].closed == true) {
			++temp.y_axis;
			rooms[pos.y_axis + 1][pos.x_axis].frontier = true;
			this.frontier.add(new coordinate(temp));
		}
		if (pos.x_axis > 0 && rooms[pos.y_axis][pos.x_axis - 1].closed == true) {
			--temp.x_axis;
			rooms[pos.y_axis][pos.x_axis - 1].frontier = true;
			this.frontier.add(new coordinate(temp));
		}
		if (pos.x_axis < 5 && rooms[pos.y_axis][pos.x_axis + 1].closed == true) {
			++temp.x_axis;
			rooms[pos.y_axis][pos.x_axis + 1].frontier = true;
			this.frontier.add(new coordinate(temp));
		}
		
		return this.open.size();
	}
	List<coordinate> getOpen() {
		return this.open;
	}
	List<coordinate> getClosed() {
		return this.closed;
	}
	List<coordinate> getFrontier() {
		return this.frontier;
	}
	coordinate getRandomOpen() {
		Random rand = new Random();
	    coordinate randomElement = open.get(rand.nextInt(open.size()));
		return randomElement;
	}
};
