import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;
import java.io.*;
import java.awt.event.*;

class Mode2Player extends Player {
	boolean dead;
	int points, lives;
	Mode2 m2;

	Mode2Player MetSomeone(ArrayList<Mode2Player> v) {
		for (Mode2Player p : v) {
			if (this.pos == p.getPos() && this.id != p.getID()) {
				System.out.print("\033[H\033[2J");//clear screen
				System.out.println("You've met Player: " + p.getName());
				new Scanner(System.in).nextLine();//pause
				return p;
			}
		}
		return null;
	}
	int Battle(Mode2Player p) {
		int bet1, bet2;

		bet1 = Bet(this);
		System.out.print("\033[H\033[2J");//clear screen
		System.out.println(p.getName() + "'s turn");
		System.out.print("Press enter to contiue...");
		new Scanner(System.in).nextLine();//pause
		bet2 = Bet(p);
		System.out.print("\033[H\033[2J");//clear screen

		switch (bet1 - bet2)
		{
		case 0:
			// draw
			System.out.println("Draw");
			System.out.println(p.getName() + "'s turn");
			System.out.print("Press enter to contiue...");
			new Scanner(System.in).nextLine();//pause
	 		return p.getID();
		default:
			switch (Integer.compare(bet1, bet2))
			{
			case 1:
				// player 2 loses
				System.out.println("Player " + p.getName() + " lost battle");
				if (p.LoseLife() == 0) {
					m2.ConfirmDeath(p.getID());
					System.out.print("Player " + p.getName() + " lost the game");
				}
				System.out.println(this.Name + "'s turn");
				System.out.print("Press enter to contiue...");
				new Scanner(System.in).nextLine();//pause
				return this.id;
			default:
				// player 1 loses
				System.out.println("Player " + this.Name + " lost battle");
				if (this.LoseLife() == 0) {
					m2.ConfirmDeath(this.id);
					System.out.println("Player " + this.getName() + " lost the game");
				}
				System.out.println(p.getName() + "'s turn");
				System.out.print("Press enter to contiue...");
				new Scanner(System.in).nextLine();//pause
				return p.getID();
			}
		}
	}
	int Bet(Mode2Player p) {
		int bet, points = p.getPoints();

		System.out.print("\033[H\033[2J");//clear screen

		System.out.println("Your points: " + points);
		System.out.print("How many points will you bet?: ");
		bet = new Scanner(System.in).nextInt();
		while (bet > points) {
			System.out.print("\033[H\033[2J");//clear screen
			System.out.println("Your points: " + points);
			System.out.println("INVALID ANSWER, CAN'T BET MORE POINTS THAN YOU HAVE");
			System.out.print("How many points you will bet?: ");
			bet = new Scanner(System.in).nextInt();
		}
		p.DecPoints(bet);
		return bet;
	}
	public Mode2Player() {
		super(-1);
		points = 0;
		lives = 2;
		dead = false;
		m2 = null;
	}
	public Mode2Player(int id, Mode2 mode) {
		super(id);
		this.symbol = 'O';
		m2 = mode;
	}
	public void setPoints(int v) {
		this.points = v;
	}
	public int getPoints() {
		return this.points;
	}
	public void IncPoints(int value) {
		this.points += value;
	}
	public void DecPoints(int value) {
		this.points -= value;
	}
	public int LoseLife() {
		--(this.lives);
		if (this.lives == 0)
			this.dead = true;

		return this.lives;
	}
	public int Move(ArrayList<Mode2Player> v) {
		char c;
		Mode2Player p;

		if (this.dead)
			return this.id + 1;

		this.Own.alter(this.pos, this.symbol);
		this.Own.printLabyrinth(!Labyrinth.show_walls);
		System.out.println("Your points: " + this.points);

		c = KeyEvent.KEY_PRESSED;

		while (!this.Own.isClosed(this.pos, c)) {
			switch (c)
			{
			case 'w':
				if (this.pos.y_axis > 0) {
					this.Own.alter(this.pos, '^');
					this.pos.y_axis -= 1;
				}
				else
					this.DecPoints(1);
				break;
			case 's':
				if (this.pos.y_axis < 5) {
					this.Own.alter(this.pos, 'V');
					this.pos.y_axis += 1;
				}
				else
					this.DecPoints(1);
				break;
			case 'a':
				if (this.pos.x_axis > 0) {
					this.Own.alter(this.pos, '<');
					this.pos.x_axis -= 1;
				}
				else
					this.DecPoints(1);
				break;
			case 'd':
				if (this.pos.x_axis < 5) {
					this.Own.alter(this.pos, '>');
					this.pos.x_axis += 1;
				}
				else
					this.DecPoints(1);
				break;
			default:
				this.DecPoints(1);
			}

			this.IncPoints(1);
			this.Own.alter(this.pos, this.symbol);
			System.out.print("\033[H\033[2J");//clear screen;
			this.Own.printLabyrinth(!Labyrinth.show_walls);
			System.out.println("Your points: " + this.points);

			if ((p = MetSomeone(v)) != null) {
				return Battle(p);
			}

			if (this.pos == this.Own.getFinish()) {
				return -1;
			}

			c = KeyEvent.KEY_PRESSED;
		}

		return -2;
	}
};
