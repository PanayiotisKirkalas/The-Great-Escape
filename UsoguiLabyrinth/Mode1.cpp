#include <iostream>
#include <conio.h>
#include <stdlib.h>

#include "Mode1.h"

#define HIT_WALL true
#define WON false
#define DONT_SHOW_WALLS false
#define SHOW_WALLS true

#define DEBUG(x, y) cout << x << y << endl; (_getch() != char(13) ? _getch() : 0);
#define PAUSE if (int c = _getch() != char(13)) {c = _getch();}

using std::cout;
using std::cin;
using std::endl;

Mode1::Mode1() {
	for (int i = 0; i < 2; ++i) {
		P[i] = Mode1Player(i + 1);
	}

	Explanation =
		"How to play:\n\n"
		"\tBoth players will make a labyrinth for the other to solve. There is the option to let the program randomly\n"
		"\tgenerate a labyrinth\n"
		"\tBoth players take turns to move through the labyrinths each one made for the other with invisible walls.\n"
		"\nHow to move:\n"
		"\tUP: W\n"
		"\tDOWN: S\n"
		"\tLEFT: A\n"
		"\tRIGHT: D\n"
		"\nHow to Build:\n"
		"\tYou enter the coordinates of the block you want to add a wall around (e.g. E5)\n"
		"\tand then the direction that you want to build towards, the direction is inputed the same way you move"
		"\tYou can enter up to 20 walls\n"
		"\nPress Enter to go back"
		;
}

void Mode1::Setup() {
	string name;
	char c2;
	
	for (int i = 0; i < 2; ++i) {
		system("cls");
		cout << "Player " << i + 1 << endl;
		cout << "Username: " << std::flush;
		cin >> name;
		system("cls");
		P[i] = Mode1Player(name);
		P[i].Ask();
		system("cls");
		cout << "Give to other player" << endl;
		cout << "Press enter to continue..." << std::flush;
		PAUSE
	}

	P[0].setPos(P[1].getLabyrinth().getStart());
	P[1].setPos(P[0].getLabyrinth().getStart());
	system("cls");
}

void Mode1::Play() {
	char c;
	bool i;
	for (i = 0; P[i].Move(P[!i].getLabyrinth()) != WON; i = !i) {
		system("cls");
		cout << "You hit a wall. Give next player" << endl;
		cout << "Press enter to continue..." << std::flush;
		PAUSE
		system("cls");
	}

	P[i].getLabyrinth().printLabyrinth(P[i].getName(), SHOW_WALLS);
	cout << endl << P[i].getName() << " won!";
	PAUSE
}