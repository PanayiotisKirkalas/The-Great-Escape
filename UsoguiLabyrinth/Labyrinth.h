#pragma once
#include <vector>
#include <utility>
#include <string>
using std::vector;
using std::pair;
using std::string;

#define coordinate pair<int, int>

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
	void setStart(coordinate pos);
	void setFinish(coordinate pos);
	void AddDelWall(int condition);
	int getNofWalls() const;
};

