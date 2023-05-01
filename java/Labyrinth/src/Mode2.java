
import java.util.*;

class Mode2
{
	private ArrayList<Mode2Player> Players;
	private ArrayList<Integer> CorpseList;

	public static String Explanation;

	Mode2() {
		Explanation =
				"How to play:\n\n"+
				"All the players take turns to move through randomly generated labyrinths with invisible walls.\n"+
				"A player loses their turn when they hit a wall. Every time you move and don't hit a wall, you gain one point,\n"+
				"if you meet another player, both of you will have to bet an amount of points. It will then be the bet's winner turn\n"+
				"to move and the loser will lose 1HP (each player starts with 2HP), if there is draw, it will also be the other\n"+
				"player's turn. Each side will lose the points they used.\n"+
				"A player wins if they are the only one left or if they reach the finish of the labyrinth (F).\n"+
				"A player loses if someone reaches the finish before them or if they lose all their lives (everyone starts with 2)\n"+
				"How to move:\n"+
				"\tUP: W\n"+
				"\tDOWN: S\n"+
				"\tLEFT: A\n"+
				"\tRIGHT: D\n"+
				"\nPress Enter to go back"
				;
	}
	Mode2(int n_Players) {
		Players.clear();
		for (int i = 0; i < n_Players; ++i) {
			Players.add(new Mode2Player(i, this));
		}
	}
	void Setup() {

		for (int i = 0; i < Players.size(); ++i) {
			System.out.print("\033[H\033[2J");//clear screen;
			Players.get(i).askName();
			Players.get(i).setPos(Players.get(i).getLabyrinth().GenerateLabyrinth());
			System.out.print("\033[H\033[2J");//clear screen;
			System.out.println("Next player");
			System.out.println("Press Enter to continue...");
			new Scanner(System.in).nextLine();//pause
		}
		System.out.print("\033[H\033[2J");//clear screen;
	}
	void Play() {
		//char c;
		//boolean GameOver = false;
		int i, result;

		Setup();
		for (i = 0; (result = Players.get(i).Move(Players)) != -1; i = (i+1) % Players.size()) {
			//System.out.println("[DEBUG] " + i);
			if (result != -2) {
				i = result - 1;
			}
			else {
				System.out.print("\033[H\033[2J");//clear screen;
				System.out.println("You hit a wall. Give next player");
				System.out.println("Press enter to continue...");
				new Scanner(System.in).nextLine();//pause
			}

			GatherCorpses();
			System.out.print("\033[H\033[2J");//clear screen;
		}

		System.out.println('\n' + Players.get(i).getName() + " won!");
		new Scanner(System.in).nextLine();//pause
		return;
	}
	void ConfirmDeath(int index) {
		this.CorpseList.add(index);
	}
	void GatherCorpses() {
		for (int i : this.CorpseList)
			this.CorpseList.remove(i);
		this.CorpseList.clear();
	}
};
