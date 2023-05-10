import java.util.List;
import java.util.*;

class Grid {
	List<coordinate> closed, open, frontier;
	Room[][] rooms;

	public Grid() {
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
		this.frontier.remove(pos);
		this.closed.remove(pos);

		// else-if not used bcs they function separately
		if (pos.y_axis > 0 && rooms[pos.y_axis - 1][pos.x_axis].closed == true) {
			rooms[pos.y_axis - 1][pos.x_axis].frontier = true;
			this.frontier.add(coordinate.make_pair(pos.y_axis - 1, pos.x_axis));
		}
		if (pos.y_axis < 5 && rooms[pos.y_axis + 1][pos.x_axis].closed == true) {
			rooms[pos.y_axis + 1][pos.x_axis].frontier = true;
			this.frontier.add(coordinate.make_pair(pos.y_axis + 1, pos.x_axis));
		}
		if (pos.x_axis > 0 && rooms[pos.y_axis][pos.x_axis - 1].closed == true) {
			rooms[pos.y_axis][pos.x_axis - 1].frontier = true;
			this.frontier.add(coordinate.make_pair(pos.y_axis, pos.x_axis - 1));
		}
		if (pos.x_axis < 5 && rooms[pos.y_axis][pos.x_axis + 1].closed == true) {
			rooms[pos.y_axis][pos.x_axis + 1].frontier = true;
			this.frontier.add(coordinate.make_pair(pos.y_axis, pos.x_axis + 1));
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
