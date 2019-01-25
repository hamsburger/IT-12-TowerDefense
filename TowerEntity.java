import java.awt.Color;
import java.awt.Graphics;

public class TowerEntity extends Entity {
	private Game game; // allows access to game variables

	/*
	 * Constructor input: game - the game the tower is in 
	 * ref - a string with the name of the image associated to the sprite for tower 
	 * x,y - location of tower
	 * location of monster towerType = indicated type of tower
	 */
	public TowerEntity(Game g, String r, int newX, int newY, int towerType) {
		super(r, newX, newY);
		switch (towerType) {
		case 1:
			damage = 1;
			break;

		case 2:
			damage = 3;
			break;

		case 3:
			damage = 5;
			break;

		default:
			break;

		} // switch

		this.game = g;
		ID = towerType;

	} // TowerEntity

	/* Draw range and sprite of tower */
	public void draw(Graphics g) {
		super.draw(g);
		g.setColor(Color.YELLOW);
		g.drawOval(getX() - 60, getY() - 60, 180, 180);

	} // draw

	/* Collision code done in EnemyEntity */
	public void collidedWith(Entity other) {

	} // collidedWith

} // TowerEntity
