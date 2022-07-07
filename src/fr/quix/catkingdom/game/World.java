package fr.quix.catkingdom.game;

import java.util.ArrayList;

import fr.quix.catkingdom.game.block.Block;
import fr.quix.catkingdom.game.block.Bricks;
import fr.quix.catkingdom.game.entity.Cat;
import fr.quix.catkingdom.game.entity.Cucumber;
import fr.quix.catkingdom.game.entity.DoggoBoss;
import fr.quix.catkingdom.game.entity.Entity;
import fr.quix.catkingdom.game.entity.Ghost;
import fr.quix.catkingdom.game.entity.Pickle;
import fr.quix.catkingdom.game.particle.Particle;
import fr.quix.catkingdom.game.tile.Carpet;
import fr.quix.catkingdom.game.tile.Grass;
import fr.quix.catkingdom.game.tile.Planks;
import fr.quix.catkingdom.game.tile.Tile;
import fr.quix.catkingdom.game.tile.Water;

public class World {
	public Tile[][] tiles;
	public Block[][] blocks;

	public int movex = 0;
	public int movey = 0;
	
	public int camcenterx = 800;
	public int camcentery = 800;
	
	public int tickPerWave = 600;
	public int nextWave = tickPerWave;
	public int wave = 1;
	public int score = 0;
	
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public ArrayList<Particle> particles = new ArrayList<Particle>();

	public Cat[] toDefend = null;
	
	public Player player = new Player(this);
	
	public World(int sizex, int sizey) {
		tiles = new Tile[sizex][sizey];
		blocks = new Block[sizex][sizey];
		
		for(int x = 0;x < sizex;x ++)for(int y = 0;y < sizey;y ++) {
			tiles[x][y] = new Water();
		}
	    double midPoint = (tiles.length-1)/2.0;
	    for (int col = 0; col < tiles.length; col++)
	    {
		Tile[] row = tiles[col];
		double yy = col-midPoint;
		for (int x=0; x<row.length; x++)
		{
		   double xx = x-midPoint;
		   if (Math.sqrt(xx*xx+yy*yy)<=midPoint)
		     row[x] = new Grass();
		}
		tiles[col] = row;
	    }

	    player.x = sizex/2;
	    player.y = sizey/2;

		for(int x = (sizex/2)-10;x < (sizex/2)+10;x ++)for(int y = (sizey/2)-10;y < (sizey/2)+10;y ++) {
			tiles[x][y] = new Planks();
		}
		for(int x = (sizex/2)-1;x < (sizex/2)+2;x ++)for(int y = (sizey/2)-10;y < (sizey/2)+10;y ++) {
			tiles[x][y] = new Carpet();
		}
		
	    for(int i = 0;i < 20;i ++) {
	    	blocks[(sizex/2)+i-9][(sizey/2)-10] = new Bricks();
	    	blocks[(sizex/2)+i-9][(sizey/2)+10] = new Bricks();
	    	blocks[(sizex/2)-10][(sizey/2)+i-10] = new Bricks();
	    	blocks[(sizex/2)+10][(sizey/2)+i-10] = new Bricks();
	    }
	    blocks[(sizex/2)-10][(sizey/2)+10] = new Bricks();
	    
	    toDefend = new Cat[10];
	    for(int i = 0;i < toDefend.length;i ++) {
	    	toDefend[i] = new Cat(this);
	    	
	    	toDefend[i].setLocation((double)((sizex/2)+(Math.random()*10)-5), (double)((sizey/2)+(Math.random()*10)-5));
	    	entities.add(toDefend[i]);
	    }
	}
	
	public void tick() {
		
		nextWave--;
		
		if(nextWave<=0) {
			nextWave = tickPerWave;
			
			wave++;

			int cucumbercount = 2 + wave;
			int picklecount = 0;
			int dogbosscount = 0;
			int ghostbosscount = 0;
			
			if(wave%10==0) dogbosscount += wave / 10;
			
			if(wave>5) picklecount += wave / 5;
			if(wave>10) picklecount += wave / 10;

			if(wave>20) ghostbosscount += wave / 20;
			if(wave>25) ghostbosscount += wave - 25;
			
			
			for(int i = 0;i < cucumbercount;i ++) {
				Cucumber c = new Cucumber(this);
				c.setLocation((tiles.length/2) + (Math.random()*10) - 5, (tiles[0].length/1.2) + (Math.random()*10) - 5);
				entities.add(c);
			}
			for(int i = 0;i < picklecount;i ++) {
				Pickle c = new Pickle(this);
				c.setLocation((tiles.length/2) + (Math.random()*10) - 5, (tiles[0].length/1.2) + (Math.random()*10) - 5);
				entities.add(c);
			}
			for(int i = 0;i < dogbosscount;i ++) {
			    DoggoBoss dog = new DoggoBoss(this);
			    dog.setLocation(18, 40);
			    entities.add(dog);
			}
			for(int i = 0;i < ghostbosscount;i ++) {
			    Ghost g = new Ghost(this);
			    g.setLocation(18, 40);
			    entities.add(g);
			}
		}
		
		player.calculateVelocity(movex, movey);
		player.tick();
		
		if(player.health<=0) {
			player.respawn(tiles.length/2, tiles[0].length/2);
		}
		
		entities.forEach(e -> {
			e.tick();
		});
	    for(int i = 0;i < entities.size();i ++) {
	    	if(!entities.get(i).isAlive()) {
	    		if(entities.get(i).isbald())
	    			score += entities.get(i).getMaxHealth() * entities.get(i).deathGain();
	    		player.money += entities.get(i).deathGain();
	    		entities.remove(i);
	    		i--;
	    	}
	    }

	    for(int x  = 0;x < blocks.length;x ++) {
		    for(int y  = 0;y < blocks[0].length;y ++) {
		    	if(blocks[x][y]!=null) {
		    		blocks[x][y].tick(x, y);
		    		if(blocks[x][y].gethealth()<=0) {
		    			blocks[x][y] = null;
		    		}
		    	}
		    }
	    }

	    for(int i = 0;i < particles.size();i ++) {
		    particles.get(i).tick();
		    if(particles.get(i).expired()) {
		    	particles.remove(i);
		    	i--;
		    }
	    }
	    
	    
	}
}











