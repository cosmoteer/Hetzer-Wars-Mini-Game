package GUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import Components.Bomb;
import Components.Bullet;
import Components.GameObjects;
import Components.PowerUp;
import Components.Sound;
import Components.Tank;
import Components.Wall;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class GamePanel extends JPanel implements KeyListener, Runnable
{
	public static final int DRAWING_WIDTH = 800;
	public static final int DRAWING_HEIGHT = 600;

	Main w;
	private Tank tank1;
	private Tank tank2;
	int currentlevel;
	private Thread runThread;
	private ArrayList<GameObjects> walls;
	private ArrayList<Bullet> bullets;
	private ArrayList<PowerUp> pu;
	private ArrayList<Bomb> bomb;
	boolean levelfinished;
	boolean onlyOneLevel;

	/**
	 * Creates the JPanel utilizes for all the levels of the game
	 * @param w instance of main class
	 */
	public GamePanel (Main w) {
		super();
		this.w = w;

		tank1 = new Tank("tankRed.png", 730,280);
		tank2 = new Tank("tankGreen.png",30,280);

		pu = new ArrayList<PowerUp>();
		bomb = new ArrayList<Bomb>();
		bullets = new ArrayList<Bullet>();
		walls = new ArrayList<GameObjects>();

		levelfinished = true;
		currentlevel = 1;
		reset(1);

		runThread = new Thread(this);
		onlyOneLevel = false;
		Sound.backM.loop();

	}


	/**
	 * Begins the runThread, hence starting the game
	 */
	public void startTheGame(){
		runThread.start();
	}

	/**
	 * Draws the Game Screen
	 * @param g graphics object utilized for drawing
	 */
	public void paintComponent(Graphics g){

		super.paintComponent(g); 

		Graphics2D g2 = (Graphics2D)g;

		int width = getWidth();
		int height = getHeight();

		double ratioX = (double)width/DRAWING_WIDTH;
		double ratioY = (double)height/DRAWING_HEIGHT;

		AffineTransform at = g2.getTransform();
		g2.scale(ratioX, ratioY);

		for (Bullet bl : bullets)
			if(bl.isVisible())
				bl.draw(g2, this);

		for (PowerUp p : pu)
			if(p.isVisible())
				p.draw(g, this);

		for (Bomb b : bomb)
			if(b.isVisible())
				b.draw(g, this);


		for (GameObjects wl : walls)
			wl.draw(g, this);

		tank1.draw(g, this);
		tank2.draw(g, this);

		g.drawRoundRect(99, 40, 100, 19, 10, 10);
		g.drawString("Health", 50, 55);
		g.setColor(Color.WHITE);
		g.fillRoundRect(101, 41, 98, 18, 10, 10);
		g.setColor(new Color(34,139,34));
		g.fillRoundRect(99, 40, (int)tank2.getHealth(), 19, 10, 10);

		g.setColor(Color.BLACK);
		g.drawRoundRect(650, 40, 100, 19, 10, 10);
		g.drawString("Health", 600, 55);
		g.setColor(Color.WHITE);
		g.fillRoundRect(651, 41, 99, 18, 10, 10);
		g.setColor(Color.RED);
		g.fillRoundRect(650, 40, (int)tank1.getHealth(), 19, 10, 10);

		g2.setTransform(at);
	}

	/**
	 * Determines whether the game is one level, depending on what a player selects on the Menu Screen
	 * @param x variable representing if the game is one level or not
	 */
	public void isOnlyOneLevel(boolean x){
		onlyOneLevel = x;
	}

	/**
	 * Sets the current level to one passed in
	 * @param lev int level to set current level to
	 */
	public void setCurrentLevel(int lev){
		currentlevel = lev;
	}

	/**
	 * Controls the motion of all GameObjects in the Game Panel while the player is going through the levels
	 */
	public void run() {
		if (!onlyOneLevel){
			while (currentlevel < 4){
				while (!levelfinished) { 
					long startTime = System.currentTimeMillis();

					tank1.act(walls, tank2);
					tank2.act(walls, tank1);


					for (PowerUp p : pu){
						if(tank1.collide(p)){
							Sound.health.play();
							p.setVisible(false);
							pu.remove(p);
							if(tank1.getHealth() > 80)
								tank1.changeHealth(100-(int)tank1.getHealth());
							else tank1.changeHealth(20);
							break;
						}
						if(tank2.collide(p)){
							Sound.health.play();
							p.setVisible(false);
							pu.remove(p);
							if(tank2.getHealth() > 80)
								tank2.changeHealth(100-(int)tank2.getHealth());
							else tank2.changeHealth(20);
							break;
						}
					}
					
					for (Bomb b: bomb){
						if(tank1.collide(b)){
							Sound.bomb.play();
							b.setVisible(false);
							bomb.remove(b);
							if(tank1.getHealth() < 40)
								tank1.setHealth(0);
							else tank1.changeHealth(-40);
							break;
						}
						if(tank2.collide(b)){
							Sound.bomb.play();
							b.setVisible(false);
							bomb.remove(b);
							if(tank2.getHealth() < 40)
								tank2.setHealth(0);
							else tank2.changeHealth(-40);
							break;
						}
					}



					for (int i = 0; i < bullets.size(); i++){
						Bullet b = bullets.get(i);
						for (int j=i+1; j < bullets.size(); j++){
							if(b.collideBul(bullets.get(j)))
							{
								bullets.remove(bullets.get(j));
								bullets.remove(bullets.get(i));			
							}
						}
						b.act(walls);
						if(b.collide())
						{
							bullets.remove(b);
							b.setVisible(false);			
						}

						if (tank1.collide(b)) {
							Sound.boom.play();
							bullets.remove(b);
							tank1.changeHealth(-20);
							break;
						}
						else if (tank2.collide(b)) {
							Sound.boom.play();
							bullets.remove(b);
							tank2.changeHealth(-20);
							break;				
						}
						


					}

					repaint();

					if (tank1.getHealth() == 0 || tank2.getHealth() == 0){
						levelfinished = true;
						currentlevel++;
						if (tank1.getHealth() == 0) 
							tank2.addWin();
						else
							tank1.addWin();
					}

					long waitTime = 17 - (System.currentTimeMillis()-startTime);
					try {
						if (waitTime > 0)
							Thread.sleep(waitTime);
						else
							Thread.yield();
					} catch (InterruptedException e) {}
				}
				reset(currentlevel);
				repaint();
			}
			w.changePanel("3");
			w.getWinnerScreen().setTankWins(1,tank1.getWins());
			w.getWinnerScreen().setTankWins(2,tank2.getWins());
		} else {

			reset(currentlevel);
			repaint();

			while (!levelfinished) { 
				long startTime = System.currentTimeMillis();

				tank1.act(walls, tank2);
				tank2.act(walls, tank1);

				for (PowerUp p : pu){
					if(tank1.collide(p)){
						Sound.health.play();
						p.setVisible(false);
						pu.remove(p);
						if(tank1.getHealth() > 80)
							tank1.changeHealth(100-(int)tank1.getHealth());
						else tank1.changeHealth(20);
						break;
					}
					if(tank2.collide(p)){
						Sound.health.play();
						p.setVisible(false);
						pu.remove(p);
						if(tank2.getHealth() > 80)
							tank2.changeHealth(100-(int)tank2.getHealth());
						else tank2.changeHealth(20);
						break;
					}
				}
				
				for (Bomb b: bomb){
					if(tank1.collide(b)){
						Sound.bomb.play();
						b.setVisible(false);
						bomb.remove(b);
						if(tank1.getHealth() < 40)
							tank1.setHealth(0);
						else tank1.changeHealth(-40);
						break;
					}
					if(tank2.collide(b)){
						Sound.bomb.play();
						b.setVisible(false);
						bomb.remove(b);
						if(tank2.getHealth() < 40)
							tank2.setHealth(0);
						else tank2.changeHealth(-40);
						break;
					}
				}




				for (int i = 0; i < bullets.size(); i++){
					Bullet b = bullets.get(i);
					for (int j=i+1; j < bullets.size(); j++){
						if(b.collideBul(bullets.get(j)))
						{
							bullets.remove(bullets.get(j));
							bullets.remove(bullets.get(i));			
						}
					}
					b.act(walls);
					if(b.collide())
					{
						bullets.remove(b);
						b.setVisible(false);			
					}

					if (tank1.collide(b)) {
						Sound.boom.play();
						bullets.remove(b);
						tank1.changeHealth(-20);
						break;
					}
					else if (tank2.collide(b)) {
						Sound.boom.play();
						bullets.remove(b);
						tank2.changeHealth(-20);
						break;				
					}
					
					

				}

				repaint();

				if (tank1.getHealth() == 0 || tank2.getHealth() == 0){
					levelfinished = true;
					if (tank1.getHealth() == 0) 
						tank2.addWin();
					else
						tank1.addWin();
				}

				long waitTime = 17 - (System.currentTimeMillis()-startTime);
				try {
					if (waitTime > 0)
						Thread.sleep(waitTime);
					else
						Thread.yield();
				} catch (InterruptedException e) {}
			}
			w.changePanel("3");
			w.getWinnerScreen().setTankWins(1,tank1.getWins());
			w.getWinnerScreen().setTankWins(2,tank2.getWins());
		}

	}

	/**
	 * Determines and returns the number of wins by tank1
	 * @return number of wins acquired by tank1
	 */
	public int tank1Wins(){
		return tank1.getWins();
	}

	/**
	 * Determines and returns the number of wins by tank2
	 * @return number of wins acquired by tank2
	 */
	public int tank2Wins(){
		return tank2.getWins();
	}

	/**
	 * Moves or creates MovingImage objects on the screen depending on the level number chosen
	 * @param level the level number
	 */
	public void reset(int level){

		if (level == 1){
			tank2.moveToLocation(30,280);
			tank1.moveToLocation(720,280);
			tank1.turn(-Math.PI);
			tank2.turn(0);
			tank1.setHealth(100);
			tank2.setHealth(100);

			for (int i = walls.size()-1; i >= 0; i--){
				walls.remove(i);
			}
			walls.add(new Wall(0,0,800,10));
			walls.add(new Wall(0,590,800,10));
			walls.add(new Wall(0,0,10,600));
			walls.add(new Wall(790,0,10,600));
			walls.add(new Wall(395,0,10,100));
			walls.add(new Wall(395,167,10,100));
			walls.add(new Wall(395,333,10,100));
			walls.add(new Wall(395,500,10,100));
			walls.add(new Wall(150,100,10,400));
			walls.add(new Wall(650,100,10,400));

			for (int i = bullets.size()-1; i >= 0; i--){
				bullets.remove(i);
			}

			for (int i = pu.size()-1; i >= 0; i--){
				pu.remove(i);
			}			

			for (int i = bomb.size()-1; i >= 0; i--){
				bomb.remove(i);
			}
			pu.add(new PowerUp("heart.png", 390, 290));
			pu.add(new PowerUp("heart.png", 100, 100));
			pu.add(new PowerUp("heart.png", 690, 480));
			
			bomb.add(new Bomb("bomb.png", 270, 290));
			bomb.add(new Bomb("bomb.png", 520, 290));

			levelfinished = false;

		}

		if (level == 2){
			tank1.moveToLocation(710, 60);
			tank2.moveToLocation(70, 530);
			tank2.turn(-Math.PI);
			tank1.turn(-Math.PI);
			tank1.setHealth(100);
			tank2.setHealth(100);
			for (int i = walls.size()-1; i >= 0; i--){
				walls.remove(i);
			}
			walls.add(new Wall(0,0,800,10));
			walls.add(new Wall(0,590,800,10));
			walls.add(new Wall(0,0,10,600));
			walls.add(new Wall(790,0,10,600));
			walls.add(new Wall(70,500,80,10));
			walls.add(new Wall(70,166, 10,335));
			walls.add(new Wall(150,500,10,100));
			walls.add(new Wall(400,0, 10,400));
			walls.add(new Wall(160,400,250,10));
			walls.add(new Wall(400,500,10,100));
			walls.add(new Wall(400,340,300 ,10));
			walls.add(new Wall(480,150,320 ,10));

			for (int i = bullets.size()-1; i >= 0; i--){
				bullets.remove(i);
			}

			for (int i = pu.size()-1; i >= 0; i--){
				pu.remove(i);
			}
			
			for (int i = bomb.size()-1; i >= 0; i--){
				bomb.remove(i);
			}

			pu.add(new PowerUp("heart.png", 420, 300));
			pu.add(new PowerUp("heart.png", 100, 100));
			
			bomb.add(new Bomb("bomb.png", 90, 470));
			bomb.add(new Bomb("bomb.png", 395, 470));
			bomb.add(new Bomb("bomb.png", 420, 370));


			levelfinished = false;
		}

		if (level == 3){
			tank1.moveToLocation(530, 280);
			tank2.moveToLocation(30, 280);
			tank1.turn(-Math.PI);
			tank2.turn(0);
			tank1.setHealth(100);
			tank2.setHealth(100);

			for (int i = walls.size()-1; i >= 0; i--){
				walls.remove(i);
			}
			walls.add(new Wall(0,0,800,10));
			walls.add(new Wall(0,590,800,10));
			walls.add(new Wall(0,0,10,600));
			walls.add(new Wall(790,0,10,600));
			walls.add(new Wall(100,100,600,10));
			walls.add(new Wall(100,490,600,10));
			walls.add(new Wall(100,100,10,400));
			walls.add(new Wall(700,100,10,150));
			walls.add(new Wall(700,350,10,150));
			walls.add(new Wall(200,200,400,10));
			walls.add(new Wall(200,390,400,10));
			walls.add(new Wall(200,200,10,50));
			walls.add(new Wall(200,350,10,50));
			walls.add(new Wall(600,200,10,200));

			for (int i = bullets.size()-1; i >= 0; i--){
				bullets.remove(i);
			}

			for (int i = pu.size()-1; i >= 0; i--){
				pu.remove(i);
			}
			
			for (int i = bomb.size()-1; i >= 0; i--){
				bomb.remove(i);
			}
			
			pu.add(new PowerUp("heart.png", 230, 225));
			pu.add(new PowerUp("heart.png", 70, 70));
			
			bomb.add(new Bomb("bomb.png", 165, 360));
			bomb.add(new Bomb("bomb.png", 625, 360));

			levelfinished = false;
		}
	}

	/**
	 * Detects any key presses
	 * @param arg0 an instance of a KeyEvent
	 */
	public void keyPressed(KeyEvent arg0) {
		int code = arg0.getKeyCode();

		if (code == KeyEvent.VK_UP) {
			tank1.moveForward(true);
		}

		if (code == KeyEvent.VK_DOWN ) {
			tank1.moveBackward(true);
		}

		if (code == KeyEvent.VK_LEFT){
			tank1.turnLeft(true);
		}

		if (code == KeyEvent.VK_RIGHT){
			tank1.turnRight(true);
		}

		if (code == KeyEvent.VK_V){
			Sound.bullet.play();
			Bullet b = new Bullet((int) (tank2.getDirX()),(int) (tank2.getDirY()), tank2.getDirection());
			bullets.add(b);
		}

		if (code == KeyEvent.VK_W ) {
			tank2.moveForward(true);
		}

		if (code == KeyEvent.VK_S ) {
			tank2.moveBackward(true);
		}

		if(code == KeyEvent.VK_A ){
			tank2.turnLeft(true);
		}

		if(code == KeyEvent.VK_D){
			tank2.turnRight(true);
		}

		if (code == KeyEvent.VK_M){
			Sound.bullet.play();
			Bullet b = new Bullet((int) (tank1.getDirX()),(int) (tank1.getDirY()), tank1.getDirection());
			bullets.add(b);
		}

	}

	/**
	 * Detects any key releases
	 * @param arg0 an instance of a KeyEvent
	 */
	public void keyReleased(KeyEvent arg0) {
		int code = arg0.getKeyCode();
		if (code == KeyEvent.VK_UP) {
			tank1.moveForward(false);
		}

		if (code == KeyEvent.VK_DOWN ) {
			tank1.moveBackward(false);
		}

		if (code == KeyEvent.VK_LEFT){
			tank1.turnLeft(false);
		}

		if (code == KeyEvent.VK_RIGHT){
			tank1.turnRight(false);
		}

		if (code == KeyEvent.VK_W ) {
			tank2.moveForward(false);
		}

		if (code == KeyEvent.VK_S  ) {
			tank2.moveBackward(false);
		}

		if(code == KeyEvent.VK_A ){
			tank2.turnLeft(false);
		}

		if(code == KeyEvent.VK_D ){
			tank2.turnRight(false);
		}
	}

	/**
	 * Detects any key typed
	 * @param arg0 an instance of a KeyEvent
	 */
	public void keyTyped(KeyEvent arg0) {
	}


}



