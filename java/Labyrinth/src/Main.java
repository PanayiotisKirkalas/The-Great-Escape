import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;


public class Main {

	boolean MainMenu2(boolean go_a, boolean Mode) {
		char ch;
		int index = 0, n = 0;
		while (true) {
			System.out.print("\033[H\033[2J");//clear screen;;
			System.out.println("MAIN MENU");
			switch (index)
			{
			case 0:
				System.out.print("    ->Play");
				if (go_a) {
					System.out.print(" again"); //+ 1 tab
				}
				System.out.print('\n' + "      How to play" + '\n' + "      Back");
				break;
				
			case 1:
				System.out.print("      Play");
				if (go_a) {
					System.out.print(" again"); //+ 1 tab
				}
				System.out.print('\n' + "    ->How to play" + '\n' + "      Back");
				break;

			case 2:
				System.out.print("      Play");
				if (go_a) {
					System.out.print(" again"); //+ 1 tab
				}
				System.out.print('\n' + "      How to play" + '\n' + "    ->Back");
				break;

			default:
				break;
			}

			while (true) {
				if ((ch = KeyEvent.KEY_PRESSED) == KeyEvent.VK_ENTER) {
					break;
				}
			}
			if (ch == KeyEvent.VK_ENTER) {
				switch (index)
				{
				case 0:
					go_a = false;

					if (!Mode) {
						Mode1 m1 = new Mode1();
						m1.Setup();
						m1.Play();
						go_a = true;
						break;
					}
					
					do {
						System.out.print("\033[H\033[2J");//clear screen;;
						System.out.print("Number of players (" + 2 + '-' + 4 + "): ");
						n = new Scanner(System.in).nextInt();
					} while (n > 4 || n < 2);
					{
						Mode2 m2 = new Mode2(n);
						m2.Setup();
						m2.Play();
					}
					go_a = true;
					break;

				case 1:
					System.out.print("\033[H\033[2J");//clear screen;;
					if (!Mode) {
						System.out.print(Mode1.Explanation);
					}
					else{
						System.out.print(Mode2.Explanation);
					}
					new Scanner(System.in).nextLine();//pause
					System.out.print("\033[H\033[2J");//clear screen;;
					break;

				case 2:
					return true;

				default:
					break;
				}
			}
			else if (ch == 'w'/* && index > 0*/) {
				if (index == 0)
					index = 2;
				else
					index--;
			}
			else if (ch == 's'/* && index < 2*/) {
				if (index == 2)
					index = 0;
				else
					index++;
			}
		}
	}

	boolean MainMenu(boolean go_a) {
		char ch;
		int index = 0;
		while (true) {
			System.out.print("\033[H\033[2J");//clear screen;;
			System.out.println("MAIN MENU");
			switch (index) {
			case 0:
				System.out.println("    ->1v1"); //+ 1 tab
				System.out.println("      Everyone for themselves (up to 4 players)" + '\n' + "      Quit");
				break;
			case 1:
				System.out.println("      1v1"); //+ 1 tab
				System.out.println("    ->Everyone for themselves (up to 4 players)" + '\n' + "      Quit");
				break;
			case 2:
				System.out.println("      1v1"); //+ 1 tab
				System.out.println("      Everyone for themselves (up to 4 players)" + '\n' + "    ->Quit");
				break;
			}

			while (true) {
				if ((ch = KeyEvent.KEY_PRESSED) == KeyEvent.VK_ENTER) {
					break;
				}
			}
			if (ch == KeyEvent.VK_ENTER) {
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
			else if (ch == 'w'/* && index > 0*/) {
				if (index == 0)
					index = 2;
				else
					index--;
			}
			else if (ch == 's'/* && index < 2*/) {
				if (index == 2)
					index = 0;
				else
					index++;
			}
		}
	}
	
	public static void main(String[] args) {
		

	}

}
