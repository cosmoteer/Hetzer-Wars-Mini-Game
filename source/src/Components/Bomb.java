package Components;

public class Bomb extends GameObjects{

	/**
	 * Constructs a bomb object
	 * @param s string name of the image passed to represent this object
	 * @param x the x coordinate of this object
	 * @param y the y coordinate of this object
	 */
	public Bomb(String s, int x, int y) {
		super (s ,x,y,20,20);
	}
}
