import java.awt.*;

public abstract class Entity {
	protected double x; // x coord
	protected double y; // y coord
	protected Sprite sprite; // graphic
	protected double dx; // horizontal speed (pixels/second) + = right
	protected double dy; // vertical speed (pixels/second) + = down
	protected Rectangle me = new Rectangle(); // bounding rectangle of this
	protected Rectangle them = new Rectangle(); // bounding rectangle of other
												// entity
	protected int damage; // damage inflicted on enemy by tower
	protected int ID; // ID of entity

	/*
	 * Constructor 
	 * input: reference to the image for this entity, 
	 * 		  initial x, y
	 * 		  location
	 */

	public Entity(String r, int newX, int newY) {
		x = newX;
		y = newY;

		// Gets image by checking string name of image 
		this.sprite = (SpriteStore.get()).getSprite(r);

	} // Entity

	/*
	 * move input: delta - the amount of time passed in ms purpose: after set
	 * amount of time, update the location of each sprite
	 */

	public void move(long delta) {
		x += (dx * delta) / 1000;
		y += (dy * delta) / 1000;

	} // move

	// get and set velocities
	public void setHorizontalMovement(double newDX) {
		dx = newDX;
	} // setHorizontalMovement

	public void setVerticalMovement(double newDY) {
		dy = newDY;
	} // setVerticalMovement

	public double getHorizontalMovement() {
		return dx;
	} // getHorizontalMovement

	public double getVerticalMovement() {
		return dy;
	} // getVerticalMovement

	// get methods for x and y
	public int getX() {
		return (int) x;
	} // getX

	public int getY() {
		return (int) y;
	} // getX

	// get method for damage
	public int getDamage() {
		return damage;
	} // getDamage

	// get method for ID
	public int getID() {
		return ID;
	} // getID

	// Draw this entity to the graphics object at (x,y)
	public void draw(Graphics g) {
		sprite.draw(g, (int) x, (int) y);
	} // draw

	/*
	 * collidesWith input: other entity to check collision against output: true
	 * if entities collide] purpose: check if entity collides w/ another
	 */

	public boolean collidesWith(Entity other) {
		me.setBounds((int) x - Game.tileDimension + 15, (int) y - Game.tileDimension + 15,
				(int) (sprite.getWidth() * 2.5), (int) (sprite.getHeight() * 2.5));
		them.setBounds(other.getX(), other.getY(), other.sprite.getWidth(), other.sprite.getHeight());
		return me.intersects(them);
	} // collidesWith

	/*
	 * collidedWith input: the entity with which it has collided purpose:
	 * notification that a collision has occured
	 */

	public abstract void collidedWith(Entity other);

} // Entity
