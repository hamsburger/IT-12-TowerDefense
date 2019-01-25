import java.util.Scanner;


public class Interface extends Entity{
	private Game game; // pass entire game into class
	
	/* constructor
	 * input: game - the game in which the tile is created
	 * ref - a string with the name of the image
	 * 		 the sprite for the tile
	 * 
	 * x,y - location of tile
	 */
	public Interface (Game g, String r, int newX, int newY){
	    super(r, newX, newY); 
	    this.game = g;
	    
	} // Tiles
	
	/* Determines which tile to print in background according to placement.txt*/
	public static int getID(Scanner scan){
		
		int temporary = 0;
		if (scan.hasNext()){
			temporary = scan.nextInt();
		} else {
			scan.nextLine();
			temporary = scan.nextInt();
		} // if
		
		return temporary;
		
	} // getID
	

	@Override
	public void collidedWith(Entity other) {
		// TODO Auto-generated method stub
	}
}
