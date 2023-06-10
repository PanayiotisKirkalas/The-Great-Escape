/*
 * It takes a labyrinth and solves it to figure out if the labyrinth is valid (can be solved).
 * Gets called by Mode1 class
 */

import java.util.*;

class Solver {
	private Stack<Branch> branches;//A stack of branching paths
	private coordinate pos; 
	private Labyrinth l;//the labyrinth to be solved
	private boolean checkedRooms[][];//a 6*6 matrix of flags for each position on the labyrinth for the solver to know if it has already been there

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
	
	private int Move(int dir, boolean on_branch) {//the solver moves into a neighboring spot in the labyrinth
		if (on_branch) {
			//if, before moving, the solver is on a branching path it marks the direction it will move as passed and decreases the number of open paths 
			branches.peek().directions[dir] = false;
			--branches.peek().openDirN;
		}

		checkedRooms[pos.y_axis][pos.x_axis] = true;//marks that the solver has already been here

		switch (dir) {//move to given direction
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

		return (dir + 2) % 4;//Returns the opposite direction to be stored as the one it came from 
	}
	private coordinate calculatePos(coordinate pos, int dir) {
		//calculates and returns the position the solver would be in if he moved to a given direction
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

		if (l.getNofWalls() > 20) {//if labyrinth has more than 20 custom walls is already invalid 
			return false;
		}
		
		while (true) {
			openDirN = 4;//at first consider all directions as open

			if (this.pos.equals(l.getFinish()))//if it reaches the finish the labyrinth is valid
				return true;
			
			for (int i = 0; i < 4; ++i) {//checks to which directions it can move at
				close_path = false;
				temp = calculatePos(pos, i);
				if (i == 0 && pos.y_axis <= 0) {//there is no room to move up
					close_path = true;
				}
				else if (i == 1 && pos.x_axis >= 5){//there is no room to move right
					close_path = true;
				}
				else if (i == 2 && pos.y_axis >= 5){//there is no room to move down
					close_path = true;
				}
				else if (i == 3 && pos.x_axis <= 0){//there is no room to move left
					close_path = true;
				}
				else if (i == prev) {//it came from there
					close_path = true;
				}
				else if (!directions[i]){//this direction has been closed from before
					close_path = true;
				}
				else if (l.isClosed(this.pos, dir[i])) {//there is a wall there
					close_path = true;
				}
				else if (checkedRooms[temp.y_axis][temp.x_axis]) {//it has already been there
					close_path = true;
				}
				
				if (close_path) {//close direction
					directions[i] = false;
					--openDirN;
					continue;
				}
				openDir = i;//it will go to that direction
			}
			
			
			if (openDirN == 0) {//if no open paths
				if (branches.isEmpty())//if there are no stored branching paths to go back to the labyrinth is not valid
					return false;
				if (pos.equals(branches.peek().pos)) {//if it is on a branching path removes it from the stack since there are no path left 
					branches.pop();
				}
				if (branches.isEmpty())//if there are no more branching paths the labyrinth is invalid
					return false;

				pos.copy(branches.peek().pos);//Goes to the last branching path it visited
				openDirN = branches.peek().openDirN;
				for (int i = 0; i < 4; ++i) {
					directions[i] = branches.peek().directions[i];
				}
				deja_vu = true;//stores that the branching path is not newly found
				prev = -1;
			}
			else if (openDirN == 1) {//if there is only one path open
				prev = Move(openDir, (branches.empty() ? false : pos.equals(branches.peek().pos)));//moves there

				for (int i = 0; i < 4; ++i) {//sets all directions to be checked
					directions[i] = true;
				}
				directions[prev] = false;//except the one it came from
				
				deja_vu = false;
			}
			else if (openDirN > 1) {//if there are more than 1 branching paths
				if (!deja_vu) {//if its newly found
					branches.push(new Branch());//push it to the stack
					branches.peek().pos.copy(this.pos);
					for (int i = 0; i < 4; ++i) {
						branches.peek().directions[i] = directions[i];
					}
					
					if (prev > -1) {//if this isn't the start of the labyrinth
						//marks the direction it came from as passed and decreases the number of open paths
						branches.peek().directions[prev] = false;
						--branches.peek().openDirN;
					}
				}
				else 
					deja_vu = false;
				
				prev = Move(openDir, true);//Move
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
