
public class coordinate {
	int y_axis, x_axis;
	
	coordinate() {}
	
	public coordinate(coordinate pos) {
		this.y_axis = pos.y_axis;
		this.x_axis = pos.x_axis;
	}
	
	public void copy(coordinate pos) {
		this.y_axis = pos.y_axis;
		this.x_axis = pos.x_axis;
	}
	
	static public coordinate make_pair(int y, int x) {
		coordinate temp = new coordinate();
		temp.y_axis = y;
		temp.x_axis = x;
		return temp;
	}
}
