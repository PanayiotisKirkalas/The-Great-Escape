#include <iostream>
#include <cmath>
#include <string>
#include <stdlib.h>
#include <iterator>
#include <chrono>

#include "Labyrinth.h"
using std::cout;
using std::cin;
using std::endl;
using std::string;
using std::make_pair;
using std::chrono::system_clock;
using std::chrono::duration_cast;
using std::chrono::milliseconds;
using std::chrono::duration;
using std::chrono::time_point;

#define _UP_ 'w'
#define _DOWN_ 's'
#define _LEFT_ 'a'
#define _RIGHT_ 'd'
#define _VWALL_ '|'
#define _HWALL_ '='
#define _JOINT_ '+'
#define _ROOM_ ' '
#define UP 1
#define DOWN 3
#define RIGHT 2
#define LEFT 4
#define UPPER_LIMIT 2
#define LEFT_LIMIT 2
#define RIGHT_LIMIT 12
#define DOWN_LIMIT 12
#define SIZE 6

#ifndef DEBUG(x, y)
#define DEBUG(x, y) cout << "[DEBUG] " << x << y << endl; (_getch() != char(13) ? _getch() : 0);
#endif // DEBUG(x, y) cout << x << y << endl; (_getch() != char(13) ? _getch() : 0);

#ifndef PAUSE
#define PAUSE if (int c = _getch() != char(13)) {c = _getch();}
#endif // !1

#ifndef RANDOM_SEED(x)
#define RANDOM_SEED(x) duration_cast<milliseconds>(x.time_since_epoch()).count()
#endif // !1

#ifndef DISTANCE(x, y)
#define DISTANCE(x, y) sqrt(pow(start.first - finish.first, 2)+pow(start.second - finish.second, 2))
#endif // ! DISTANCE(x, y) sqrt(pow(start.first - finish.first, 2)+pow(start.second - finish.second, 2))

Labyrinth::Labyrinth() : n_walls(0){
	char side = 'A', column = '1';
	vector<char> row;

	row.push_back(_ROOM_);
	row.push_back(_VWALL_);
	for (int i = 0; i < (SIZE * 2); ++i) {
		row.push_back(column);
		row.push_back(_ROOM_);
		++column;
	}
	map.push_back(row);
	row.clear();

	row.push_back(_HWALL_);
	row.push_back(_JOINT_);
	for (int i = 0; i < (SIZE * 2); ++i) {
		row.push_back(_HWALL_);
	}
	map.push_back(row);
	row.clear();

	for (int i = 0; i < (SIZE * 2); ++i) {
		if (i % 2 == 0) {
			row.push_back(side);
			++side;
			row.push_back(_VWALL_);
			for (int k = 0; k < SIZE; ++k) {
				row.push_back(_ROOM_);
				row.push_back(_ROOM_);
			}
		}
		else {
			row.push_back(_ROOM_);
			row.push_back(_VWALL_);
			for (int k = 0; k < SIZE; ++k) {
				row.push_back(_ROOM_);
				row.push_back(_JOINT_);
			}
		}

		map.push_back(row);
		row.clear();
	}
}

void Labyrinth::printLabyrinth(const string& name, bool show_walls) {
	cout << "Player: " << name << endl;
	char c;
	for (int i = 0; i < 14; ++i) {
		for (int j = 0; j < 14; ++j) {
			c = map.at(i).at(j);
			cout << ((i >= 2 && j >= 2) && (c == _VWALL_ || c == _HWALL_) && !show_walls ? ' ' : c) << std::flush;
		}
		cout << endl;
	}
}

coordinate Labyrinth::translate(coordinate c) const {
	c.first = (c.first * 2) + 2;
	c.second = (c.second * 2) + 2;
	return c;
}

char Labyrinth::at(coordinate pos) const {
	pos = translate(pos);
	return map.at(pos.first).at(pos.second);
}

void Labyrinth::alter(coordinate pos, char c) {
	pos = translate(pos);
	map.at(pos.first).at(pos.second) = c;
	return;
}

bool Labyrinth::BuildWall(coordinate pos, int dir) {
	pos = translate(pos);
	
	switch (dir) {
	case UP:
		--pos.first;
		break;
	case RIGHT:
		++pos.second;
		break;
	case DOWN:
		++pos.first;
		break;
	case LEFT:
		--pos.second;
		break;
	}

	if (map.at(pos.first).at(pos.second) != _ROOM_)
		return false;
	if (dir == 1 || dir == 3) {
		map.at(pos.first).at(pos.second) = _HWALL_;
	}
	else if (dir == 2 || dir == 4) {
		map.at(pos.first).at(pos.second) = _VWALL_;
	}
	this->AddDelWall(1);
	return true;
}

bool Labyrinth::EraseWall(coordinate pos, int dir) {
	pos = translate(pos);

	switch (dir) {
	case UP:
		--pos.first;
		break;
	case RIGHT:
		++pos.second;
		break;
	case DOWN:
		++pos.first;
		break;
	case LEFT:
		--pos.second;
		break;
	}

	if ((pos.first <= 2 && dir == 1) || (pos.second <= 2 && dir == 4))
		return false;
	
	map.at(pos.first).at(pos.second) = ' ';
	this->AddDelWall(-1);
	return true;
}

bool Labyrinth::isClosed(coordinate pos, char dir) {
	pos = translate(pos);
	switch (dir) {
	case _UP_:
		return (map.at(pos.first - 1).at(pos.second) != _ROOM_ && pos.first > UPPER_LIMIT ? true : false);
	case _DOWN_:
		return (map.at(pos.first + 1).at(pos.second) != _ROOM_ && pos.first < DOWN_LIMIT ? true : false);
	case _LEFT_:
		return (map.at(pos.first).at(pos.second - 1) != _ROOM_ && pos.second > LEFT_LIMIT ? true : false);
	case _RIGHT_:
		return (map.at(pos.first).at(pos.second + 1) != _ROOM_ && pos.second < RIGHT_LIMIT ? true : false);
	default:
		return false;
	}
}

coordinate Labyrinth::getStart() const {
	coordinate c = this->start;
	c.first = (c.first / 2) - 1;
	c.second = (c.second / 2) - 1;
	return c;
}

coordinate Labyrinth::getFinish() const {
	coordinate c = this->finish;
	c.first = (c.first / 2) - 1;
	c.second = (c.second / 2) - 1;
	return c;
}

coordinate Labyrinth::SetupLabyrinth() {
	time_point<system_clock> t = system_clock::now();
	srand(RANDOM_SEED(t));
	coordinate start, finish;

	for (int i = 0; i < 6; ++i) {
		for (int j = 0; j < 6; ++j) {
			this->BuildWall(make_pair(i, j), 1);
			this->BuildWall(make_pair(i, j), 4);
			if (i < 5)
				this->BuildWall(make_pair(i, j), 3);
			if (j < 5)
				this->BuildWall(make_pair(i, j), 2);
		}
	}

	do {
		start.first = rand() % 6;
		start.second = rand() % 6;
		finish.first = rand() % 6;
		finish.second = rand() % 6;
	} while (DISTANCE(start, finish) < 4.75);

	this->setStart(start);
	this->setFinish(finish);
	BuildLabyrinth();
	return start;
}

void Labyrinth::BuildLabyrinth() {
	int dir;
	coordinate pos, temp;
	Grid grid;

	pos.first = rand() % 6;
	pos.second = rand() % 6;

	grid.Open(pos);
	while (grid.getClosed().size() > 0) {
		dir = 1 + rand() % 4;
		temp = pos;
		switch (dir % 2)
		{
		case 1:
			temp.first += (dir == 1 ? -1 : 1);
			break;
		case 0:
			temp.second += (dir == 2 ? 1 : -1);
			break;
		default:
			break;
		}
		if (std::find(grid.getFrontier().begin(), grid.getFrontier().end(), temp) != grid.getFrontier().end()) {
			grid.Open(temp);
			this->EraseWall(pos, dir);
		}

		pos = grid.getRandomOpen();
	}
}

void Labyrinth::setStart(coordinate pos) {
	this->start = translate(pos);
	this->alter(pos, 'S');
}

void Labyrinth::setFinish(coordinate pos) {
	this->finish = translate(pos);
	this->alter(pos, 'F');
}

void Labyrinth::AddDelWall(int condition) {
	this->n_walls += condition;
}

int Labyrinth::getNofWalls() const {
	return this->n_walls;
}

Room::Room() : open(false), frontier(false), closed(true) {

}

Grid::Grid() {
	for (int i = 0; i < 6; ++i) {
		for (int j = 0; j < 6; ++j) {
			closed.push_back(make_pair(i, j));
		}
	}
}

int Grid::Open(coordinate pos) {
	rooms[pos.first][pos.second].open = true;
	rooms[pos.first][pos.second].closed = false;
	rooms[pos.first][pos.second].frontier = false;

	this->open.push_back(make_pair(pos.first, pos.second));
	this->frontier.remove(pos);
	this->closed.remove(pos);

	// else-if not used bcs they function separately
	if (pos.first > 0 && rooms[pos.first - 1][pos.second].closed == true) {
		rooms[pos.first - 1][pos.second].frontier = true;
		this->frontier.push_back(make_pair(pos.first - 1, pos.second));
	}
	if (pos.first < 5 && rooms[pos.first + 1][pos.second].closed == true) {
		rooms[pos.first + 1][pos.second].frontier = true;
		this->frontier.push_back(make_pair(pos.first + 1, pos.second));
	}
	if (pos.second > 0 && rooms[pos.first][pos.second - 1].closed == true) {
		rooms[pos.first][pos.second - 1].frontier = true;
		this->frontier.push_back(make_pair(pos.first, pos.second - 1));
	}
	if (pos.second < 5 && rooms[pos.first][pos.second + 1].closed == true) {
		rooms[pos.first][pos.second + 1].frontier = true;
		this->frontier.push_back(make_pair(pos.first, pos.second + 1));
	}

	return this->open.size();
}

const list<coordinate>& Grid::getOpen() const {
	return this->open;
}

const coordinate& Grid::getRandomOpen() const {
	int index = rand() % this->open.size();
	list<coordinate>::const_iterator it = this->open.begin();
	std::advance(it, index);
	return *it;
}

const list<coordinate>& Grid::getClosed() const {
	return this->closed;
}

const list<coordinate>& Grid::getFrontier() const {
	return this->frontier;
}