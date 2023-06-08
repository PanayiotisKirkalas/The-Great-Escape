import java.awt.Event;
import java.awt.event.KeyEvent;
public class Mode1Player extends Player {

	public Mode1Player(int id, GameScreen game)
	{
		super(id, game);
	}
	
	public void Ask()
	{
		String input;
		
		while (true)
		{
			this.Own = new Labyrinth(this.game);
			this.Own.SetPlayer(this);
			this.game.Update();
			this.Own.printLabyrinth(true);
			
//			System.out.print("\033[2J\033[1;1H");
//			System.out.println("Player: " + this.Name);
//			System.out.print("Do you want to build your own labyrinth? (yes/no)");
//			input = keyboard.nextLine();
			input = Main.AskUser(null, "Player: " + this.Name + "\nDo you want to build your own labyrinth? (yes/no)");
			
			if (input.charAt(0) == 'n')
			{
				this.pos = this.Own.GenerateLabyrinth();
//				this.Own.printLabyrinth(SHOW_WALLS);
			}
			else if (input.charAt(0) == 'y')
			{
				BuildLabyrinth();
			}
			
//			System.out.print("\nIs that good enough? (yes/no)");
			input = Main.AskUser(game, "Is that good enough? (yes/no)");
			
			if (input.charAt(0) == 'y') break;
		}
	}
	
	public void BuildLabyrinth()
	{
		int dir;
		char[] RowColumn = {' ', ' '};
//		int c;
		coordinate start, finish;
		
		Main.ShowMessage(game, "Build your labyrinth");
		Own.printLabyrinth(true);
		game.Update();
		game.show();
		while ((RowColumn[0] < 97 || RowColumn[0] > 102)
				|| (RowColumn[1] < 49 || RowColumn[1] > 54))
		{
//			System.out.println("Starting point: ");
			RowColumn = Main.AskUser(game, "Starting point: ").toCharArray();
		}
		start = coordinate.make_pair(RowColumn[0] - 97, RowColumn[1] - 49);
		
		RowColumn[0] = RowColumn[1] = ' ';
		while ((RowColumn[0] < 97 || RowColumn[0] > 102)
				|| (RowColumn[1] < 49 || RowColumn[1] > 54))
		{
//			System.out.println("Finishing point: ");
			RowColumn = Main.AskUser(game, "Finishing point: ").toCharArray();
		}
		finish = coordinate.make_pair(RowColumn[0] - 97, RowColumn[1] - 49);
		
		this.Own.setStart(start);
		this.Own.setFinish(finish);
//			this.Own.printLabyrinth(SHOW_WALLS);
//			System.out.println("\nBuild your labyrinth:");
		
		
		RowColumn[0] = RowColumn[1] = ' ';
		Main.ShowMessage(game, "Add walls (Enter -1 to stop building):");
		for (int i = 1; i <= 20 && (RowColumn[0] != 45 && RowColumn[1] != 49); i++)
		{
//				this.Own.printLabyrinth(SHOW_WALLS);
//				System.out.println("\nAdd walls (Enter -1 to stop building):");
			while ((RowColumn[0] < 97 || RowColumn[0] > 102)
					|| (RowColumn[1] < 49 || RowColumn[1] > 54))
			{
				//System.out.println("Choose block to add wall around (" + i + "/20): ");
				RowColumn = Main.AskUser(game, "Choose block to add wall around (" + i + "/20): ").toCharArray();
				if (RowColumn[0] == 45 && RowColumn[1] == 49) {Own.printLabyrinth(true); game.Update(); return;}
			}
			
//				System.out.println("Choose direction to add wall(w/a/s/d): ");
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
		Own.printLabyrinth(true);
		game.Update();
	}
}
