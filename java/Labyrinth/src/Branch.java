/*
 * It represents a point with multiple directions around it
 * It is Used by the Solver class in stack
 */
public class Branch {
	public coordinate pos = new coordinate();
	public boolean directions[] = {true, true, true, true};
	public int openDirN = 4;
};
