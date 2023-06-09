class Mode2Player extends Player {
	protected static final int PLAYER_LIVES = 2;
	
	boolean dead = false;
	int points = 0, lives = PLAYER_LIVES;
	Mode2 m2;
	
	public Mode2Player Battle(Mode2Player p) {
		int bet1, bet2;

		bet1 = Bet(this);
		Main.ShowMessage(game, p.getName() + "'s turn");
		bet2 = Bet(p);

		switch (bet1 - bet2)
		{
		case 0:
			// draw
			Main.ShowMessage(game, "Draw!!!\n" + p.getName() + "'s turn");
	 		return p;
		default:
			String msg = new String();
			switch (Integer.compare(bet1, bet2))
			{
			case 1:
				// player 2 loses
				msg = "Player " + p.getName() + " lost battle\n";
				p.LoseLife();
				if (p.dead) {
					msg += "Player " + p.getName() + " lost the game\n";
				}
				msg += this.Name + "'s turn\n";
				Main.ShowMessage(game, msg);
				return null;
			default:
				// player 1 loses
				System.out.println("Player " + this.Name + " lost battle");
				msg = "Player " + this.Name + " lost battle\n";
				this.LoseLife();
				if (this.dead) {
					msg += "Player " + this.getName() + " lost the game\n";
				}
				msg += p.Name + "'s turn\n";
				Main.ShowMessage(game, msg);
				return p;
			}
		}
	}
	int Bet(Mode2Player p) {
		int bet = 0, points = p.getPoints();
		
		bet = Integer.parseInt(Main.AskUser(game, "Your points: " + points + "\nHow many points will you bet?: "));
		while (bet > points) {
			Main.ShowMessage(game, "INVALID ANSWER, CAN'T BET MORE POINTS THAN YOU HAVE");
			bet = Integer.parseInt(Main.AskUser(game, "Your points: " + points + "How many points you will bet?: "));
		}
		p.DecPoints(bet);
		return bet;
	}
	public Mode2Player() {
		super(-1, null);
		m2 = null;
	}
	public Mode2Player(int id, Mode2 mode) {
		super(id, mode.game);
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
		if (this.dead = (this.lives <= 0)) {
			m2.ConfirmDeath(this);
		}

		return this.lives;
	}
};
