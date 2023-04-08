#pragma once
#include <iostream>
#include <conio.h>
#include <vector>
#include <iterator>
#include <cmath>

#include "Mode2.h"

using std::cout;
using std::cin;
using std::endl;

#define HIT_WALL -2
#define WON -1
#define LOST -3
#define DONT_SHOW_WALLS false
#define SHOW_WALLS true

#ifndef DEBUG(x, y)
#define DEBUG(x, y) cout << "[DEBUG] " << x << y << endl; (_getch() != char(13) ? _getch() : 0);
#endif // DEBUG(x, y) cout << x << y << endl; (_getch() != char(13) ? _getch() : 0);

#ifndef PAUSE
#define PAUSE if (int c = _getch() != char(13)) {c = _getch();}
#endif // !1


Mode2::Mode2() {
	Explanation =
		"How to play:\n\n"
		"All the players take turns to move through randomly generated labyrinths with invisible walls.\n"
		"A player loses their turn when they hit a wall. Every time you move and don't hit a wall, you gain one point,\n"
		"if you meet another player, both of you will have to bet an amount of points. It will then be the bet's winner turn\n"
		"to move and the loser will lose 1HP (each player starts with 2HP), if there is draw, it will also be the other\n"
		"player's turn. Each side will lose the points they used.\n"
		"A player wins if they are the only one left or if they reach the finish of the labyrinth (F).\n"
		"A player loses if someone reaches the finish before them or if they lose all their lives (everyone starts with 2)\n"
		"How to move:\n"
		"\tUP: W\n"
		"\tDOWN: S\n"
		"\tLEFT: A\n"
		"\tRIGHT: D\n"
		"\nPress Enter to go back"
		;
}

Mode2::Mode2(int n_Players) {
	Players.clear();
	for (int i = 0; i < n_Players; ++i) {
		Players.push_back(Mode2Player(i, this));
	}
}

void Mode2::Setup() {
	string name;
	char c;

	for (int i = 0; i < Players.size(); ++i) {
		system("cls");
		Players[i].askName();
		Players[i].setPos(Players[i].getLabyrinth().SetupLabyrinth());
		system("cls");
		cout << "Next player" << endl;
		cout << "Press Enter to continue..." << endl << std::flush;
		PAUSE
	}
	system("cls");
}

void Mode2::Play() {
	char c;
	bool GameOver = false;
	int i, result;


	for (i = 0; (result = Players.at(i).Move(Players)) != WON; (++i) %= Players.size()) {
		cout << "[DEBUG] " << i << endl;
		if (result != HIT_WALL) {
			i = result - 1;
		}
		else {
			system("cls");
			cout << "You hit a wall. Give next player" << endl;
			cout << "Press enter to continue..." << std::flush;
			PAUSE
		}

		GatherCorpses();
		system("cls");
	}

	cout << endl << Players.at(i).getName() << " won!" << endl;
	PAUSE
	return;
}

void Mode2::ConfirmDeath(int index) {
	this->CorpseList.push_back(index);
}

void Mode2::GatherCorpses() {
	for (int i : this->CorpseList)
		this->CorpseList.erase(this->CorpseList.begin() + i);
	this->CorpseList.clear();
}