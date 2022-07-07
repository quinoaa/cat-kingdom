package fr.quix.catkingdom.game.tile;

import java.awt.image.BufferedImage;

import fr.quix.catkingdom.ressources.Ressources;

public class Planks implements Tile {

	@Override
	public BufferedImage getTexture() {
		return Ressources.getImage("tile.planks");
	}

	@Override
	public boolean placableon() {
		return true;
	}

	@Override
	public double movementMultiplier() {
		return 1.2;
	}

	@Override
	public int getprice() {
		return 10;
	}

	@Override
	public Tile createNew() {
		return new Planks();
	}

}
