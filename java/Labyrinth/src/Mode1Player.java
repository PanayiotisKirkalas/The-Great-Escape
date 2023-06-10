public class Mode1Player extends Player {

	public Mode1Player(int id, GameScreen game)
	{
		super(id, game);
	}
	
	public void Ask()//asks the user if they want to build their labyrinth or receive a made one 
	{
		String input;
		
		while (true)
		{
			this.Own = new Labyrinth(this.game);
			this.Own.SetPlayer(this);
			this.game.Update();
			
			input = Main.AskUser(null, "Player: " + this.Name + "\nDo you want to build your own labyrinth? (yes/no)");
			
			if (input.charAt(0) == 'n')
			{
				this.pos = this.Own.GenerateLabyrinth();
			}
			else if (input.charAt(0) == 'y')
			{
				BuildLabyrinth();
			}
			
			input = Main.AskUser(game, "Is that good enough? (yes/no)");
			
			if (input.charAt(0) == 'y') break;
		}
	}
	
	public void BuildLabyrinth()//allows the user to build their labyrinth
	{
		int dir;
		char[] RowColumn = {' ', ' '};
//		int c;
		coordinate start, finish;
		
		Main.ShowMessage(game, "Build your labyrinth");
		game.Update();
		game.show();
		//Asks for starting and finishing points
		while ((RowColumn[0] < 97 || RowColumn[0] > 102)
				|| (RowColumn[1] < 49 || RowColumn[1] > 54))
		{
			RowColumn = Main.AskUser(game, "Starting point: ").toCharArray();
		}
		start = coordinate.make_pair(RowColumn[0] - 97, RowColumn[1] - 49);
		
		RowColumn[0] = RowColumn[1] = ' ';
		while ((RowColumn[0] < 97 || RowColumn[0] > 102)
				|| (RowColumn[1] < 49 || RowColumn[1] > 54))
		{
			RowColumn = Main.AskUser(game, "Finishing point: ").toCharArray();
		}
		finish = coordinate.make_pair(RowColumn[0] - 97, RowColumn[1] - 49);
		
		this.Own.setStart(start);
		this.Own.setFinish(finish);
		
		
		RowColumn[0] = RowColumn[1] = ' ';
		//lets the user add up to 20 walls by asking the position of the block the wall will be added and the direction
		Main.ShowMessage(game, "Add walls (Enter -1 to stop building):");
		for (int i = 1; i <= 20 && (RowColumn[0] != 45 && RowColumn[1] != 49); i++)
		{
			while ((RowColumn[0] < 97 || RowColumn[0] > 102)
					|| (RowColumn[1] < 49 || RowColumn[1] > 54))
			{
				RowColumn = Main.AskUser(game, "Choose block to add wall around (" + i + "/20): ").toCharArray();
				if (RowColumn[0] == 45 && RowColumn[1] == 49) {game.Update(); return;}
			}
			
			dir = Main.AskUser(game, "Choose direction to add wall(w/a/s/d): ").charAt(0);
		
			start = coordinate.make_pair(RowColumn[0] - 97, RowColumn[1] - 49);
			switch (dir) 
			{
				case KEY_UP:
					this.Own.BuildWall(start, 1);
					break;
				case KEY_LEFT:
					this.Own.BuildWall(start, 4);
					break;
				case KEY_DOWN:
					this.Own.BuildWall(start, 3);
					break;
				case KEY_RIGHT:
					this.Own.BuildWall(start, 2);
					break;
				default:
					--i;
					break;
			}
			
			RowColumn[0] = RowColumn[1] = ' ';
		}
		game.Update();
	}
}
