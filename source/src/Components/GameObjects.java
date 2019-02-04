package Components;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.util.ArrayList;

import javax.swing.*;


public class GameObjects extends Rectangle2D.Double{

	// FIELDS
	private Image image;
	private double dir;
	Point2D c;
	private boolean isVisible;


	/**
	 * Constructs a GameObject object
	 * @param filename string name of the image passed to represent this object
	 * @param x the x coordinate of this object
	 * @param y the y coordinate of this object
	 * @param w the width coordinate of this object
	 * @param h the height coordinate of this object
	 */
	public GameObjects(String filename, double x, double y, double w, double h) {
		this((new ImageIcon(filename)).getImage(),x,y,w,h);
	}

	/**
	 * Contructs a GameObject object
	 * @param filename image passed in to represent this object
	 * @param x the x coordinate of this object
	 * @param y the y coordinate of this object
	 * @param w the width coordinate of this object
	 * @param h the height coordinate of this object
	 */
	public GameObjects(Image img, double x, double y, double w, double h) {
		super(x,y,w,h);
		image = img;
		dir = 0;
		isVisible = true;
	}


	/**
	 * Moves object to the location passed in
	 * @param x new x coordinate of this object
	 * @param y new y coordinate of this object
	 */
	public void moveToLocation(double x, double y) {
		super.x = x;
		super.y = y;
	}

	/**
	 * Moves each axis of the object by the corresponding amounts passed in
	 * @param x adds x-value the current x coordinate
	 * @param y adds y-value the current y coordinate
	 */
	public void moveByAmount(double x, double y) {
		super.x += x;
		super.y += y;
	}

	/**
	 * Applied the windows limits to the object's x and y coordinate
	 * @param windowWidth maximum width of window
	 * @param windowHeight maximum height of window
	 */
	public void applyWindowLimits(int windowWidth, int windowHeight) {
		x = Math.min(x,windowWidth-width);
		y = Math.min(y,windowHeight-height);
		x = Math.max(0,x);
		y = Math.max(0,y);
	}

	/**
	 * Draws GameObject
	 * @param g Graphics
	 * @param io ImageOberver
	 */
	public void draw(Graphics g, ImageObserver io) {
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform at = g2.getTransform();
		g2.translate(x+width/2, y+height/2);
		g2.rotate(-dir);
		g.drawImage(image,(int)(-width/2),(int)(-height/2),(int)width,(int)height,io);
		g2.setTransform(at);
		c = new Point2D.Double( this.getCenterX(), this.getCenterY()); //this.getX/getY

	}
	
	

	/**
	 * Turns object to direction passed in
	 * @precondition value is a double
	 * @param dir new direction
	 * @post value of dir changes
	 */
	public void turn(double dir) {
		this.dir = dir;
	}

	/**
	 * Turns object by amounts passed in
	 * @param x new direction in x component
	 * @param y new direction in y component
	 * @post value of dir changes
	 */
	public void turnToward(int x, int y) {
		dir = Math.atan(((double)this.y-y)/(this.x-x));
		if (this.x > x)
			dir += Math.PI;
	}

	/**
	 * Retruns direction of the object
	 * @return direction of object
	 */
	public double getDirection() {
		return dir;
	}


	/**
	 * Find the x coordinate in front of the turret
	 * @return x coordinate in front of turret
	 */
	public double getDirX()
	{
		return c.getX() + width/1.5 * Math.cos(-dir) - height/2.5 * Math.sin(-dir);
		//System.out.println(-dir);
		//return c.getX() + 30*Math.sin(-dir);
	}

	/**
	 * Finds the y coordinate in front of the turret
	 * @return y coordinate in front of turret
	 */
	public double getDirY()
	{
		return c.getY() + width/1.5 * Math.sin(-dir) + height/2.5 * Math.cos(-dir);
		//return c.getY() - 30*Math.cos(dir);
	}


	/**
	 * Sets whether object can be seen or not
	 * @param b boolean if object is visible or not
	 */
	public void setVisible(boolean b) {
		isVisible = b;
	}

	/**
	 * Returns the visibility of the object
	 * @return isVisible boolean visiblity of object
	 */
	public boolean isVisible()
	{
		return isVisible;
	}

	//zero points right, goes counterclockwise

	/**
	 * If this collides with arraylist of GameObjects passed in
	 * @param m ArrayList of GameObjects
	 * @return boolean whether an GameObject collides with this object
	 */
	public boolean collide(ArrayList<GameObjects> i)
	{ 	
		for (GameObjects ob : i)
		{
			if(ob.intersects(this)){ 
				return true;
			}
		}
		return false;
	}
	/**
	 * If this collides with GameObject passed in
	 * @precondition parameter is not null
	 * @param m GameObject to check collision for
	 * @return boolean collides or not
	 */
	public boolean collide(GameObjects m)
	{ 	
		if(new Ellipse2D.Double(this.x, this.y, this.width, this.height).intersects(m)){
			return true;
		}
		return false;
	}
	
	/**
	 * If this collides with bullet passed in
	 * @precondition parameter is not null
	 * @param m GameObject to check collision for
	 * @return boolean collides or not
	 */
	public boolean collideBul(GameObjects m)
	{ 	
		if(new Rectangle.Double(this.x, this.y, this.width-5, this.height-5).intersects(m)){
			return true;
		}
		return false;
	}

}










