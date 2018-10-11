package game5;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Random;

import sun.audio.*;

public class MyCanvas extends Canvas implements KeyListener {
	
	// global variables - accessible in all methods
	Goodguy link = new Goodguy(10,10,50,50,"files/right.png");
	LinkedList badguys = new LinkedList();
	
	/**
	 * MyCanvas drawing canvas inherits from java.awt.Canvas
	 * @author mhoel
	 * @since Sept. 30, 2018
	 * @param no parameters, default constructor
	 */
	public MyCanvas() {
		this.setSize(600,400); // set same size as MyScreen
		this.addKeyListener(this); // add the listener to your canvas
		playIt("files/storm.wav");
		
		Random rand = new Random();
		int winwidth = this.getWidth();
		int winheight = this.getHeight();
		for(int i = 0; i<10; i++) {
			Badguy bg = new Badguy(rand.nextInt(winwidth), rand.nextInt(winheight),50,50,"files/villain.png");
			Rectangle r = new Rectangle(100,100,30,30);
			if (r.contains(link.getxCoord(),link.getyCoord())) { // check to see if badguy spawns on link
				System.out.println("badguy on top of link");
				continue;
			}
			badguys.add(bg);
		}
	}
	
	public void playIt(String filename) {
		
		try {
			InputStream in = new FileInputStream(filename);
			AudioStream as = new AudioStream(in);
			AudioPlayer.player.start(as);
		} catch (IOException e) {
			System.out.println(e);
		}
		
	}
	
	/**
	 *  paint overload java.awt.Canvas paint method and make it draw an oval
	 *  @param graphics context variable called g 
	 */
	@Override
	public void paint(Graphics g) {
		g.drawImage(link.getImg(), link.getxCoord(), link.getyCoord(), link.getWidth(), link.getHeight(), this); // draw good guy
		
		for(int i = 0; i < badguys.size(); i++) {// draw bad guys
			Badguy bg = (Badguy) badguys.get(i);
			g.drawImage(bg.getImg(), bg.getxCoord(), bg.getyCoord(), bg.getWidth(), bg.getHeight(), this); 
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//System.out.println(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e);
		link.moveIt(e.getKeyCode(),this.getWidth(),this.getHeight()); // move link in response to keypress
			
		for(int i = 0; i < badguys.size(); i++) { // check if badguys hit 
			Badguy bg = (Badguy) badguys.get(i); // convert generic 
			Rectangle r = new Rectangle(bg.getxCoord(),bg.getyCoord(),bg.getWidth(),bg.getHeight());
			if (r.contains(link.getxCoord(),link.getyCoord())) { 
				System.out.println("badguy hit by link");
				badguys.remove(i);
			}
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//System.out.println(e);
	}

}
