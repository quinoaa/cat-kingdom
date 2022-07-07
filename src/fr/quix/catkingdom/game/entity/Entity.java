package fr.quix.catkingdom.game.entity;

import java.awt.image.BufferedImage;

public interface Entity {
	public void tick();
	public double[] getLocation();
	public void setLocation(double x, double y);
	public int getHealth();
	public int getMaxHealth();
	public BufferedImage getTexture();
	public boolean isAlive();
	public void takeDamage(int count);
	public boolean isbald();
	public int deathGain();
}
