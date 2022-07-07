package fr.quix.catkingdom.game.tile;

import java.awt.image.BufferedImage;

public interface Tile {
	public BufferedImage getTexture();
	public boolean placableon();
	public double movementMultiplier();
	public int getprice();
	public Tile createNew();
}
