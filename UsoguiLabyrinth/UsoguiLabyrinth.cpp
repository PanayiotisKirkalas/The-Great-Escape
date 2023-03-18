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

#define elif else if

#define _A 2
#define _B 4
#define _C 6
#define _D 8
#define _E 10
#define _F 12
#define _1 _A
#define _2 _B
#define _3 _C
#define _4 _D
#define _5 _E
#define _6 _F

#define HIT_WALL true
#define WON false
#define PAUSE if (int c = _getch() != char(13)) {c = _getch();}

bool MainMenu2(bool& go_a) {
	char ch;
	int index = 0, n = 0;
	while (1) {
		system("cls");
		cout << "MAIN MENU" << endl;
		if (index == 0) {
			if (go_a) {
				cout << "    ->Play again" << endl; //+ 1 tab
			}
			else {
				cout << "    ->Play" << endl;
			}
			cout << "      Controls" << endl << "      Back";
			//	pr = 'r';
			//if (go_a) {
			//	cout << "MAIN MENU" << endl << "\t	Restart (press r)" << endl << "\t	Controls (press c)" << endl << "\t	Quit (press q)";
			//	pr = 'r';
			//}
			//else {
			//	cout << "MAIN MENU" << endl << "\t	Play (press p)" << endl << "\t	Controls (press c)" << endl << "\t	Quit (press q)";
			//	pr = 'p';
			//}
		}
		elif(index == 1) {
			if (go_a) {
				cout << "      Play again" << endl; //+ 1 tab
			}
			else {
				cout << "      Play" << endl;
			}
			cout << "    ->Controls" << endl << "      Back";
		}
		elif(index == 2) {
			if (go_a) {
				cout << "      Play again" << endl; //+ 1 tab
			}
			else {
				cout << "      Play" << endl;
			}
			cout << "      Controls" << endl << "    ->Back";
		}
		while (1) {
			if (_kbhit()) {
				ch = _getch();
				break;
			}
		}
		if (ch == char(13)) {
			if (index == 0) {
				go_a = false;
				do {
					system("cls");
					cout << "Number of players (1-4): ";
					cin >> n;
				} while (n > 4 || n < 2);
				Mode2 m2(n);
				m2.Setup();
				m2.Play();
				//GameOverf();
				go_a = true;
			}
			elif(index == 1) {
				system("cls");
				cout << "Controls" << endl << endl << "\tMove: \tW, A, S, D for Up, Left, Down and Right respectively" << endl << endl << "Press enter to go back";
				PAUSE
				system("cls");
			}
			elif(index == 2) {
				return true;
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

bool MainMenu1(bool& go_a) {
	char ch;
	int index = 0;
	while (1) {
		system("cls");
		cout << "MAIN MENU" << endl;
		if (index == 0) {
			if (go_a) {
				cout << "    ->Play again" << endl; //+ 1 tab
			}
			else {
				cout << "    ->Play" << endl;
			}
			cout << "      Controls" << endl << "      Back";
			//	pr = 'r';
			//if (go_a) {
			//	cout << "MAIN MENU" << endl << "\t	Restart (press r)" << endl << "\t	Controls (press c)" << endl << "\t	Quit (press q)";
			//	pr = 'r';
			//}
			//else {
			//	cout << "MAIN MENU" << endl << "\t	Play (press p)" << endl << "\t	Controls (press c)" << endl << "\t	Quit (press q)";
			//	pr = 'p';
			//}
		}
		elif(index == 1) {
			if (go_a) {
				cout << "      Play again" << endl; //+ 1 tab
			}
			else {
				cout << "      Play" << endl;
			}
			cout << "    ->Controls" << endl << "      Back";
		}
		elif(index == 2) {
			if (go_a) {
				cout << "      Play again" << endl; //+ 1 tab
			}
			else {
				cout << "      Play" << endl;
			}
			cout << "      Controls" << endl << "    ->Back";
		}
		while (1) {
			if (_kbhit()) {
				ch = _getch();
				break;
			}
		}
		if (ch == char(13)) {
			if (index == 0) {
				go_a = false;
				Mode1 m1;
				m1.Setup();
				m1.Play();
				//GameOverf();
				go_a = true;
			}
			elif(index == 1) {
				system("cls");
				cout << "Controls" << endl << endl << "\tMove:\tW, A, S, D for Up, Left, Down and Right respectively" << endl << endl << "Press enter to go back";
				PAUSE
				system("cls");
			}
			elif(index == 2) {
				return true;
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
		if (index == 0) {
			//if (go_a) {
			//	cout << "    ->1v1" << endl; //+ 1 tab
			//}
			//else {
			//	cout << "    ->Play" << endl;
			//}
			cout << "    ->1v1" << endl; //+ 1 tab
			cout << "      Everyone for themselves (up to 4 players)" << endl << "      Quit";
			//	pr = 'r';
			//if (go_a) {
			//	cout << "MAIN MENU" << endl << "\t	Restart (press r)" << endl << "\t	Controls (press c)" << endl << "\t	Quit (press q)";
			//	pr = 'r';
			//}
			//else {
			//	cout << "MAIN MENU" << endl << "\t	Play (press p)" << endl << "\t	Controls (press c)" << endl << "\t	Quit (press q)";
			//	pr = 'p';
			//}
		}
		elif(index == 1) {
			cout << "      1v1" << endl; //+ 1 tab
			cout << "    ->Everyone for themselves (up to 4 players)" << endl << "      Quit";
		}
		elif(index == 2) {
			cout << "      1v1" << endl; //+ 1 tab
			cout << "      Everyone for themselves (up to 4 players)" << endl << "    ->Quit";
		}
		while (1) {
			if (_kbhit()) {
				ch = _getch();
				break;
			}
		}
		if (ch == char(13)) {
			if (index == 0) {
				go_a = false;
				MainMenu1(go_a);
				//GameOverf();
				go_a = true;
			}
			elif(index == 1) {
				//system("cls");
				//cout << "Constrols" << endl << endl << "\tMove: \tA, D or L.Arrow, R.Arrow" << endl << "\tShoot: \tW or Up Arrow or Space" << endl << "\tPause: \tShift + P" << endl << "\tReset Highscore:\tShift + R" << endl << endl << "Press any key to go back";
				//char c2 = _getch(), c3 = _getch();
				//system("cls");
				go_a = false;
				MainMenu2(go_a);
				//GameOverf();
				go_a = true;
			}
			elif(index == 2) {
				return true;
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
    //Mode1 m1;
    //Mode2 m2(4);
	//
    //m1.Setup();
    //m1.Play();
    //m2.Setup();
    //m2.Play();

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
