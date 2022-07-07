package fr.quix.catkingdom.game.block;

import java.awt.image.BufferedImage;

public interface Block {
	public BufferedImage getTexture();
	public void clicked();
	public boolean isSolid();
	public String hoverText();
	public void tick(int x, int y);
	public void addHealth(int count);
	public int getpathfindincost();//Enemies supposed to have path finding but... time :(
	public int gethealth();
	public int getmaxhealth();
	public int getprice();
	public void takeDamage(int count);
	public Block createNew();
}
