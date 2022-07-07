package fr.quix.catkingdom.game.tile;

import java.awt.image.BufferedImage;

import fr.quix.catkingdom.ressources.Ressources;

public class Water implements Tile{
	public final BufferedImage texture = Ressources.getImage("tile.water");
	@Override
	public BufferedImage getTexture() {
		return texture;
	}

	@Override
	public boolean placableon() {
		return false;
	}

	@Override
	public double movementMultiplier() {
		return 0.2;
	}

	@Override
	public int getprice() {
		return 2;
	}

	@Override
	public Tile createNew() {
		return new Water();
	}

}
