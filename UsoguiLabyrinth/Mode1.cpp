#include <iostream>
#include <conio.h>
#include <stdlib.h>

#include "Mode1.h"

#define HIT_WALL true
#define WON false

#define DEBUG(x, y) cout << x << y << endl; (_getch() != char(13) ? _getch() : 0);
#define PAUSE if (int c = _getch() != char(13)) {c = _getch();}

using std::cout;
using std::cin;
using std::endl;

Mode1::Mode1() {
	for (int i = 0; i < 2; ++i) {
		P[i] = Mode1Player(i + 1);
	}
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
		P[i].BuildLabyrinth();
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

	cout << endl << P[i].getName() << " won!";
	PAUSE
}