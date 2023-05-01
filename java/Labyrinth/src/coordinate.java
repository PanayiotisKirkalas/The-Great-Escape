
public class coordinate {
	int y_axis, x_axis;
	
	static public coordinate make_pair(int y, int x) {
		coordinate temp = new coordinate();
		temp.y_axis = y;
		temp.x_axis = x;
		return temp;
	}
}
