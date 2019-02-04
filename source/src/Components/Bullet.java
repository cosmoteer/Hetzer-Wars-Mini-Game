package Components;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Bullet extends GameObjects{
	
	private boolean moving;
	private double direction;
	private int speed;
	private int x, y;
	private boolean collide;
	
	
	/**
	 * Constructs a Bullet object
	 * @param x the x coordinate of this object
	 * @param y the y coordinate of this object
	 * @param dir direction bullet is facing
	 */
	public Bullet(int x, int y, double dir) {
		super ("bullet.png",x,y,10,7);
		direction = dir;
		moving = true;
		speed = 4;
		this.x=x;
		this.y=y;
		collide = false;
	}
	
	/**
	 * Sets whether bullet can continue forward or not
	 * @param boolean whether bullet can move or not
	 */
	public void moveForward(boolean moving) {
		this.moving = moving;
		
	}
	
	/**
	 * How the Bullet object acts
	 * @postcondition changes bullet's location and/or direction
	 */
	public void act(ArrayList<GameObjects> m) {
		
		double xCoord = getX();
		double yCoord = getY();
		double width = getWidth();
		double height = getHeight();


		double yCoord2 = yCoord + speed;

		Rectangle2D.Double strechY = new Rectangle2D.Double(xCoord,Math.min(yCoord,yCoord2),width,height+Math.abs(speed));


		if (speed > 0) {
			Shape standingSurface = null;
			for (Shape s : m ) {
				if (s.intersects(strechY)) {
					standingSurface = s;
					speed = 0;
					collide = true;
				}
			}
			if (standingSurface != null) {
				Rectangle r = standingSurface.getBounds();
				yCoord2 = r.getY()-height;
			}
		} else if (speed < 0) {
			Shape headSurface = null;
			for (Shape s : m) {
				if (s.intersects(strechY)) {
					headSurface = s;
					speed = 0;
					collide = true;
				}
			}
			if (headSurface != null) {
				Rectangle r = headSurface.getBounds();
				yCoord2 = r.getY()+r.getHeight();
			}
		}

		if (Math.abs(speed) < .5)
			speed = 0;

		
		if (moving){
			moveByAmount(speed*Math.cos(-direction),speed*Math.sin(-direction));
			turn(direction);
		}
	}
	
	/**
	 * Gets speed of the bullet
	 * @return speed of bullet
	 */
	public int getSpeed()
	{
		return speed;
	}
	
	/**
	 * Getter for if bullet object collides
	 * @return collision
	 */
	public boolean collide()
	{
		return collide;
	}

	
	
}
