package GUI;
import java.awt.event.*;
import javax.swing.*;

import java.awt.*;


public class Main extends JFrame{
	
	JPanel cardPanel;
	IntroScreen panel1;  
	GamePanel panel2;
    WinnerScreen panel3;
	
    /**
	 * Creates a Main object that handles the JPanels
	 * @param title name of game JFrame
	 */
	public Main(String title) {
		
		super(title);
		setBounds(100, 100, 800, 600);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    cardPanel = new JPanel();
	    CardLayout cl = new CardLayout();
	    cardPanel.setLayout(cl);
	    
	    panel1 = new IntroScreen(this);  
		panel2 = new GamePanel(this);
	    panel3 = new WinnerScreen(this);
		
	    
	    super.addKeyListener(panel2);
	    super.setFocusable(true);
	    
	    cardPanel.add(panel1,"1");
	    cardPanel.add(panel2,"2");
	    cardPanel.add(panel3,"3");
	    
	    add(cardPanel);
	
	    setVisible(true);
	    
	   Toolkit toolkit = Toolkit.getDefaultToolkit();
	    Image image = toolkit.getImage("cursorBlue.png");
	    Cursor c = toolkit.createCustomCursor(image , new Point(cardPanel.getX(), cardPanel.getY()), "cur");
	    cardPanel.setCursor(c);   
	    
	}
	
	public static void main(String[] args)
	{
		Main w = new Main("Tank Game");
	}
  
	/**
	 * Changes to a specific JPanel
	 * 
	 * @param name name of card panel
	 */
	public void changePanel(String name) {
		((CardLayout)cardPanel.getLayout()).show(cardPanel,name);
		requestFocus();
	}
	
	/**
	 * Gets winnerScreen panel
	 * @return panel3
	 */
	public WinnerScreen getWinnerScreen(){
		return panel3;
	}
	
	/**
	 * Gets GamePanel
	 * @return panel2
	 */
	public GamePanel getGamePanel(){
		return panel2;
	}
	

}
