import java.util.*;
import java.util.Scanner;

class Solver {
	private Stack<Branch> branches;
	private coordinate pos;
	private Labyrinth l;
	private boolean checkedRooms[][];

	public Solver(Labyrinth other) {
		checkedRooms = new boolean[6][6];
		branches = new Stack<Branch>();
		pos = other.getStart();
		l = other;
		for (int i = 0; i < 6; ++i) {
			for (int k = 0; k < 6; ++k) {
				checkedRooms[i][k] = false;
			}
		}
	}
	
	private int Move(int dir, boolean on_branch) {
		if (on_branch) {
			branches.peek().directions[dir] = false;
			--branches.peek().openDirN;
		}

		checkedRooms[pos.y_axis][pos.x_axis] = true;

		switch (dir) {
		case 0://Up
			--pos.y_axis;
			break;
		case 1://Right
			++pos.x_axis;
			break;
		case 2://Down
			++pos.y_axis;
			break;
		case 3://Left
			--pos.x_axis;
			break;
		}

		return (dir + 2) % 4;
	}
	private coordinate calculatePos(coordinate pos, int dir) {
		coordinate temp = new coordinate(pos);
		switch (dir) {
		case 0://Up
			--temp.y_axis;
			break;
		case 1://Right
			++temp.x_axis;
			break;
		case 2://Down
			++temp.y_axis;
			break;
		case 3://Left
			--temp.x_axis;
			break;
		default:
			break;
		}

		return temp;
	}

	public boolean Solve() {
		boolean directions[] = { true, true, true, true }, deja_vu = false, close_path = false;
		char dir[] = { 'w', 'd', 's', 'a' };
		int openDir = 4, openDirN = 4, prev = -1;
		coordinate temp = new coordinate();

		if (l.getNofWalls() > 20) {
			return false;
		}
		
		while (true) {
			openDirN = 4;

			if (this.pos.equals(l.getFinish()))
				return true;
			System.out.println("Current pos: " + (char)('A' + pos.y_axis) + "" + (pos.x_axis + 1));
			System.out.println("Final pos: " + (char)('A' + l.getFinish().y_axis) + "" + (l.getFinish().x_axis + 1));

			for (int i = 0; i < 4; ++i) {
				close_path = false;
				//System.out.println("Position: " + (char)('A' + pos.y_axis) + "" + (pos.x_axis + 1));
				temp = calculatePos(pos, i);
				//System.out.println("iteration: " + i + " y:" + (char)('A' + temp.y_axis) + "/x:" + temp.x_axis);
				if (i == 0 && pos.y_axis <= 0) {
					close_path = true;
					//System.out.println("Above is closed");
				}
				else if (i == 1 && pos.x_axis >= 5){
					close_path = true;
					//System.out.println("Right is closed");
				}
				else if (i == 2 && pos.y_axis >= 5){
					close_path = true;
					//System.out.println("Below is closed");
				}
				else if (i == 3 && pos.x_axis <= 0){
					close_path = true;
					//System.out.println("Left is closed");
				}
				else if (i == prev) {
					close_path = true;
					//System.out.println("Came from" + i);
				}
				else if (!directions[i]){
					close_path = true;
					//System.out.println("Direction: " + i + " is already closed");
				}
				else if (l.isClosed(this.pos, dir[i])) {
					close_path = true;
					//System.out.println("Direction: " + i + " is closed");
				}
				else if (checkedRooms[temp.y_axis][temp.x_axis]) {
					close_path = true;
					//System.out.println("Has already been here");
				}
				else {
					//System.out.println("Direction: " + i + " is open");
				}
				
				if (close_path) {
					directions[i] = false;
					--openDirN;
					continue;
				}
				openDir = i;
			
			}

			//System.out.println("N of open paths: " + openDirN + " | Selected path: " + openDir + '\n');
			
			
			if (openDirN == 0) {
//				System.out.println("N of open paths: " + branches.peek().openDirN + " | N of Branches: " + branches.size());
//				System.out.println("Branch Position: " + (char)('A' + branches.peek().pos.y_axis) + "/" + branches.peek().pos.x_axis);
				if (branches.isEmpty())
					return false;
				if (pos.equals(branches.peek().pos)) {
					branches.pop();
				}
				if (branches.isEmpty())
					return false;

				//pos = branches.peek().pos;
				pos.copy(branches.peek().pos);
				openDirN = branches.peek().openDirN;
				for (int i = 0; i < 4; ++i) {
					directions[i] = branches.peek().directions[i];
				}
				deja_vu = true;
				prev = -1;
			}
			else if (openDirN == 1) {
				prev = Move(openDir, (branches.empty() ? false : pos.equals(branches.peek().pos)));

				for (int i = 0; i < 4; ++i) {
					directions[i] = true;
				}
				directions[prev] = false;
				
				deja_vu = false;
			}
			else if (openDirN > 1) {
				if (!deja_vu) {
					branches.push(new Branch());
					//branches.peek().pos = this.pos;
					branches.peek().pos.copy(this.pos);
					for (int i = 0; i < 4; ++i) {
						branches.peek().directions[i] = directions[i];
					}
					
					if (prev > -1) {
						branches.peek().directions[prev] = false;
						--branches.peek().openDirN;
					}
				}
				else 
					deja_vu = false;
				
				prev = Move(openDir, true);
			}

			if (prev >= 0) {
				for (int i = 0; i < 4; ++i) {
					directions[i] = true;
				}
				directions[prev] = false;
			}
		}
	}
};
