
import java.util.*;

class Mode2 extends Mode
{
	private ArrayList<Mode2Player> Players;
	private Mode2Player currPlayer;
	private int currPIndex;
	private ArrayList<Mode2Player> CorpseList;

	Mode2() {
		Players = new ArrayList<Mode2Player>();
		CorpseList = new ArrayList<Mode2Player>();
		Explanation =
				"How to play:\n\n"+
				"All the players take turns to move\n"+ 
				"through randomly generated labyrinths\n"+ 
				"with invisible walls.\n"+
				"For every succesful move\n"+ 
				"you gain one point,\n"+
				"if you meet another player\n"+ 
				"both of you will bet an amount of points.\n"+ 
				"It will then be the bet winner's turn\n"+
				"to move and the loser will lose 1HP\n"+ 
				"Each side will lose the points they used.\n"+
				"To win you need:\n"+
				"1.To be the only one left.\n"+
				"2.Solve the labyrinth.\n"+
				"Everyone else loses\n"+
				"How to move:\n"+
				"UP: W"+
				"\tDOWN: S\n"+
				"LEFT: A"+
				"\tRIGHT: D\n"
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
			Players.get(i).setGameScreen(game);
			Players.get(i).askName();
			game.SetPlayer(Players.get(i));
			Players.get(i).setPos(Players.get(i).getLabyrinth().GenerateLabyrinth());
		}
	}
	
	Mode2Player MetSomeone(Mode2Player current) {
		for (Mode2Player p : Players) {
			if (current.getPos().equals(p.getPos()) && current.getID() != p.getID()) {
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
				return;
			}
			if ((other = MetSomeone(currPlayer)) != null) {
				if (currPlayer.Battle(other) != null) {
					//Main.ShowMessage(game, "\nNext player's turn");
					currPIndex = Players.indexOf(other);
					currPlayer = other;
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
	}
	void ConfirmDeath(Mode2Player corpse) {
		this.CorpseList.add(corpse);
		this.Players.remove(corpse);
	}
};
