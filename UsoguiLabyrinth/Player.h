#pragma once
#include <string>
#include <conio.h>
#include <utility>
#include <vector>

#include "Labyrinth.h"
using std::string;
using std::pair;
using std::vector;
#ifndef coordinate 
#define coordinate pair<int, int>
#endif // !coordinate pair<int, int>


class Mode2;

class Player
{
protected:
	int id;
	char symbol;
	coordinate pos;
	Labyrinth Own;
	
public:
	string Name;
	Player();
	Player(int id);
	void askName();
	bool Move();
	char getSymbol() const;
	void setPos(coordinate p);
	string getName() const;
	coordinate getPos() const;
	int getID() const;
	Labyrinth& getLabyrinth();
};

class Mode1Player : public Player {
	using Player::Player;

public:
	using Player::getLabyrinth;
	void Ask();
	void BuildLabyrinth();
	bool Move(Labyrinth& Other);
};

class Mode2Player : public Player {
	using Player::Player;

	bool dead;
	int points, lives;
	Mode2 *m2;

	Mode2Player* MetSomeone(vector<Mode2Player>& v);
	int Battle(Mode2Player* p);
	int Bet(Mode2Player* p);

public:
	using Player::getID;
	Mode2Player();
	Mode2Player(int value, Mode2* mode);
	void setPoints(int v);
	int getPoints() const;
	void IncPoints(int value = 1);
	void DecPoints(int value = 1);
	int LoseLife();
	int Move(vector<Mode2Player>& v, bool agro = true);
};

