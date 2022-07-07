package fr.quix.catkingdom.game.particle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Shot extends Particle{
	int fade = 100;
	
	int size = 0;
	
	int x1, y1, x2, y2;
	
	public Shot(double x1, double y1, double x2, double y2, int size) {
		this.x1 = (int) (x1*64);
		this.y1 = (int) (y1*64);
		this.x2 = (int) (x2*64);
		this.y2 = (int) (y2*64);
		this.size = size;
	}
	public void tick() {
		fade -= 5;
	}
	public boolean expired() {
		return fade<=0;
	}
	public void renderParticle(Graphics2D g2D, int rx, int ry) {
		if(fade>0) {
			g2D.setColor(new Color(255, 255, 255, fade));
			g2D.setStroke(new BasicStroke(size));
			g2D.drawLine(x1-rx, y1-ry, x2-rx, y2-ry);
		}
	}
}
