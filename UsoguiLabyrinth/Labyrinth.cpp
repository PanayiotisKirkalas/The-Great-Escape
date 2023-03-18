#include <iostream>
#include <cmath>
#include <string>
#include <stdlib.h>

#include "Labyrinth.h"
using std::cout;
using std::cin;
using std::endl;
using std::string;

#define _UP_ 'w'
#define _DOWN_ 's'
#define _LEFT_ 'a'
#define _RIGHT_ 'd'
#define _VWALL_ '|'
#define _HWALL_ '='
#define _JOINT_ '+'
#define _ROOM_ ' '
#define UPPER_LIMIT 2
#define LEFT_LIMIT 2
#define RIGHT_LIMIT 12
#define DOWN_LIMIT 12
#define DEBUG(x, y) cout << x << y << endl; (_getch() != char(13) ? _getch() : 0);
#define PAUSE if (int c = _getch() != char(13)) {c = _getch();}

Labyrinth::Labyrinth() : n_walls(0){
	char side = 'A';

	map.push_back(vector<char>{_ROOM_, _VWALL_, '1', _ROOM_, '2', _ROOM_, '3', _ROOM_, '4', _ROOM_, '5', _ROOM_, '6', _ROOM_}); //14 elements
	//14 elements
	map.push_back(vector<char>{_HWALL_, '+', _HWALL_, _HWALL_, _HWALL_, _HWALL_, _HWALL_, _HWALL_, _HWALL_, _HWALL_, _HWALL_, _HWALL_, _HWALL_, _HWALL_});
	for (int i = 2; i < 14; ++i) {
		if (i % 2 == 0) {
			map.push_back(vector<char>{side, _VWALL_, _ROOM_, _ROOM_, _ROOM_, _ROOM_, _ROOM_, _ROOM_, _ROOM_, _ROOM_, _ROOM_, _ROOM_, _ROOM_, _ROOM_}); //14 elements
			++side;
		}
		else//14 elements
			map.push_back(vector<char>{_ROOM_, _VWALL_, _ROOM_, _JOINT_, _ROOM_, _JOINT_, _ROOM_, _JOINT_, _ROOM_, _JOINT_, _ROOM_, _JOINT_, _ROOM_, _JOINT_});
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
	//DEBUG(pos.first, pos.second)
	
	switch (dir) {
	case 1:
		--pos.first;
		break;
	case 2:
		++pos.second;
		break;
	case 3:
		++pos.first;
		break;
	case 4:
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
	//DEBUG(pos.first, pos.second)

	switch (dir) {
	case 1:
		--pos.first;
		break;
	case 2:
		++pos.second;
		break;
	case 3:
		++pos.first;
		break;
	case 4:
		--pos.second;
		break;
	}

	if ((pos.first <= 2 && dir == 1) || (pos.second <= 2 && dir == 4))
		return false;
	//if (dir == 1 || dir == 3) {
		map.at(pos.first).at(pos.second) = ' ';
	//}
	//else if (dir == 2 || dir == 4) {
	//	map.at(pos.first).at(pos.second) = _VWALL_;
	//}
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
	//return this->finish;
	coordinate c = this->finish;
	c.first = (c.first / 2) - 1;
	c.second = (c.second / 2) - 1;
	return c;
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