package fr.quix.catkingdom.game.block;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import fr.quix.catkingdom.game.World;
import fr.quix.catkingdom.game.entity.Entity;
import fr.quix.catkingdom.game.particle.Shot;
import fr.quix.catkingdom.ressources.Ressources;

public class Sniper implements Block{
	public double rotation = 0;

	int health = 30;
	int maxhealth= 30;

	public BufferedImage img = null;
	
	World world;
	public Sniper(World world) {
		this.world = world;
		render();
		
		render();
	}
	@Override
	public BufferedImage getTexture() {
		return img;
	}
	public void render() {
		img = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2D = img.createGraphics();
		
		g2D.drawImage(Ressources.getImage("block.sniper.body"), 0, 0, 64, 64, null);
		g2D.translate(32, 32);
		g2D.rotate(Math.toRadians(rotation));
		g2D.drawImage(Ressources.getImage("block.sniper.head"), -32, -32, 64, 64, null);
	}

	@Override
	public void clicked() {}

	@Override
	public boolean isSolid() {
		return true;
	}
	@Override
	public String hoverText() {
		return "A simple sniper. 20 attack damage, 15 seconds to load and 40 blocs range.";
	}
	
	int timetoreload = 900;
	int reload = 0;
	
	@Override
	public void tick(int x, int y) {
		
		if(reload<=0) {
			Entity target = null;
			
			for(Entity e : world.entities) {
				if(e.isbald()&&e.isAlive()) {
					double[] loc = e.getLocation();
					
					double dist = Math.sqrt(((loc[0]-x)*(loc[0]-x))+((loc[1]-y)*(loc[1]-y)));
					if(dist<40) {
						target = e;
						break;
					}
				}
			}
			
			if(target!=null) {
				double[] loc = target.getLocation();
				double ex = loc[0];
				double ey = loc[1];
				
				double bx = x;
				double by = y;

				double dx = ex - bx;
				double dy = ey - by;
				
				rotation = Math.toDegrees(Math.atan2(dy, dx))-90;
				
				target.takeDamage(20);
				reload = timetoreload;
				render();
				
				world.particles.add(new Shot(bx+.5, by+.5, ex+.5, ey+.5, 10));
			}
		}else {
			reload--;
		}
		
		
	}
	@Override
	public int getpathfindincost() {
		return 1;
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
	public int getprice() {
		return 250;
	}

	@Override
	public void takeDamage(int count) {
		this.health -= count;
	}

	@Override
	public Block createNew() {
		return new Sniper(world);
	}
	@Override
	public void addHealth(int count) {
		health += count;
		if(health>maxhealth)
			health = maxhealth;
	}

}
