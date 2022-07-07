package fr.quix.catkingdom.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.quix.catkingdom.ui.handler.Handler;

public class Panel extends JPanel{
	private static final long serialVersionUID = 1L;
	public static double Fps = 60;
	
	
	public Handler handler;
	public Panel(JFrame frame) {
		super();
		
		this.setIgnoreRepaint(true);
		this.setFocusable(true);
		this.requestFocus();
		this.setName("CatKingdom");
	}
	public Thread updater = new Thread(new Runnable() {
		@Override
		public void run() {
			Panel pan = getInstance();
			int fps = 60;
			long normalWait = 1000/fps;
			
			long Time = System.currentTimeMillis();
			long lastTime = Time;
			
			while(!Thread.interrupted()) {
				Time = System.currentTimeMillis();
				long wait = lastTime + normalWait - Time;
				lastTime += normalWait;
				
				Fps = ((double)wait) / ((double)normalWait) * 60;
				
				if(wait>0)
				try {
					Thread.sleep(wait);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				else if(wait<fps*(-60)) {
					lastTime = Time;
				}
				try {
					pan.repaint();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	});
	
	@Override
	public void paint(Graphics g) {
		handler.paint((Graphics2D)g);
	}
	
	public Panel getInstance() {
		return this;
	}
}
