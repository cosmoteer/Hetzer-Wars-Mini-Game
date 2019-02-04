package Components;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.*;

public class Tank extends GameObjects {

	public static final int TANK_WIDTH = 50;
	public static final int TANK_HEIGHT = 30;
	public int speed;
	private boolean movingForward;
	private boolean movingBackward;
	private boolean turnRight;
	private boolean turnLeft;
	private double health;
	private int numWins;

	/**
	 * Constructs a Tank object
	 * @param i  string name of the image passed to represent this object
	 * @param x the x coordinate of this object
	 * @param y the y coordinate of this object
	 */
	public Tank(String i, int x, int y) {
		super (i,x,y,TANK_WIDTH,TANK_HEIGHT);
		movingForward = false;
		movingBackward = false;
		speed = 2;
		turnLeft = false;
		turnRight = false;
		health = 100;
		numWins = 0;
	}
	
	/**
	 * Sets whether tank can continue forward or not
	 * @param moving whether tank can move forward
	 * @post changes moveForward
	 */
	public void moveForward(boolean moving) {
		this.movingForward = moving;
	}

	/**
	 * Sets whether tank can continue backward or not
	 * @param moving whether tank can move backward
	 * @post changes moveBackward
	 */
	public void moveBackward(boolean moving) {
		this.movingBackward = moving;
	}

	/**
	 * Sets whether tank can turn right or not
	 * @param moving whether tank can turn right
	 * @post changes turnRight
	 */
	public void turnRight(boolean turn) {
		this.turnRight = turn;
	}

	/**
	 * Sets whether tank can turn left or not
	 * @param moving whether tank can turn left
	 * @post changes turnLeft
	 */
	public void turnLeft(boolean turn) {
		this.turnLeft = turn;
	}

	/**
	 * Get width
	 * @return width
	 */
	public double getWidth(){
		return TANK_WIDTH;
	}

	/**
	 * Get height
	 * @return height
	 */
	public double getHeight(){
		return TANK_HEIGHT;
	}

	/**
	 * Changes health by amount passed in
	 * @param amount amount to change health
	 * @post changes health
	 */
	public void changeHealth(int amount){
		health += amount;
	}

	/**
	 * Sets health to amount passed in
	 * @param h changes health to this amount
	 * @post health changes
	 */
	public void setHealth(int h){
		health = h;
	}	
	
	/**
	 * Gets health
	 * @return health
	 */
	public double getHealth(){
		return health;
	}
	
	/**
	 * Adds 1 to the number of wins
	 * @postconditions changes amount of numWins by 1
	 */
	public void addWin(){
		numWins++;
	}
	
	/**
	 * Gets number of wins
	 * @return number of wins
	 */
	public int getWins(){
		return numWins;
	}
	
	/**
	 * How the Tank object acts
	 * @param m Array of MovingImages
	 * @param t Tank object
	 */
	public void act(ArrayList<GameObjects> m, Tank t) {
		double dir = getDirection();	

		double xCoord = getX();
		double yCoord = getY();
		double width = getWidth();
		double height = getHeight();
		
		double vx = 0;
		double vy = 0;
		
		
		if (turnLeft && turnRight){

		} else { 
			if(turnLeft && (!movingForward) && (turnLeft && (!movingBackward))){
				turn(dir+0.03);
			}
			if(turnRight && (!movingForward)  && (turnRight && (!movingBackward)))
				turn(dir-0.03);
		}
		
		if (movingForward && movingBackward){

		} else { 
			if (movingForward) {
				 vx = speed*Math.cos(-dir);
				 vy = speed*Math.sin(-dir);
			} else if (movingBackward) {
				vx = -speed*Math.cos(-dir);
				 vy = -speed*Math.sin(-dir);
			}
		}

		
		//Y
		double yCoord2 = yCoord + vy;
		Rectangle2D.Double strechY = new Rectangle2D.Double(xCoord,Math.min(yCoord,yCoord2),width,height+Math.abs(vy));

		if (vy > 0) {
			Shape bottomSect = null;
			for (Shape s : m ) {
				if (s.intersects(strechY)) {
					bottomSect = s;
					vy = 0;
				}
			}
			if (t.intersects(strechY)) {
				bottomSect = t;
				vy = 0;
			}
			if (bottomSect != null) {
				Rectangle r = bottomSect.getBounds();
				yCoord2 = r.getY()-height;
			}
		} else if (vy < 0) {
			Shape topSect = null;
			for (Shape s : m) {
				if (s.intersects(strechY)) {
					topSect = s;
					vy = 0;
				}
			}
			if (t.intersects(strechY)) {
				topSect = t;
				vy = 0;
			}
			if (topSect != null) {
				Rectangle r = topSect.getBounds();
				yCoord2 = r.getY()+r.getHeight();
			}
		}

		if (Math.abs(vy) < .5)
			vy = 0;
		
		
		//X
		double xCoord2 = xCoord + vx;
		Rectangle2D.Double strechX = new Rectangle2D.Double(Math.min(xCoord,xCoord2),yCoord2,width+Math.abs(vx),height);
		
		if (vx > 0) {
			Shape rightSurface = null;
			for (Shape s : m) {
				if (s.intersects(strechX)) {
					rightSurface = s;
					vx = 0;
				}
			}
			if (t.intersects(strechX)) {
				rightSurface = t;
				vx = 0;
			}
			if (rightSurface != null) {
				Rectangle r = rightSurface.getBounds();
				xCoord2 = r.getX()-width;
			}
		} else if (vx < 0) {
			Shape leftSurface = null;
			for (Shape s : m) {
				if (s.intersects(strechX)) {
					leftSurface = s;
					vx = 0;
				}
			}
			if (t.intersects(strechX)) {
				leftSurface = t;
				vx = 0;
			}
			if (leftSurface != null) {
				Rectangle r = leftSurface.getBounds();
				xCoord2 = r.getX()+r.getWidth();
			}
		}

		if (Math.abs(vx) < .5)
			vx = 0;


		moveByAmount(vx,vy);

	}

	/**
	 * Checks if tank collides with bullet
	 * @param b Bullet object
	 * @return true if tank collides with bullet, false if not
	 */
	public boolean collide(Bullet b)
	{ 	
		if(new Ellipse2D.Double(this.x, this.y, this.width, this.height-20).intersects(b)){
			return true;
		}
		return false;
	}
	
	
}
