package fr.quix.catkingdom.game;

import java.awt.image.BufferedImage;

import fr.quix.catkingdom.game.entity.Entity;
import fr.quix.catkingdom.game.tile.Tile;
import fr.quix.catkingdom.ressources.Ressources;

public class Player {
	World world;
	
	public double x = 0, y = 0;
	
	double velocityx = 0;
	double velocityy = 0;
	
	public int health = 20;
	public int maxhealth = 20;
	
	public int regenCountdownmax = 300;
	public int regenCountdown = regenCountdownmax;
	
	
	public int money = 0;
	
	public Player(World world) {
		this.world = world;
		
	}
	
	public BufferedImage getCurrentTexture() {
		
		if(velocityx==0&&velocityy==0) {
			int x = (int) (((System.currentTimeMillis()%2500)/500*16));
			return Ressources.getImage("entity.player").getSubimage(x, 0, 16, 16);
		}else {
			int x = (int) (((System.currentTimeMillis()%1000)/250*16));
			int y = 0;
			if(velocityx>0) y = 1;
			if(velocityx<0) y = 2;
			if(velocityy>0) y = 3;
			if(velocityy<0) y = 4;
			return Ressources.getImage("entity.player").getSubimage(x, y*16, 16, 16);
		}
	}
	
	public void tick() {
		world.camcenterx = (int) (x * 64);
		world.camcentery = (int) (y * 64);
		
		y += velocityy;
		x += velocityx;
		
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
	
	public void calculateVelocity(int movex, int movey) {
		if(x>=0&&x<world.tiles.length&&y>=0&&y<world.tiles[0].length) {
			Tile tile = world.tiles[(int)x][(int)y];
			if(tile!=null) {
				velocityx = movex * tile.movementMultiplier() * 0.08;
				velocityy = movey * tile.movementMultiplier() * 0.08;
			}else
				takeDamage(1);
		} else
			takeDamage(1);
		
	}
	
	public void kill() {
		health = 0;
	}
	
	public void takeDamage(int count) {
		health -= count;
	}
	public void respawn(double x, double y) {
		this.health = maxhealth;
		
		this.x = x;
		this.y = y;
	}
	
	public void attack() {
		int attacked = 0;
		for(Entity e : world.entities) {
			double[] loc = e.getLocation();
			if(e.isbald()&&Math.sqrt(((loc[0]-x)*(loc[0]-x))+(loc[1]-y)*(loc[1]-y)) <= 2 ){
				e.takeDamage(3);
				
				attacked ++;
				if(attacked>5) break;
			}
		}
		
	}
}





