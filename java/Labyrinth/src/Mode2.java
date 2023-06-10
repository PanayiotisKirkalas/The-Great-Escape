/*
 * Represents the second mode (Battle Royal)
 */
import java.util.*;

class Mode2 extends Mode
{
	private ArrayList<Mode2Player> Players;//The list of players still playing
	private Mode2Player currPlayer;//the player that is now their turn
	private int currPIndex;
	private ArrayList<Mode2Player> CorpseList;//The players that are "dead", they have lost the game

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
	private void SetPlayers(int n_Players) {//initializes the players
		Players.clear();
		Players = new ArrayList<Mode2Player>(n_Players);
		CorpseList = new ArrayList<>();
		for (int i = 0; i < n_Players; ++i) {
			Players.add(new Mode2Player(i, this));
		}
		game = new GameScreen(Players.get(0), this);
	}
	
	private void Setup() {//makes necessary preparations for the game to start
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
			Players.get(i).setPos(Players.get(i).getLabyrinth().GenerateLabyrinth());//randomly generates a labyrinth for each player
		}
	}
	
	private Mode2Player MetSomeone(Mode2Player current) {//checks if two players have "met each other", are in the same position
		for (Mode2Player p : Players) {
			if (current.getPos().equals(p.getPos()) && current.getID() != p.getID()) {
				if (!p.dead) return p;
			}
		}
		return null;
	}
	
	public void PassToPlayer(char direction) {//informs a Mode2Player instance about an input from the user 
		coordinate temp = new coordinate(currPlayer.getPos());
		Mode2Player other;
		if (currPlayer.Move(direction) != Player.HIT_WALL) {//if the player hasn't met a wall
			if (!temp.equals(currPlayer.getPos()))//if they have moved gives them a point
				currPlayer.IncPoints(1);
			
			if (currPlayer.getPos().equals(currPlayer.getLabyrinth().getFinish()) || Players.size() == 1) {//if a player finished
				//winning
				Main.ShowMessage(game, "Player " + currPlayer.getName() + " won!!!");
				game.close();
				return;
			}
			if ((other = MetSomeone(currPlayer)) != null) {//if they met another player
				if (currPlayer.Battle(other) != null) {//have them bet, if the other player won, its their turn
					currPIndex = Players.indexOf(other);
					currPlayer = other;
					game.SetPlayer(currPlayer);
				}
			}
		}
		else {//they met a wall so its next player's turn
			Main.ShowMessage(game, "You met a wall\nNext player's turn");
			do {
				currPIndex = (currPIndex + 1) % Players.size();
				currPlayer = Players.get(currPIndex);
			} while (currPlayer.dead);
			game.SetPlayer(currPlayer);
		}
	}
	
	public void Play() {//starts the game
		Setup();
		game.SetPlayer(Players.get(0));
		game.WallVisibility(false);
		game.show();
		game.EnableMovement();
	}
	public void ConfirmDeath(Mode2Player corpse) {//a player calls it if they have lost all lives
		this.CorpseList.add(corpse);
		this.Players.remove(corpse);
	}
};
