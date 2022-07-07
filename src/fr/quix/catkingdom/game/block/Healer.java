package fr.quix.catkingdom.game.block;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import fr.quix.catkingdom.game.World;
import fr.quix.catkingdom.ressources.Ressources;

public class Healer implements Block {
	
	public static BufferedImage[] textures = null;
	
	public int maxHealth = 20;
	public int health = 20;
	
	public World world;
	
	public Healer(World world) {
		this.world = world;
		if(textures == null) prepareTextures();
	}
	
	@Override
	public BufferedImage getTexture() {
		return textures[(int) ((System.currentTimeMillis()%1440)/4)];
	}
	
	public void prepareTextures() {
		textures = new BufferedImage[360];
		for(int i = 0;i < 360;i ++) {
			BufferedImage img = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
			
			Graphics2D g2D = img.createGraphics();
			
			g2D.drawImage(Ressources.getImage("block.healer.body"), 0, 0, 64, 64, null);
			g2D.translate(32, 32);
			g2D.rotate(Math.toRadians(i));
			g2D.drawImage(Ressources.getImage("block.healer.head"), -32, -32, 64, 64, null);
			
			textures[i] = img;
		}
	}

	@Override
	public void clicked() {
		
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public String hoverText() {
		return "This is a healer that heals 2 blocks around him every 5 seconds";
	}
	
	int reloadTime = 300;
	int reload = 0;
	
	@Override
	public void tick(int x, int y) {
		if(reload<=0) {
			for(int bx = -2;bx < 4;bx ++) {
				for(int by = -2;by < 4;by ++) {
					if(bx>0&&by>0&&bx<world.blocks.length&&by<world.blocks[0].length) {
						Block b = world.blocks[x-bx][y-by];
						if(b!=null) {
							b.addHealth(3);
						}
					}
				}
			}
			reload = reloadTime;
		}else {
			reload--;
		}
	}

	@Override
	public int getpathfindincost() {
		return 0;
	}

	@Override
	public int gethealth() {
		return health;
	}

	@Override
	public int getmaxhealth() {
		return maxHealth;
	}

	@Override
	public int getprice() {
		return 150;
	}

	@Override
	public void takeDamage(int count) {
		health -= count;
	}

	@Override
	public Block createNew() {
		return new Healer(world);
	}

	@Override
	public void addHealth(int count) {
		health += count;
		if(health>maxHealth)
			health = maxHealth;
	}

}





