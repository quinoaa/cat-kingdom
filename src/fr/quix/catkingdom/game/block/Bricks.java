package fr.quix.catkingdom.game.block;

import java.awt.image.BufferedImage;

import fr.quix.catkingdom.ressources.Ressources;

public class Bricks implements Block {
	
	int health = 120;
	int maxhealth= 120;
	
	@Override
	public BufferedImage getTexture() {
		return Ressources.getImage("block.brick");
	}
	@Override
	public String hoverText() {
		return "Bricks are very resistant and can be healed by clicking on it with interact mode.";
	}
	@Override
	public void clicked() {
		if(health+1<=maxhealth)
			health++;
	}
	@Override
	public boolean isSolid() {
		return true;
	}
	@Override
	public void tick(int x, int y) {
	}
	@Override
	public int getpathfindincost() {
		return 50;
	}
	@Override
	public int gethealth() {
		return health;
	}
	@Override
	public int getmaxhealth() {
		return maxhealth;
	}
	@Override
	public void takeDamage(int count) {
		health -= count;
	}
	@Override
	public Block createNew() {
		return new Bricks();
	}
	@Override
	public int getprice() {
		return 20;
	}
	@Override
	public void addHealth(int count) {
		health += count;
		if(health>maxhealth)
			health = maxhealth;
	}
	
}






