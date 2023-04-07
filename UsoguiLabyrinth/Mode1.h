#pragma once
#include <string>
#include <stack>

#include "Player.h"

#ifndef coordinate
#define coordinate pair<int, int>
#endif // !coordinate pair<int, int>

#ifndef direction
#define direction int
#endif // !direction

#ifndef branches_n
#define branches_n int
#endif // !branches_n

using std::stack;
using std::make_pair;

class Branch {
public:
	coordinate pos;
	bool directions[4] = {true, true, true, true};
	int openDirN = 4;
};

class Solver {
	//Solver* s[4], *prev;
	//int prev;
	stack<Branch> branches;
	coordinate pos;
	Labyrinth& l;
	bool checkedRooms[6][6];

	//Solver(coordinate p, int prev_dir, Solver* prev, Labyrinth& other);
	int Move(int dir, bool on_branch);
	coordinate calculatePos(coordinate pos, int dir);
	//Solver* Call(int dir);
public:
	Solver(Labyrinth& other);
	bool Solve();
	//~Solver();
};

class Mode1
{
	Mode1Player P[2];

public:
	string Explanation;

	Mode1();
	bool Validate(Labyrinth &l);
	void Setup();
	void Play();
};
 
