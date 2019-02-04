package GUI;

import java.awt.*;
import java.awt.geom.AffineTransform;

import javax.swing.*;

public class WinnerScreen extends JPanel {
	
	public static final int DRAWING_WIDTH = 800;
	public static final int DRAWING_HEIGHT = 600;
	Main w;
	private int tank1wins;
	private int tank2wins;
	
	/**
	 * Creates the winner screen
	 * @param w instance of main class
	 */
	public WinnerScreen(Main w) {
		this.w = w;
		tank1wins = 0;
		tank2wins = 0;
	}
	
	/**
	 * Determines which tank wins
	 * @param tanknumber number of tank
	 * @param wins number of wins of the tank of tankNumber
	 */
	public void setTankWins(int tanknumber, int wins){
	
	   if (tanknumber == 1){
		   tank1wins = wins;
	   }
	   if (tanknumber == 2){
		   tank2wins = wins;
	   }
	}
	
	/**
	 * Draws the Winner Screen
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

			
			g.drawImage((new ImageIcon("winnerPanelBackground.png")).getImage(), 0, 0, null);
			
			g.setColor(Color.WHITE);
			g.fillRoundRect(290, 240, 220, 170, 10, 10);
			g.setFont(g.getFont().deriveFont(18.0f)); 
			g.setFont(new Font("default", Font.BOLD, 18));
			
			if (tank1wins > tank2wins){
				g.setColor(Color.RED);
				g.drawRoundRect(300, 250, 200, 150, 5, 5);				
				g.drawString("Red Tank Wins", 340,  330);
			}
			else {
				g.setColor(new Color(34,139,34));
				g.drawRoundRect(300, 250, 200, 150, 5, 5);
				g.drawString("Green Tank Wins", 330,  330);
			}
				
			
			

			g2.setTransform(at);
		}
	
}