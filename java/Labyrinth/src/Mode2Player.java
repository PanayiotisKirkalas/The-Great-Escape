import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;
import java.io.*;
import java.awt.event.*;

class Mode2Player extends Player {
	boolean dead;
	int points, lives;
	Mode2 m2;

//	Mode2Player MetSomeone(ArrayList<Mode2Player> v) {
//		for (Mode2Player p : v) {
//			if (this.pos == p.getPos() && this.id != p.getID()) {
//				System.out.print("\033[H\033[2J");//clear screen
//				System.out.println("You've met Player: " + p.getName());
//				new Scanner(System.in).nextLine();//pause
//				return p;
//			}
//		}
//		return null;
//	}
	public Mode2Player Battle(Mode2Player p) {
		int bet1, bet2;

		bet1 = Bet(this);
//		System.out.print("\033[H\033[2J");//clear screen
//		System.out.println(p.getName() + "'s turn");
//		System.out.print("Press enter to contiue...");
//		new Scanner(System.in).nextLine();//pause
		Main.ShowMessage(game, p.getName() + "'s turn");
		bet2 = Bet(p);
//		System.out.print("\033[H\033[2J");//clear screen

		switch (bet1 - bet2)
		{
		case 0:
			// draw
//			System.out.println("Draw");
//			System.out.println(p.getName() + "'s turn");
//			System.out.print("Press enter to contiue...");
//			new Scanner(System.in).nextLine();//pause
			Main.ShowMessage(game, "Draw!!!\n" + p.getName() + "'s turn");
	 		return p;
		default:
			String msg = new String();
			switch (Integer.compare(bet1, bet2))
			{
			case 1:
				// player 2 loses
//				System.out.println("Player " + p.getName() + " lost battle");
				msg = "Player " + p.getName() + " lost battle\n";
				if (p.LoseLife() <= 0) {
					m2.ConfirmDeath(p.getID());
//					System.out.print("Player " + p.getName() + " lost the game");
					msg += "Player " + p.getName() + " lost the game\n";
				}
				else {
					System.out.println("Loser's lifes: " + p.getLives());
				}
				msg += this.Name + "'s turn\n";
				Main.ShowMessage(game, msg);
//				System.out.println(this.Name + "'s turn");
//				System.out.print("Press enter to contiue...");
//				new Scanner(System.in).nextLine();//pause
				return null;
			default:
				// player 1 loses
				System.out.println("Player " + this.Name + " lost battle");
				msg = "Player " + this.Name + " lost battle\n";
				if (this.LoseLife() == 0) {
					m2.ConfirmDeath(p.getID());
//					System.out.print("Player " + p.getName() + " lost the game");
					msg += "Player " + this.getName() + " lost the game\n";
				}
				else {
					System.out.println("Loser's lifes: " + p.getLives());
				}
				msg += p.Name + "'s turn\n";
				Main.ShowMessage(game, msg);
				return p;
			}
		}
	}
	int Bet(Mode2Player p) {
		int bet = 0, points = p.getPoints();

//		System.out.print("\033[H\033[2J");//clear screen
//
//		System.out.println("Your points: " + points);
//		System.out.print("How many points will you bet?: ");
//		bet = new Scanner(System.in).nextInt();
		bet = Integer.parseInt(Main.AskUser(game, "Your points: " + points + "\nHow many points will you bet?: "));
		while (bet > points) {
//			System.out.print("\033[H\033[2J");//clear screen
//			System.out.println("Your points: " + points);
//			System.out.println("INVALID ANSWER, CAN'T BET MORE POINTS THAN YOU HAVE");
//			System.out.print("How many points you will bet?: ");
//			bet = new Scanner(System.in).nextInt();
			Main.ShowMessage(game, "INVALID ANSWER, CAN'T BET MORE POINTS THAN YOU HAVE");
			bet = Integer.parseInt(Main.AskUser(game, "Your points: " + points + "How many points you will bet?: "));
		}
		p.DecPoints(bet);
		return bet;
	}
	public Mode2Player() {
		super(-1, null);
		points = 0;
		lives = 1;
		dead = false;
		m2 = null;
	}
	public Mode2Player(int id, Mode2 mode) {
		super(id, mode.game);
		this.symbol = 'O';
		m2 = mode;
	}
	public void setPoints(int v) {
		this.points = v;
	}
	public int getPoints() {
		return this.points;
	}
	public int getLives() {
		return this.lives;
	}
	public void IncPoints(int value) {
		this.points += value;
	}
	public void DecPoints(int value) {
		this.points -= value;
	}
	public int LoseLife() {
		--(this.lives);
		this.dead = this.lives <= 0;

		return this.lives;
	}
};
