import java.awt.Color;
import java.awt.Graphics;


public class EnemyEntity extends Entity {
	private Game game; // allows access to game variables

	// attributes of enemy entity
	private double moveSpeed = 200;
	private int health;
	private int maxHealth;
	private int monsterID;

	private int healthSpace = 3, healthHeight = 6; // width and length of health
													// bar

	/*
	 * Constructor 
	 * input: game - the game the monster is in 
	 * ref - a string with the name of the image associated to the sprite for monster 
	 * x,y - initial location of monster 
	 * monsterID = indicated type of monster
	 */

	public EnemyEntity(Game g, String r, int newX, int newY, int monsterID) {
		super(r, newX, newY);
		this.game = g;
		this.monsterID = monsterID;

		switch (monsterID) {
		case 1:
			moveSpeed = 100;
			maxHealth = 280;
			health = 280;
			break;

		case 2:
			moveSpeed = 200;
			maxHealth = 480;
			health = 480;
			break;

		case 3:
			moveSpeed = 400;
			maxHealth = 560;
			health = 560;
			break;

		default:
			moveSpeed = 200;
			maxHealth = 480;
			health = 480;
			break;

		} // switch

		dx = 0;
		dy = moveSpeed;

	} // EnemyEntity

	/*
	 * move 
	 * input - delta time elapsed since last move (ms) 
	 * purpose - move monsters
	 */
	public void move(long delta) {

		if (x == 120 && y >= 600 && y <= 610) {
			goRight();
		} else if (x >= 300 && x <= 310 && y >= 600 && y <= 610) {
			goUp();
		} else if (x >= 300 && x <= 310 && y >= 112 && y <= 122) {
			goRight();
		} else if (x >= 780 && x <= 790 && y >= 112 && y <= 122) {
			goDown();
		} else if (x >= 780 && x <= 790 && y >= 240 && y <= 250) {
			goLeft();
		} else if (x >= 412 && x <= 422 && y >= 240 && y <= 250) {
			goDown();
		} else if (x >= 412 && x <= 422 && y >= 600 && y <= 610) {
			goRight();
		} else if (x >= 600 && x <= 610 && y >= 600 && y <= 610) {
			goUp();
		} else if (x >= 600 && x <= 610 && y >= 413 && y <= 423) {
			goRight();
		} else if (x >= 840 && x <= 850 && y >= 413 && y <= 423) {
			goDown();
		}

		// player loses life every time a monster reaches the end of path
		if (y >= game.screenHeight) {
			game.removeEntity(this);
			game.earnCoins = false;
			game.life -= 1;

			// game over if player loses all lives
			if (game.life < 1) {
				game.notifyDeath();
			} // if

		} // if

		super.move(delta);

	} // move

	public void goLeft() {
		dx = -moveSpeed;
		dy = 0;
	} // goLeft

	public void goRight() {
		dx = moveSpeed;
		dy = 0;
	} // goRight

	public void goUp() {
		dx = 0;
		dy = -moveSpeed;
	} // goUp

	public void goDown() {
		dx = 0;
		dy = moveSpeed;
	} // goDown

	/* Deplete monster health and remove it when health reaches 0 */
	public void loseHealth(int amount) {
		health -= amount;

		if (health <= 0) {
			game.removeEntity(this);
			game.earnCoins = true;
			ID = this.monsterID;
		} // if

	} // loseHealth

	/* Draws sprite and health bar */
	public void draw(Graphics g) {
		super.draw(g);

		g.setColor(Color.red);
		g.fillRect(getX(), getY() - healthSpace - healthHeight, 60, healthHeight);

		g.setColor(Color.green);
		g.fillRect(getX(), getY() - healthSpace - healthHeight, 60 * health / maxHealth, healthHeight);

		g.setColor(Color.black);
		g.drawRect(getX(), getY() - healthSpace - healthHeight, Game.tileDimension, healthHeight);

	} // draw

	/*
	 * collidedWith input: the entity with which "this" has collided purpose:
	 * notification that hits entity collided with another "this" starts losing
	 * health after collision
	 */
	public void collidedWith(Entity other) {
		if (other instanceof TowerEntity)
			loseHealth(game.healthDepletion);
	} // collidedWith

} // EnemyEntity
