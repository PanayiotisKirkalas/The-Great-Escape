
import java.util.*;

class Mode2 extends Mode
{
	private ArrayList<Mode2Player> Players;
	private Mode2Player currPlayer;
	private int currPIndex;
	private ArrayList<Integer> CorpseList;

	Mode2() {
		Players = new ArrayList<Mode2Player>();
		CorpseList = new ArrayList<>();
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
	private void SetPlayers(int n_Players) {
		Players.clear();
		Players = new ArrayList<Mode2Player>(n_Players);
		CorpseList = new ArrayList<>();
		for (int i = 0; i < n_Players; ++i) {
			Players.add(new Mode2Player(i, this));
		}
		game = new GameScreen(Players.get(0), this);
	}
	void Setup() {
		int n;
		
		do {
			n = Integer.parseInt(Main.AskUser(null, "Number of players(2 to 4)? "));
		} while (n < 2 || n > 4);
		
		this.SetPlayers(n);
		currPlayer = Players.get(0);
		currPIndex = 0;
		for (int i = 0; i < Players.size(); ++i) {
//			System.out.print("\033[H\033[2J");//clear screen;
			Players.get(i).setGameScreen(game);
			Players.get(i).askName();
			game.SetPlayer(Players.get(i));
			Players.get(i).setPos(Players.get(i).getLabyrinth().GenerateLabyrinth());
//			System.out.print("\033[H\033[2J");//clear screen;
//			System.out.println("Next player");
//			System.out.println("Press Enter to continue...");
//			new Scanner(System.in).nextLine();//pause
		}
//		System.out.print("\033[H\033[2J");//clear screen;
	}
	
	Mode2Player MetSomeone(Mode2Player current) {
		for (Mode2Player p : Players) {
			if (current.getPos().equals(p.getPos()) && current.getID() != p.getID()) {
//				System.out.print("\033[H\033[2J");//clear screen
//				System.out.println("You've met Player: " + p.getName());
//				new Scanner(System.in).nextLine();//pause
				if (!p.dead) return p;
			}
		}
		return null;
	}
	
	public void PassToPlayer(char direction) {
		coordinate temp = new coordinate(currPlayer.getPos());
		Mode2Player other;
		if (currPlayer.Move(direction) != Player.HIT_WALL) {
			if (!temp.equals(currPlayer.getPos()))
				currPlayer.IncPoints(1);
			
			if (currPlayer.getPos().equals(currPlayer.getLabyrinth().getFinish()) || Players.size() == 1) {
				//winning
				Main.ShowMessage(game, "Player " + currPlayer.getName() + " won!!!");
				game.close();
			}
			if ((other = MetSomeone(currPlayer)) != null) {
				if (currPlayer.Battle(other) != null) {
					//Main.ShowMessage(game, "\nNext player's turn");
					do {
						currPIndex = (currPIndex + 1) % Players.size();
						currPlayer = Players.get(currPIndex);
					} while (currPlayer.dead);
					game.SetPlayer(currPlayer);
				}
			}
		}
		else {
			Main.ShowMessage(game, "You met a wall\nNext player's turn");
			do {
				currPIndex = (currPIndex + 1) % Players.size();
				currPlayer = Players.get(currPIndex);
			} while (currPlayer.dead);
			game.SetPlayer(currPlayer);
		}
	}
	
	public void Play() {
		Setup();
		game.SetPlayer(Players.get(0));
		game.WallVisibility(false);
		game.show();
		game.EnableMovement();
//		//char c;
//		//boolean GameOver = false;
//		int i, result;
//
//		Setup();
//		for (i = 0; (result = Players.get(i).Move(Players)) != -1; i = (i+1) % Players.size()) {
//			//System.out.println("[DEBUG] " + i);
//			if (result != -2) {
//				i = result - 1;
//			}
//			else {
//				System.out.print("\033[H\033[2J");//clear screen;
//				System.out.println("You hit a wall. Give next player");
//				System.out.println("Press enter to continue...");
//				new Scanner(System.in).nextLine();//pause
//			}
//
//			GatherCorpses();
//			System.out.print("\033[H\033[2J");//clear screen;
//		}
//
//		System.out.println('\n' + Players.get(i).getName() + " won!");
//		new Scanner(System.in).nextLine();//pause
//		return;
	}
	void ConfirmDeath(int index) {
		this.CorpseList.add(index);
		this.Players.remove(index);
	}
	void GatherCorpses() {
		for (int i : this.CorpseList)
			this.CorpseList.remove(i);
		this.CorpseList.clear();
	}
};
