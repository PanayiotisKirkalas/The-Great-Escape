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
		rooms[pos.y_axis][pos.x_axis].open = true;
		rooms[pos.y_axis][pos.x_axis].closed = false;
		rooms[pos.y_axis][pos.x_axis].frontier = false;

		this.open.add(coordinate.make_pair(pos.y_axis, pos.x_axis));
		if (!this.frontier.remove(new coordinate(pos))) {
			//System.out.println("Not removed from frontier");
		}
		if (!this.closed.remove(new coordinate(pos))) {
			//System.out.println("Not removedfrom closed");
		}

		//int currSizeF = this.frontier.size();
		// else-if not used bcs they function separately
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
		//if (this.frontier.size() == currSizeF) {System.out.println("Element not added");}
		
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
