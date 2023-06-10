/*
 * Represents the first mode (1v1)
 */
class Mode1 extends Mode
{
	private Mode1Player P[] = new Mode1Player[2];//the players
	private int currPIndex;//the player that is now their turn

	public Mode1() {
		for (int i = 0; i < 2; ++i) {
			P[i] = new Mode1Player(i + 1, game);
		}

		Explanation = new String(
			"How to play:\n"+
			"\tBoth players will make a labyrinth\n"+ 
			"\tfor the other to solve.\n"+
			"\tBoth players take turns\n"+ 
			"\tto move through the labyrinths\n"+ 
			"\teach one made for the other\n"+ 
			"\twith invisible walls.\n"+
			"\nHow to move:\n"+
			"\tUP: W"+
			"\tDOWN: S\n"+
			"\tLEFT: A"+
			"\tRIGHT: D\n");
	}
	public boolean Validate(Labyrinth l) {//calls the Solver class to check if a labyrinth is valid 
		Solver solver = new Solver(l);
		
		return solver.Solve();
	}
	public void Setup() {//makes necessary preparations for the game to start
		game = new GameScreen(P[0], this);
		currPlayer = P[0];
		currPIndex = 0;
		game.show();
		for (int i = 0; i < 2; ++i) {//asks players about their names and to make labyrinths
			P[i].setGameScreen(game);
			P[i].askName();
			game.show();
			game.SetPlayer(P[i]);
			P[i].Ask();
			while (!Validate(P[i].getLabyrinth())) {//while labyrinth is not valid
				Main.ShowMessage(game, "Invalid Labyrinth");
				P[i].Ask();
			}
			Main.ShowMessage(game, "Next player's turn");
			game.hide();
		}

		//sets starting positions of each player
		P[0].setPos(P[1].getLabyrinth().getStart());
		P[1].setPos(P[0].getLabyrinth().getStart());
		
		//gives each player the labyrinth they will have to solve
		Labyrinth temp = P[0].getLabyrinth();
		P[0].setLabyrinth(P[1].getLabyrinth());
		P[1].setLabyrinth(temp);
		P[0].getLabyrinth().SetPlayer(P[0]);
		P[1].getLabyrinth().SetPlayer(P[1]);
		game.Update();
	}
	
	public void PassToPlayer(char direction) {//informs a Mode2Player instance about an input from the user 
		if (currPlayer.Move(direction) != Player.HIT_WALL) {//if the player hasn't met a wall
			if (currPlayer.getPos().equals(currPlayer.getLabyrinth().getFinish())) {//if a player finished
				//winning
				Main.ShowMessage(game, "Player " + currPlayer.getName() + " won!!!");
				game.close();
			}
		}
		else {//they met a wall so its next player's turn
			Main.ShowMessage(game, "You met a wall\nNext player's turn");
			currPIndex = (currPIndex == 0) ? 1 : 0;
			currPlayer = P[currPIndex];
			game.SetPlayer(currPlayer);
		}
	}
	
	public void Play() {//starts the game
		Setup();
		game.SetPlayer(P[0]);
		game.WallVisibility(false);
		game.show();
		game.EnableMovement();
	}
};
