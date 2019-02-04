package Components;

import java.io.File;

import javax.sound.sampled.*;

public class Sound {
	private Clip c;
	public static Sound backM = new Sound("backgroundMusic.wav");
	public static Sound boom = new Sound("boomSound.wav");
	public static Sound bomb = new Sound("bombSound.wav");
	public static Sound bullet = new Sound("bulletSound.wav");
	public static Sound health = new Sound("healthSound.wav");
	
	/**
	 * Constructs sound object
	 * @param fileName string name of sound file
	 */
	public Sound (String fileName) {
		File file = new File(fileName);
		try {
			AudioInputStream aistream = AudioSystem.getAudioInputStream(file);
			c = AudioSystem.getClip();
			c.open(aistream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Plays the clip one time
	 */
	public void play() {
		try {
			if (c != null) 
			{
				new Thread() 
				{
					public void run() 
					{
						synchronized (c) 
						{
							c.stop();
							c.setFramePosition(0);
							c.start();
						}
					}
				}
				.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Stops the clip
	 */
	public void stop(){
		if(c == null) 
			return;
		c.stop();
	}
	
	/**
	 * Loops through the clip infinitely
	 */
	public void loop() {
		try {
			if (c != null) 
			{
				new Thread() 
				{
					public void run() 
					{
						synchronized (c) 
						{
							c.stop();
							c.setFramePosition(0);
							c.loop(Clip.LOOP_CONTINUOUSLY);
						}
					}
				}
				.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
