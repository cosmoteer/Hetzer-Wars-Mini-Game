package GUI;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class IntroScreen extends JPanel  {

	Main w;
	public static final int DRAWING_WIDTH = 800;
	public static final int DRAWING_HEIGHT = 600;


	/**
	 * Initializes several JButtons in the Menu Screen
	 * @param w instance of main class
	 */
	public IntroScreen(Main w) {
		this.w = w;
		JButton button1 = new JButton("Play All");
		JButton button2 = new JButton("Play Level 1");
		JButton button3 = new JButton("Play Level 2");
		JButton button4 = new JButton("Play Level 3");
		JButton button5 = new JButton("Instructions");

		button1.setBackground(Color.WHITE);
		button2.setBackground(new Color(253,245,230));
		button3.setBackground(new Color(253,245,230));
		button4.setBackground(new Color(253,245,230));
		button5.setBackground(Color.WHITE);

		button1.setFont(button1.getFont().deriveFont(16.0f));
		button2.setFont(button2.getFont().deriveFont(13.0f));
		button3.setFont(button3.getFont().deriveFont(13.0f));
		button4.setFont(button4.getFont().deriveFont(13.0f));
		button5.setFont(button4.getFont().deriveFont(13.0f));


		button1.setPreferredSize(new Dimension(100,50));

		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				w.getGamePanel().isOnlyOneLevel(false);
				w.getGamePanel().startTheGame();
				w.changePanel("2");
			}

		});
		add(button1);

		button5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(w, "", "Instructions", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(("instructions.PNG")));
			}
		});
		add(button5);

		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				w.getGamePanel().isOnlyOneLevel(true);
				w.getGamePanel().setCurrentLevel(1);
				w.getGamePanel().startTheGame();
				w.changePanel("2");
			}

		});
		add(button2);


		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				w.getGamePanel().setCurrentLevel(2);
				w.getGamePanel().isOnlyOneLevel(true);
				w.getGamePanel().startTheGame();

				w.changePanel("2");
			}

		});
		add(button3);


		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				w.getGamePanel().isOnlyOneLevel(true);
				w.getGamePanel().setCurrentLevel(3);
				w.getGamePanel().startTheGame();
				w.changePanel("2");	
			}

		});
		add(button4);


	}

	@Override
	/**
	 * Draws the Menu Screen
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

		g.drawImage((new ImageIcon("back.png")).getImage(), 0, 0, null);

		g.setFont(g.getFont().deriveFont(18.0f)); 
		g.setFont(new Font("SERIF", Font.BOLD, 70));
		g.setColor(new Color(253,245,230));
		g.drawString("Hetzer Wars", 370,  550);

		g2.setTransform(at);


	}



}