#pragma once
#include <iostream>
#include <conio.h>
//#include <stdlib.h>
#include <vector>
//#include <cstdlib>
//#include <chrono>
//#include <ctime>
//#include <utility>
#include <iterator>
#include <cmath>

#include "Mode2.h"

using std::cout;
using std::cin;
using std::endl;
//using std::make_pair;
//using std::chrono::system_clock;
//using std::chrono::duration_cast;
//using std::chrono::milliseconds;
//using std::chrono::duration;
//using std::chrono::time_point;

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

//coordinate Mode2::SetupLabyrinth(Labyrinth& l) {
//	time_point<system_clock> t = system_clock::now();
//	srand(RANDOM_SEED(t));
//	coordinate start, finish;
//
//	for (int i = 0; i < 6; ++i) {
//		for (int j = 0; j < 6; ++j) {
//			l.BuildWall(make_pair(i, j), 1);
//			l.BuildWall(make_pair(i, j), 4);
//			if (i < 5)
//				l.BuildWall(make_pair(i, j), 3);
//			if (j < 5)
//				l.BuildWall(make_pair(i, j), 2);
//		}
//	}
//
//	do {
//		start.first = rand() % 6;
//		start.second = rand() % 6;
//		finish.first = rand() % 6;
//		finish.second = rand() % 6;
//	} while (DISTANCE(start, finish) < 4.75);
//
//	l.setStart(start);
//	l.setFinish(finish);
//	BuildLabyrinth(l);
//	return start;
//}
//
//void Mode2::BuildLabyrinth(Labyrinth& l) {
//	int dir;
//	coordinate pos, temp;
//	Grid grid;
//
//	pos.first = rand() % 6;
//	pos.second = rand() % 6;
//
//	grid.Open(pos);
//	while (grid.getClosed().size() > 0) {
//		dir = 1 + rand() % 4;
//		temp = pos;
//		switch (dir % 2)
//		{
//		case 1:
//			temp.first += (dir == 1 ? -1 : 1);
//			break;
//		case 0:
//			temp.second += (dir == 2 ? 1 : -1);
//			break;
//		default:
//			break;
//		}
//		if (std::find(grid.getFrontier().begin(), grid.getFrontier().end(), temp) != grid.getFrontier().end()) {
//			grid.Open(temp);
//			l.EraseWall(pos, dir);
//		}
//
//		pos = grid.getRandomOpen();
//	}
//}

void Mode2::Setup() {
	string name;
	char c;

	for (int i = 0; i < Players.size(); ++i) {
		system("cls");
		cout << "Enter your name: ";
		cin >> name;
		Players[i].setName(name);
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

//Room::Room() : open(false), frontier(false), closed(true) {
//
//}
//
//Grid::Grid() {
//	for (int i = 0; i < 6; ++i) {
//		for (int j = 0; j < 6; ++j) {
//			closed.push_back(make_pair(i, j));
//		}
//	}
//}
//
//int Grid::Open(coordinate pos) {
//	rooms[pos.first][pos.second].open = true;
//	rooms[pos.first][pos.second].closed = false;
//	rooms[pos.first][pos.second].frontier = false;
//
//	this->open.push_back(make_pair(pos.first, pos.second));
//	this->frontier.remove(pos);
//	this->closed.remove(pos);
//
//	// else-if not used bcs they function separately
//	if (pos.first > 0 && rooms[pos.first - 1][pos.second].closed == true) {
//		rooms[pos.first - 1][pos.second].frontier = true;
//		this->frontier.push_back(make_pair(pos.first - 1, pos.second));
//	}
//	if (pos.first < 5 && rooms[pos.first + 1][pos.second].closed == true) {
//		rooms[pos.first + 1][pos.second].frontier = true;
//		this->frontier.push_back(make_pair(pos.first + 1, pos.second));
//	}
//	if (pos.second > 0 && rooms[pos.first][pos.second - 1].closed == true) {
//		rooms[pos.first][pos.second - 1].frontier = true;
//		this->frontier.push_back(make_pair(pos.first, pos.second - 1));
//	}
//	if (pos.second < 5 && rooms[pos.first][pos.second + 1].closed == true) {
//		rooms[pos.first][pos.second + 1].frontier = true;
//		this->frontier.push_back(make_pair(pos.first, pos.second + 1));
//	}
//
//	return this->open.size();
//}
//
//const list<coordinate>& Grid::getOpen() const {
//	return this->open;
//}
//
//const coordinate& Grid::getRandomOpen() const {
//	int index = rand() % this->open.size();
//	list<coordinate>::const_iterator it = this->open.begin();
//	std::advance(it, index);
//	return *it;
//}
//
//const list<coordinate>& Grid::getClosed() const {
//	return this->closed;
//}
//
//const list<coordinate>& Grid::getFrontier() const {
//	return this->frontier;
//}