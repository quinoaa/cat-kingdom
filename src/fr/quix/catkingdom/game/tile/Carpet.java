package fr.quix.catkingdom.game.tile;

import java.awt.image.BufferedImage;

import fr.quix.catkingdom.ressources.Ressources;

public class Carpet implements Tile {

	@Override
	public BufferedImage getTexture() {
		return Ressources.getImage("tile.carpet");
	}

	@Override
	public boolean placableon() {
		return true;
	}

	@Override
	public double movementMultiplier() {
		return 1.4;
	}

	@Override
	public int getprice() {
		return 15;
	}

	@Override
	public Tile createNew() {
		return new Carpet();
	}

}
