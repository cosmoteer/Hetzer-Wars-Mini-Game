package Components;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Wall extends GameObjects{

	/**
	 * Constructs a wall object
	 * @param x the x coordinate of this object
	 * @param y the y coordinate of this object
	 * @param w the width coordinate of this object
	 * @param h the height coordinate of this object
	 */
	public Wall(int x, int y, int width, int height) {
		super ("wall.png",x,y,width, height);
		
	}

	
}