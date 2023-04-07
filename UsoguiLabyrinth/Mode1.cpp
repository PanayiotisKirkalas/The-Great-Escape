#include <iostream>
#include <conio.h>
#include <stdlib.h>

#include "Mode1.h"

#define HIT_WALL true
#define WON false
#define DONT_SHOW_WALLS false
#define SHOW_WALLS true
#define bDir branches.top().directions
#define bPos branches.top().pos

#ifndef DEBUG(x, y) 
#define DEBUG(x, y) cout << "[DEBUG] " << x << y << endl; (_getch() != char(13) ? _getch() : 0);
#endif // DEBUG(x, y) cout << x << y << endl; (_getch() != char(13) ? _getch() : 0);

#ifndef PAUSE 
#define PAUSE if (int c = _getch() != char(13)) {c = _getch();}
#endif // !1

#define UP 0
#define DOWN 2
#define RIGHT 1
#define LEFT 3

using std::cout;
using std::cin;
using std::endl;

Mode1::Mode1() {
	for (int i = 0; i < 2; ++i) {
		P[i] = Mode1Player(i + 1);
	}

	Explanation =
		"How to play:\n\n"
		"\tBoth players will make a labyrinth for the other to solve. There is the option to let the program randomly\n"
		"\tgenerate a labyrinth\n"
		"\tBoth players take turns to move through the labyrinths each one made for the other with invisible walls.\n"
		"\nHow to move:\n"
		"\tUP: W\n"
		"\tDOWN: S\n"
		"\tLEFT: A\n"
		"\tRIGHT: D\n"
		"\nHow to Build:\n"
		"\tYou enter the coordinates of the block you want to add a wall around (e.g. E5)\n"
		"\tand then the direction that you want to build towards, the direction is inputed the same way you move"
		"\tYou can enter up to 20 walls\n"
		"\nPress Enter to go back"
		;
}

bool Mode1::Validate(Labyrinth &l) {
	Solver solver(l);
	
	return solver.Solve();
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
		P[i].Ask();
		cout << "Validating Labyrinth..." << endl;
		while (!Validate(P[i].getLabyrinth())) {
			cout << "Invalid Labyrinth..." << endl;
			PAUSE
			P[i].Ask();
			cout << "Validating Labyrinth..." << endl;
		}
		system("cls");
		cout << "The labyrinth is valid" << endl;
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

	P[i].getLabyrinth().printLabyrinth(P[i].getName(), SHOW_WALLS);
	cout << endl << P[i].getName() << " won!";
	PAUSE
}

//Solver::Solver(coordinate p, int prev_dir, Solver* prev, Labyrinth& other)
//	: s{ nullptr, nullptr, nullptr, nullptr }, pos(p), l(other), prev(prev) {
//	s[(prev_dir + 2) % 4] = prev;
//}

Solver::Solver(Labyrinth& other)
	: pos(other.getStart()), l(other) {
	for (int i = 0; i < 6; ++i) {
		for (int k = 0; k < 6; ++k) {
			checkedRooms[i][k] = false;
		}
	}
}

int Solver::Move(int dir, bool on_branch) {
	if (on_branch) {
		branches.top().directions[dir] = false;
		--branches.top().openDirN;
	}

	checkedRooms[pos.first][pos.second] = true;

	switch (dir)
	{
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
	default:
		break;
	}

	return (dir + 2) % 4;
}

coordinate Solver::calculatePos(coordinate pos, int dir) {
	switch (dir)
	{
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
	default:
		break;
	}

	return pos;
}

bool Solver::Solve() {
	bool directions[4] = { true, true, true, true }, deja_vu = false;
	char dir[4] = { 'w', 'd', 's', 'a' };
	int openDir, openDirN = 4, prev = -1;
	coordinate temp;

	//branches.push(Branch());
	//branches.top().pos = this->pos;

	static int test = 0, test2 = 0;

	while (1) {
		openDirN = 4;

		if (this->pos == l.getFinish())
			return true;

		for (int i = 0; i < 4; ++i) {
			temp = calculatePos(pos, i);
			if (checkedRooms[temp.first][temp.second])
				goto close_path;
			if (!directions[i])
				goto close_path;
			if (l.isClosed(this->pos, dir[i]) || i == prev) 
				goto close_path;
			if (i == UP && pos.first <= 0)
				goto close_path;
			if (i == RIGHT && pos.second >= 5)
				goto close_path;
			if (i == DOWN && pos.first >= 5)
				goto close_path;
			if (i == LEFT && pos.second <= 0)
				goto close_path;

			//DEBUG(++test, i);
			goto select_path;
		close_path:
			directions[i] = false;
			--openDirN;
			continue;
		select_path:
			openDir = i;
		}

		//DEBUG(char(pos.first + 'A'), pos.second + 1)
		//DEBUG("Local Directions: ", directions[0] * 1000 + directions[1] * 100 + directions[2] * 10 + directions[3]);
		//DEBUG("Local Open paths: ", openDirN);

		
		if (openDirN == 0) {
			if (branches.empty())
				return false;
			if (pos == branches.top().pos) {
				//DEBUG("Popping", "")
				branches.pop();
			}
			if (branches.empty())
				return false;

			pos = branches.top().pos;
			//openDirs[branches.top().second.first] = false;
			openDirN = branches.top().openDirN;
			for (int i = 0; i < 4; ++i) {
				directions[i] = branches.top().directions[i];
			}
			deja_vu = true;
			prev = -1;
		}
		else if (openDirN == 1) {
			prev = Move(openDir, (branches.empty() ? false : pos == branches.top().pos));

			for (int i = 0; i < 4; ++i) {
				directions[i] = true;
			}

			deja_vu = false;
		}
		else if (openDirN > 1) {
			if (!deja_vu) {
				branches.push(Branch());
				branches.top().pos = this->pos;
				for (int i = 0; i < 4; ++i) {
					branches.top().directions[i] = directions[i];
				}
				branches.top().directions[prev] = false;
				--branches.top().openDirN;
			}
			else 
				deja_vu = false;
			
			prev = Move(openDir, true);
		}

		//if (!branches.empty())
		//	if (branches.top().pos == this->pos) {
		//		DEBUG(char(bPos.first + 'A'), char(bPos.second + '1'))
		//		DEBUG("Top branch Directions :", bDir[0] * 1000 + bDir[1] * 100 + bDir[2] * 10 + bDir[3]);
		//		DEBUG("Top branch Open paths: ", branches.top().openDirN);
		//	}
		//DEBUG("Moving: ", dir[openDir]);
		//DEBUG("Size: ", branches.size());

		if (prev >= 0) {
			for (int i = 0; i < 4; ++i) {
				directions[i] = true;
			}
			directions[prev] = false;
		}
	}
}

//s[i] = Call(i);
					////DEBUG(++test2, i);
					//if (s[i]->Solve())
					//	return true;

//Solver::~Solver() {
//	for (int i = 0; i < 4; ++i) {
//		DEBUG("Destructor", i)
//		if (this->s[i] != nullptr && this->s[i] != this->prev)
//			delete s[i];
//	}
//}