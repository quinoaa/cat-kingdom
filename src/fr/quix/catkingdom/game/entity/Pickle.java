package fr.quix.catkingdom.game.entity;

import java.awt.image.BufferedImage;

import fr.quix.catkingdom.game.World;
import fr.quix.catkingdom.game.tile.Tile;
import fr.quix.catkingdom.ressources.Ressources;

public class Pickle implements Entity{
	
	int maxhealth = 50;
	int health = 50;
	
	double x = 0, y = 0;
	
	int direction = 1;

	double speed = 0.07;
	double normalspeed = 0.05;
	
	World world;
	
	Cat target = null;

	int tickbeforerecheckpathdefault = 300;
	int tickbeforerecheckpath = 0;
	int[][] path = null;
	
	int attackcooldownmax = 60;
	int attackcooldown = 0;
	
	int attackdamage = 5;
	
	public Pickle(World world) {
		this.world = world;
	}
	@Override
	public void tick() {
		if(attackcooldown>0)
			attackcooldown--;

		Tile tile = world.tiles[(int)x][(int)y];
		if(tile!=null) {
			speed = normalspeed * tile.movementMultiplier() + (Math.random()*0.01);
		}
		
		if(isAlive()) {
			if(target!=null&&!target.isAlive())
				target = null;
			if(target == null) {
				target = getNearestCat();
			}
			if(target != null) {
				if(target.x+0.5>x) {
					if(world.blocks[(int)(x+speed)][(int)y]==null||!(world.blocks[(int)(x+speed)][(int) y].isSolid())) {
						direction = 2;
						x += speed;
					}
					else if(attackcooldown<=0) {
						world.blocks[(int)(x+speed)][(int)(y)].takeDamage(attackdamage);
						attackcooldown = attackcooldownmax;
					}
				}
				if(target.x-0.5<x) {
					if(world.blocks[(int)(x-speed)][(int)y]==null||!(world.blocks[(int)(x-speed)][(int) y].isSolid())) {
						direction = 1;
						x -= speed;
					} else if(attackcooldown<=0) {
						world.blocks[(int)(x-speed)][(int)(y)].takeDamage(attackdamage);
						attackcooldown = attackcooldownmax;
					}
				}
				if(target.y+0.5>y) {
					if(world.blocks[(int)(x)][(int)(y+speed)]==null||!(world.blocks[(int)(x)][(int) (y+speed)].isSolid())) {
						direction = 0;
						y += speed;
					} else if(attackcooldown<=0) {
						world.blocks[(int)(x)][(int)(y+speed)].takeDamage(attackdamage);
						attackcooldown = attackcooldownmax;
					}
				}
				if(target.y-0.5<y) {
					if(world.blocks[(int)(x)][(int)(y-speed)]==null||!(world.blocks[(int)(x)][(int) (y-speed)].isSolid())) {
						y -= speed;
						direction = 3;
					} else if(attackcooldown<=0) {
						world.blocks[(int)(x)][(int)(y-speed)].takeDamage(attackdamage);
						attackcooldown = attackcooldownmax;
					}
				}
				if(attackcooldown<=0&&dist(target.x, target.y)<=1.5) {
					attackcooldown = attackcooldownmax;
					target.takeDamage(attackdamage);
				}
			}
			
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
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return health;
	}

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return maxhealth;
	}

	@Override
	public BufferedImage getTexture() {
		int x = (int) (((System.currentTimeMillis()%400)/100*16));
		int y = 0;
		y = direction;
		return Ressources.getImage("entity.pickle").getSubimage(x, y*16, 16, 16);
	}
	@Override
	public boolean isAlive() {
		return health>0;
	}
	@Override
	public void takeDamage(int count) {
		health -= count;
		if(!isAlive()) {
			world.player.money += 10;
		}
	}
	public Cat getNearestCat() {
		Cat nearest = null;
		double ndist = -1;
		
		for(Cat cat : world.toDefend) {
			if(cat.isAlive()) {
				double[] pos = cat.getLocation();
				
				double dist = Math.sqrt(((pos[0] - x)*(pos[0] - x)) + ((pos[1] - y)*(pos[1] - y)));
				
				if(ndist==-1||dist<ndist) {
					ndist = dist;
					nearest = cat;
				}
			}
		}
		
		return nearest;
	}
	public double dist(double xe2, double ye2) {
		return Math.sqrt(((xe2 - x)*(xe2 - x)) + ((ye2 - y)*(ye2 - y)));
	}
	@Override
	public boolean isbald() {
		return true;
	}
	@Override
	public int deathGain() {
		return 20;
	}
}





