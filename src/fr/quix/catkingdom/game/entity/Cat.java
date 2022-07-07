package fr.quix.catkingdom.game.entity;

import java.awt.image.BufferedImage;

import fr.quix.catkingdom.game.World;
import fr.quix.catkingdom.ressources.Ressources;

public class Cat implements Entity{
	String texturename = null;
	
	int health = 20;
	int maxhealth = 20;

	int velocityx = 1;
	int velocityy = 0;
	
	double x = 0, y = 0;
	
	World world;
	
	public int regenCountdownmax = 300;
	public int regenCountdown = regenCountdownmax;
	
	
	public Cat(World world) {
		this.world = world;
	}
	@Override
	public void tick() {

		if(health<maxhealth) {
			regenCountdown --;
			if(regenCountdown<=0) {
				health++;
				regenCountdown = regenCountdownmax;
			}
		}else {
			regenCountdown = regenCountdownmax;
		}
	}
	@Override
	public double[] getLocation() {
		double[] pos = new double[2];
		pos[0] = x;
		pos[1] = y;
		return pos;
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public int getMaxHealth() {
		return maxhealth;
	}
	@Override
	public BufferedImage getTexture() {
		if(texturename==null) {
			if(Math.random()>0.5) {
				texturename = "entity.cat1";
			}else {
				texturename = "entity.cat2";
			}
		}
		if(velocityx==0&&velocityy==0) {
			int x = (int) (((System.currentTimeMillis()%2500)/500*16));
			return Ressources.getImage(texturename).getSubimage(x, 0, 16, 16);
		}else {
			int x = (int) (((System.currentTimeMillis()%1000)/250*16));
			int y = 0;
			if(velocityx>0) y = 1;
			if(velocityx<0) y = 2;
			if(velocityy>0) y = 3;
			if(velocityy<0) y = 4;
			return Ressources.getImage(texturename).getSubimage(x, y*16, 16, 16);
		}
	}
	@Override
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}
	@Override
	public boolean isAlive() {
		return health>0;
	}
	@Override
	public void takeDamage(int count) {
		health -= count;
	}
	@Override
	public boolean isbald() {
		return false;
	}
	@Override
	public int deathGain() {
		return 0;
	}
	
}
