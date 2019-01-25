import java.awt.Graphics;
import java.awt.Image;

public class Sprite {
	private Image image; // this image to draw for this sprite
	
	public Sprite (Image i) {
		image = i;
	} // constructor
	
	public int getWidth() {
		return image.getWidth(null);
	} // getWidth
	
	public int getHeight() {
		return image.getHeight(null);
	} // getHeight
	
	// draw the sprite in the graphics object g at location (x,y)
	public void draw(Graphics g, int x, int y) {
		g.drawImage(image,  x,  y,  null);
	} // draw
	

} // Sprite
