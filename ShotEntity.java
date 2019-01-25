
public class ShotEntity extends Entity{
private Game game;
private int moveSpeed = 1000;

	public ShotEntity(Game game, String r, int newX, int newY, Entity other){
		super(r, newX, newY);
		this.game = game;
		dy = moveSpeed;
		dx = moveSpeed;
	} // ShotEntity
	
	public void move(long delta){
		super.move(delta);
	} // move

	@Override
	public void collidedWith(Entity other) {
		if (other instanceof EnemyEntity){
			game.removeEntity(this);
		} // if
		
	} // collidedWith
	
} // shotEntity
