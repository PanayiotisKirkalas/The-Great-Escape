import java.util.*;

class Solver {
	private Stack<Branch> branches;
	private coordinate pos;
	private Labyrinth l;
	private boolean checkedRooms[][];

	private int Move(int dir, boolean on_branch) {
		if (on_branch) {
			branches.peek().directions[dir] = false;
			--branches.peek().openDirN;
		}

		checkedRooms[pos.y_axis][pos.x_axis] = true;

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

		return (dir + 2) % 4;
	}
	private coordinate calculatePos(coordinate pos, int dir) {
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
		default:
			break;
		}

		return pos;
	}

	public Solver(Labyrinth other) {
		pos = other.getStart();
		l = other;
		for (int i = 0; i < 6; ++i) {
			for (int k = 0; k < 6; ++k) {
				checkedRooms[i][k] = false;
			}
		}
	}
	public boolean Solve() {
		boolean directions[] = { true, true, true, true }, deja_vu = false, close_path = false;
		char dir[] = { 'w', 'd', 's', 'a' };
		int openDir = 4, openDirN = 4, prev = -1;
		coordinate temp = new coordinate();

		while (true) {
			openDirN = 4;

			if (this.pos == l.getFinish())
				return true;

			for (int i = 0; i < 4; ++i) {
				temp = calculatePos(pos, i);
				if (checkedRooms[temp.y_axis][temp.x_axis])
					close_path = true;
				else if (!directions[i])
					close_path = true;
				else if (l.isClosed(this.pos, dir[i]) || i == prev) 
					close_path = true;
				else if (i == 0 && pos.y_axis <= 0)
					close_path = true;
				else if (i == 1 && pos.x_axis >= 5)
					close_path = true;
				else if (i == 2 && pos.y_axis >= 5)
					close_path = true;
				else if (i == 3 && pos.x_axis <= 0)
					close_path = true;

				if (close_path) {
					directions[i] = false;
					--openDirN;
					continue;
				}
				openDir = i;
			
			}

			
			if (openDirN == 0) {
				if (branches.empty())
					return false;
				if (pos == branches.peek().pos) {
					branches.pop();
				}
				if (branches.empty())
					return false;

				pos = branches.peek().pos;
				openDirN = branches.peek().openDirN;
				for (int i = 0; i < 4; ++i) {
					directions[i] = branches.peek().directions[i];
				}
				deja_vu = true;
				prev = -1;
			}
			else if (openDirN == 1) {
				prev = Move(openDir, (branches.empty() ? false : pos == branches.peek().pos));

				for (int i = 0; i < 4; ++i) {
					directions[i] = true;
				}

				deja_vu = false;
			}
			else if (openDirN > 1) {
				if (!deja_vu) {
					branches.push(new Branch());
					branches.peek().pos = this.pos;
					for (int i = 0; i < 4; ++i) {
						branches.peek().directions[i] = directions[i];
					}
					branches.peek().directions[prev] = false;
					--branches.peek().openDirN;
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
