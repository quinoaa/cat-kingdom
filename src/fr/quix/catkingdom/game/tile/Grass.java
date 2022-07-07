package fr.quix.catkingdom.game.tile;

import java.awt.image.BufferedImage;

import fr.quix.catkingdom.ressources.Ressources;

public class Grass implements Tile{
	public final BufferedImage texture = Ressources.getImage("tile.grass");
	@Override
	public BufferedImage getTexture() {
		return texture;
	}

	@Override
	public boolean placableon() {
		return true;
	}

	@Override
	public double movementMultiplier() {
		return 1;
	}

	@Override
	public int getprice() {
		return 0;
	}

	@Override
	public Tile createNew() {
		return new Grass();
	}

}
