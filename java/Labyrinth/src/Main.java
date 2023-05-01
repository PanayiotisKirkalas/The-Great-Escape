import java.io.*;
import java.util.*;

public class Main
{
	public static Scanner keyboard = new Scanner(System.in).useDelimiter("\\n");
	
	public static int MIN_PLAYERS = 2;
	public static int MAX_PLAYERS = 4;
	
	static boolean MainMenu2(boolean go_a, boolean Mode)
	{
		int ch, temp;
		int index = 0;
		int n = 0;
		
		while (true)
		{
			System.out.print("\033[2J\033[1;1H");
			System.out.println("MAIN MENU");
			switch (index)
			{
				case 0:
					System.out.println("    ->Play");
					if (go_a) System.out.println(" again");
					System.out.println("      How to play");
					System.out.println("      Back");
					break;
					
				case 1:
					System.out.println("      Play");
					if (go_a) System.out.println(" again");
					System.out.println("    ->How to play");
					System.out.println("      Back");
					break;
					
				case 2:
					System.out.println("      Play");
					if (go_a) System.out.println(" again");
					System.out.println("    ->How to play");
					System.out.println("      Back");
					break;
					
				default:
					break;
			}
			
			ch = (int)keyboard.next().charAt(0);
			if (ch == 13)
			{
				switch (index)
				{
					case 0:
						go_a = false;
						
						if (!Mode)
						{
							Mode1 m1 = new Mode1();
							m1.Setup();
							m1.Play();
							go_a = true;
							break;
						}
						
						do
						{
							System.out.print("\033[2J\033[1;1H");
							System.out.println("Number of players (" + MIN_PLAYERS + "-" + MAX_PLAYERS + "): " + n);
							n = keyboard.nextInt();
						} while (n > MAX_PLAYERS || n < MIN_PLAYERS);
						{
							Mode2 m2 = new Mode2();
							m2.Setup();
							m2.Play();
						}
						go_a = true;
						break;
						
					case 1:
						System.out.print("\033[2J\033[1;1H");
						if (!Mode)
						{
							Mode1 m1 = new Mode1();
							System.out.println(m1.Explanation);
						}
						else
						{
							Mode1 m2 = new Mode2();
							System.out.println(m2.Explanation);
						}
						while((temp = (int)keyboard.next().charAt(0)) != 13)
							temp = (int)keyboard.next().charAt(0);
						System.out.print("\033[2J\033[1;1H");
						break;
						
					case 2:
						return true;
						
					default:
						break;
				}
			}
			else if (ch == 119) // ASCII w
			{
				if (index == 0)
					index = 2;
				else
					index--;
			}
			else if (ch == 115) // ASCII s
			{
				if (index == 2)
					index = 0;
				else
					index++;
			}
		}
	}
	
	static boolean MainMenu(boolean go_a)
	{
		int ch;
		int index = 0;
		while (true)
		{
			System.out.print("\033[2J\033[1;1H"); // equivelant to system("cls");
			System.out.println("MAIN MENU");
			switch (index)
			{
				case 0:
					System.out.println("    ->1v1");
					System.out.println("      Everyone for themselves (up to 4 players)");
					System.out.println("	  Quit");
					break;
				case 1:
					System.out.println("      1v1");
					System.out.println("    ->Everyone for themselves (up to 4 players)");
					System.out.println("	  Quit");
					break;
				case 2:
					System.out.println("      1v1");
					System.out.println("      Everyone for themselves (up to 4 players)");
					System.out.println("	->Quit");
					break;
			}

			ch = (int)keyboard.next().charAt(0);
			if (ch == 13)
			{
				switch (index)
				{
					case 0:
						go_a = false;
						MainMenu2(go_a, false);
						break;
					case 1:
						go_a = false;
						MainMenu2(go_a, true);
						break;
					case 2:
						return true;
					default:
						break;
				}
			}
			else if (ch == 119) // ASCII w
			{
				if (index == 0)
					index = 2;
				else
					index--;
			}
			else if (ch == 115) // ASCII s
			{
				if (index == 2)
					index = 0;
				else
					index++;
			}
		}
	}
	
	public static void main(String[] args)
	{
		boolean ggo = false;
		MainMenu(ggo);
	}
}