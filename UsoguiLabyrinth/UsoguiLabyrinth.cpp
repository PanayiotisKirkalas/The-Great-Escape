// UsoguiLabyrinth.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>

#include "Labyrinth.h"
#include "Player.h"
#include "Mode1.h"
#include "Mode2.h"

using std::cout;
using std::cin;
using std::endl;

#define MIN_PLAYERS 2
#define MAX_PLAYERS 4

#define elif else if
#define HIT_WALL true
#define WON false
#define PAUSE if (int c = _getch() != char(13)) {c = _getch();}

bool MainMenu2(bool& go_a, bool Mode) {
	char ch;
	int index = 0, n = 0;
	while (1) {
		system("cls");
		cout << "MAIN MENU" << endl;
		switch (index)
		{
		case 0:
			cout << "    ->Play";
			if (go_a) {
				cout << " again"; //+ 1 tab
			}
			cout << endl << "      How to play" << endl << "      Back";
			break;
			
		case 1:
			cout << "      Play";
			if (go_a) {
				cout << " again"; //+ 1 tab
			}
			cout << endl << "    ->How to play" << endl << "      Back";
			break;

		case 2:
			cout << "      Play";
			if (go_a) {
				cout << " again"; //+ 1 tab
			}
			cout << endl << "      How to play" << endl << "    ->Back";
			break;

		default:
			break;
		}

		while (1) {
			if (_kbhit()) {
				ch = _getch();
				break;
			}
		}
		if (ch == char(13)) {
			switch (index)
			{
			case 0:
				go_a = false;

				if (!Mode) {
					Mode1 m1;
					m1.Setup();
					m1.Play();
					go_a = true;
					break;
				}
				
				do {
					system("cls");
					cout << "Number of players (" << MIN_PLAYERS << '-' << MAX_PLAYERS << "): ";
					cin >> n;
				} while (n > MAX_PLAYERS || n < MIN_PLAYERS);
				{
					Mode2 m2(n);
					m2.Setup();
					m2.Play();
				}
				go_a = true;
				break;

			case 1:
				system("cls");
				if (!Mode) {
					Mode1 m1;
					cout << m1.Explanation;
				}
				else{
					Mode2 m2;
					cout << m2.Explanation;
				}
				PAUSE
				system("cls");
				break;

			case 2:
				return true;
				break;

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

bool MainMenu(bool& go_a) {
	char ch;
	int index = 0;
	while (1) {
		system("cls");
		cout << "MAIN MENU" << endl;
		switch (index) {
		case 0:
			cout << "    ->1v1" << endl; //+ 1 tab
			cout << "      Everyone for themselves (up to 4 players)" << endl << "      Quit";
			break;
		case 1:
			cout << "      1v1" << endl; //+ 1 tab
			cout << "    ->Everyone for themselves (up to 4 players)" << endl << "      Quit";
			break;
		case 2:
			cout << "      1v1" << endl; //+ 1 tab
			cout << "      Everyone for themselves (up to 4 players)" << endl << "    ->Quit";
			break;
		}

		while (1) {
			if (_kbhit()) {
				ch = _getch();
				break;
			}
		}
		if (ch == char(13)) {
			switch (index)
			{
			case 0:
				go_a = false;
				MainMenu2(go_a, 0);
				break;

			case 1:
				go_a = false;
				MainMenu2(go_a, 1);
				break;

			case 2:
				return true;
				break;

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



int main()
{
	bool ggo = false;
	MainMenu(ggo);

    return 0;
}

// Run program: Ctrl + F5 or Debug > Start Without Debugging menu
// Debug program: F5 or Debug > Start Debugging menu

// Tips for Getting Started: 
//   1. Use the Solution Explorer window to add/manage files
//   2. Use the Team Explorer window to connect to source control
//   3. Use the Output window to see build output and other messages
//   4. Use the Error List window to view errors
//   5. Go to Project > Add New Item to create new code files, or Project > Add Existing Item to add existing code files to the project
//   6. In the future, to open this project again, go to File > Open > Project and select the .sln file
