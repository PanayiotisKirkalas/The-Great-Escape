#pragma once
#include <vector>
#include <list>
#include <utility>
#include <string>
#include <iterator>
#include <cmath>

using std::vector;
using std::pair;
using std::string;
using std::list;

#ifndef coordinate
#define coordinate pair<int, int>
#endif // !coordinate pair<int, int>

class Room {
public:
	bool open, frontier, closed;
	Room();
};

class Grid {
	list<coordinate> closed, open, frontier;
	Room rooms[6][6];


public:
	Grid();
	int Open(coordinate pos);
	const list<coordinate>& getOpen() const;
	const list<coordinate>& getClosed() const;
	const list<coordinate>& getFrontier() const;
	const coordinate& getRandomOpen() const;
};

class Labyrinth
{
	int n_walls;
	coordinate start, finish;
	vector<vector<char>> map;

	coordinate translate(coordinate c) const;

public:
	Labyrinth();
	char at(coordinate pos) const;
	void alter(coordinate pos, char c);
	bool BuildWall(coordinate pos, int dir);
	bool EraseWall(coordinate pos, int dir);
	bool isClosed(coordinate pos, char dir);
	void printLabyrinth(const string& name, bool show_walls);
	coordinate getStart() const;
	coordinate getFinish() const;
	coordinate SetupLabyrinth();
	void BuildLabyrinth();
	void setStart(coordinate pos);
	void setFinish(coordinate pos);
	void AddDelWall(int condition);
	int getNofWalls() const;
};

