/*
 * Represents a point of the labyrinth in a Grid
 */
public class Room {
	public boolean open, frontier, closed;
	Room() {
		open = false;
		frontier = false;
		closed = true;
	}
};
