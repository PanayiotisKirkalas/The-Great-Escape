#pragma once
#include <vector>
#include <list>

#include "Player.h"

#ifndef coordinate
#define coordinate pair<int, int>
#endif // !coordinate pair<int, int>
using std::list;

class Mode2
{
	vector<Mode2Player> Players;
	vector<int> CorpseList;

public:
	string Explanation;

	Mode2();
	Mode2(int n_Players);
	void Setup();
	void Play();
	void ConfirmDeath(int index);
	void GatherCorpses();
};

