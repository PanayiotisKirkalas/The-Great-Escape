import java.util.*;
import java.lang.*;

class Mode1
{
	private Mode1Player P[];

	public static String Explanation;

	public Mode1() {
		for (int i = 0; i < 2; ++i) {
			P[i] = new Mode1Player(i + 1);
		}

		Explanation = new String(
			"How to play:\n\n"+
			"\tBoth players will make a labyrinth for the other to solve. There is the option to let the program randomly\n"+
			"\tgenerate a labyrinth\n"+
			"\tBoth players take turns to move through the labyrinths each one made for the other with invisible walls.\n"+
			"\nHow to move:\n"+
			"\tUP: W\n"+
			"\tDOWN: S\n"+
			"\tLEFT: A\n"+
			"\tRIGHT: D\n"+
			"\nHow to Build:\n"+
			"\tYou enter the coordinates of the block you want to add a wall around (e.g. E5)\n"+
			"\tand then the direction that you want to build towards, the direction is inputed the same way you move"+
			"\tYou can enter up to 20 walls\n"+
			"\nPress Enter to go back");
	}
	public boolean Validate(Labyrinth l) {
		Solver solver = new Solver(l);
		
		return solver.Solve();
	}
	public void Setup() {
		String name;
		char c2;
		
		for (int i = 0; i < 2; ++i) {
			System.out.print("\033[H\033[2J");//clear screen
			P[i].askName();
			P[i].Ask();
			System.out.println("Validating Labyrinth...");
			while (!Validate(P[i].getLabyrinth())) {
				System.out.println("Invalid Labyrinth...");
				new Scanner(System.in).nextLine();//pause
				P[i].Ask();
				System.out.println("Validating Labyrinth...");
			}
			System.out.print("\033[H\033[2J");//clear screen 
			System.out.println("The labyrinth is valid");
			System.out.println("Give to other player");
			System.out.println("Press enter to continue...");
			new Scanner(System.in).nextLine();//pause
		}

		P[0].setPos(P[1].getLabyrinth().getStart());
		P[1].setPos(P[0].getLabyrinth().getStart());
		System.out.print("\033[H\033[2J");//clear screen
	}
	public void Play() {
		char c;
		boolean i;
		for (i = false; P[Main.BoolToInt(i)].Move(P[Main.BoolToInt(!i)].getLabyrinth()) != Player.WON; i = !i) {
			System.out.print("\033[H\033[2J");//clear screen
			System.out.println("You hit a wall. Give next player");
			System.out.println("Press enter to continue...");
			new Scanner(System.in).nextLine();//pause
			System.out.print("\033[H\033[2J");//clear screen
		}

		System.out.print("\033[H\033[2J");//clear screen
		System.out.print("Player: " + P[Main.BoolToInt(i)].getName());
		for (int j = 0; j < (6 - P[Main.BoolToInt(i)].getName().length()); ++j) {
			System.out.print(' ');
		}
		System.out.print("   ");
		System.out.println("Player: " + P[Main.BoolToInt(!i)].getName());
		for (int j = 0; j < 14; ++j) {
			P[Main.BoolToInt(!i)].getLabyrinth().printRow(j);
			System.out.print("   ");
			P[Main.BoolToInt(i)].getLabyrinth().printRow(j);
			System.out.println();
		}
		System.out.print(System.getProperty("line.separator") + P[Main.BoolToInt(i)].getName()+" won!");
		new Scanner(System.in).nextLine();//pause
	}
};
