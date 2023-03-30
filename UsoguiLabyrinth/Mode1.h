#pragma once
#include <string>

#include "Player.h"

class Mode1
{
	Mode1Player P[2];

public:
	string Explanation;

	Mode1();
	void Setup();
	void Play();
};

