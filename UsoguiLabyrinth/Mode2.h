#pragma once
#include <vector>
#include <list>

#include "Player.h"

#ifndef coordinate
#define coordinate pair<int, int>
#endif // !coordinate pair<int, int>
using std::list;

//class Room {
//public:
//	bool open, frontier, closed;
//	Room();
//};
//
//class Grid {
//	list<coordinate> closed, open, frontier;
//	Room rooms[6][6];
//
//	
//public:
//	Grid();
//	int Open(coordinate pos);
//	const list<coordinate>& getOpen() const;
//	const list<coordinate>& getClosed() const;
//	const list<coordinate>& getFrontier() const;
//	const coordinate& getRandomOpen() const;
//};

class Mode2
{
	vector<Mode2Player> Players;
	vector<int> CorpseList;

	//coordinate SetupLabyrinth(Labyrinth& l);
	//void BuildLabyrinth(Labyrinth& l);

public:
	string Explanation;

	Mode2();
	Mode2(int n_Players);
	void Setup();
	void Play();
	void ConfirmDeath(int index);
	void GatherCorpses();
};

