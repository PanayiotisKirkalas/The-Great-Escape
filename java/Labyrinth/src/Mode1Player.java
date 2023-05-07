import java.awt.Event;
import java.awt.event.KeyEvent;
public class Mode1Player extends Player {

	public Mode1Player(int id)
	{
		super(id);
	}
	
	public void Ask()
	{
		String input;
		
		while (true)
		{
			this.Own = new Labyrinth();
			this.Own.setName(this.Name);
			
			System.out.print("\033[2J\033[1;1H");
			System.out.println("Player: " + this.Name);
			System.out.print("Do you want to build your own labyrinth? (yes/no)");
			input = keyboard.nextLine();
			
			if (input.charAt(0) == "n".charAt(0))
			{
				this.pos = this.Own.GenerateLabyrinth();
				this.Own.printLabyrinth(SHOW_WALLS);
			}
			else if (input.charAt(0) == "y".charAt(0))
			{
				BuildLabyrinth();
			}
			
			System.out.print("\nIs that good enough? (yes/no)");
			input = keyboard.nextLine();
			
			if (input.charAt(0) == "y".charAt(0)) break;
		}
	}
	
	public void BuildLabyrinth()
	{
		int dir;
		char[] RowColumn = {' ', ' '};
		int c;
		coordinate start, finish;
		
		do
		{
			this.Own.printLabyrinth(SHOW_WALLS);
			System.out.println("\nBuild your labyrinth:");
			while ((RowColumn[0] < 97 || RowColumn[0] > 102)
					|| (RowColumn[1] < 49 || RowColumn[1] > 54))
			{
				System.out.println("Starting point: ");
				RowColumn = keyboard.next().toCharArray();
			}
			start = coordinate.make_pair(RowColumn[0] - 97, RowColumn[1] - 49);
			
			RowColumn[0] = RowColumn[1] = ' ';
			while ((RowColumn[0] < 97 || RowColumn[0] > 102)
					|| (RowColumn[1] < 49 || RowColumn[1] > 54))
			{
				System.out.println("Finishing point: ");
				RowColumn = keyboard.next().toCharArray();
			}
			finish = coordinate.make_pair(RowColumn[0] - 97, RowColumn[1] - 49);
			
			this.Own.setStart(start);
			this.Own.setFinish(finish);
			
			RowColumn[0] = RowColumn[1] = ' ';
			for (int i = 1; i <= 20 && (RowColumn[0] != 45 && RowColumn[1] != 49); i++)
			{
				this.Own.printLabyrinth(SHOW_WALLS);
				System.out.println("\nBuild your labyrinth (Enter -1 to stop building):");
				while ((RowColumn[0] < 97 || RowColumn[0] > 102)
						|| (RowColumn[1] < 49 || RowColumn[1] > 54))
				{
					System.out.println("Choose block to add wall around (" + i + "/20): ");
					RowColumn = keyboard.next().toCharArray();
					if (RowColumn[0] == 45 && RowColumn[1] == 49) return;
				}
				
				System.out.println("Choose direction to add wall(w/a/s/d): ");
				dir = (int)keyboard.next().charAt(0);
			
				start = coordinate.make_pair(RowColumn[0] - 97, RowColumn[1] - 49);
				switch (dir) 
				{
					case Player.KEY_UP:
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
		} while (true);
	}
	
	public boolean Move(Labyrinth Other)
	{
		char c;
		
		Other.alter(this.pos, this.symbol);
		Other.printLabyrinth(DONT_SHOW_WALLS);
		
		c = KeyEvent.KEY_PRESSED;
		
		while(!Other.isClosed(this.pos, c))
		{
			switch (c)
			{
				case KEY_UP:
					if (this.pos.y_axis > 0) {
						Other.alter(this.pos, PLAYER_UP);
						this.pos.y_axis -= 1;
					}
					break;
				case KEY_DOWN:
					if (this.pos.y_axis < 5) {
						Other.alter(this.pos, PLAYER_DOWN);
						this.pos.y_axis += 1;
					}
					break;
				case KEY_LEFT:
					if (this.pos.x_axis > 0) {
						Other.alter(this.pos, PLAYER_LEFT);
						this.pos.x_axis -= 1;
					}
					break;
				case KEY_RIGHT:
					if (this.pos.x_axis < 5) {
						Other.alter(this.pos, PLAYER_RIGHT);
						this.pos.x_axis += 1;
					}
					break;
			}
			
			Other.alter(this.pos, this.symbol);
			Other.printLabyrinth(DONT_SHOW_WALLS);
			
			if (this.pos == Other.getFinish()) return WON;
			
			c = KeyEvent.KEY_PRESSED;
		}
		
		return HIT_WALL;
	}

}
