package fr.quix.catkingdom.ui.handler;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import fr.quix.catkingdom.game.Player;
import fr.quix.catkingdom.game.World;
import fr.quix.catkingdom.game.block.Block;
import fr.quix.catkingdom.game.block.Bricks;
import fr.quix.catkingdom.game.block.Canon;
import fr.quix.catkingdom.game.block.Healer;
import fr.quix.catkingdom.game.block.Sniper;
import fr.quix.catkingdom.game.entity.Cat;
import fr.quix.catkingdom.game.entity.Entity;
import fr.quix.catkingdom.game.particle.Particle;
import fr.quix.catkingdom.game.tile.Carpet;
import fr.quix.catkingdom.game.tile.Grass;
import fr.quix.catkingdom.game.tile.Planks;
import fr.quix.catkingdom.game.tile.Tile;
import fr.quix.catkingdom.game.tile.Water;
import fr.quix.catkingdom.ressources.Ressources;
import fr.quix.catkingdom.ui.Panel;

public class Game extends Handler {
	World world = new World(48, 48);
	
	public Block[] blockpalette = {new Bricks(), new Canon(world), new Sniper(world), new Healer(world)};
	public Tile[] tilepalette = {new Water(),new Carpet(),new Planks(),new Grass()};
	
	Panel pan;
	
	int mousemode = 1;
	
	Object selected = new Bricks();
	
	KeyListener key = new KeyListener() {
		@Override public void keyTyped(KeyEvent e) { }
		
		@Override
		public void keyReleased(KeyEvent e) {
			if(Character.toLowerCase(e.getKeyChar())=='z'||Character.toLowerCase(e.getKeyChar())=='w'||
					Character.toLowerCase(e.getKeyChar())=='s')
				world.movey=0;
			if(Character.toLowerCase(e.getKeyChar())=='a'||Character.toLowerCase(e.getKeyChar())=='q'||
					Character.toLowerCase(e.getKeyChar())=='d')
				world.movex=0;
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			if(Character.toLowerCase(e.getKeyChar())=='w'||Character.toLowerCase(e.getKeyChar())=='z')
				world.movey=-1;
			if(Character.toLowerCase(e.getKeyChar())=='q'||Character.toLowerCase(e.getKeyChar())=='a')
				world.movex=-1;
			if(Character.toLowerCase(e.getKeyChar())=='s')
				world.movey=1;
			if(Character.toLowerCase(e.getKeyChar())=='d')
				world.movex=1;
		}
	};

	int mousex = 0, mousey = 0;
	int scroll = 0;

	boolean mousepressed = false;
	boolean mousewaspressed = false;
	
	boolean mouserightpressed = false;
	boolean mouserightwaspressed = false;
	int mouserightclickx = 0, mouserightclicky = 0;

	MouseMotionListener motionlistener = new MouseMotionListener() {
		@Override
		public void mouseMoved(MouseEvent e) {
			mousex = e.getX();
			mousey = e.getY();
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			mousex = e.getX();
			mousey = e.getY();
		}
	};
	MouseListener mouselistener = new MouseListener() {
		@Override public void mouseExited(MouseEvent e) { }
		@Override public void mouseEntered(MouseEvent e) { }
		@Override public void mouseClicked(MouseEvent e) { }
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if(e.getButton()==MouseEvent.BUTTON1)
				mousepressed = false;
			if(e.getButton()==MouseEvent.BUTTON3)
				mouserightpressed = false;
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if(e.getButton()==MouseEvent.BUTTON1)
				mousepressed = true;
			if(e.getButton()==MouseEvent.BUTTON3) {
				mouserightpressed = true;
				mouserightclickx = e.getX();
				mouserightclicky = e.getY();
			}
		}
		
	};
	
	public Game(Panel pan) {
		super(pan);
		this.pan = pan;
		
		pan.addKeyListener(key);
		pan.addMouseMotionListener(motionlistener);
		pan.addMouseListener(mouselistener);
	}
	@Override
	public void paint(Graphics2D g2D) {
		world.tick();
		
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2D.setColor(Color.BLACK);
		g2D.fillRect(0, 0, pan.getWidth(), pan.getHeight());

		int xcenter = (pan.getWidth()/2);
		int ycenter = (pan.getHeight()/2);
		
		int xtopleft = (world.camcenterx - xcenter);
		int ytopleft = (world.camcentery - ycenter);
		int xbotright = (world.camcenterx + xcenter);
		int ybotright = (world.camcentery + ycenter);

		for(int x = (xtopleft/64)-1;x < (xbotright/64)+1;x++) {
			for(int y = (ytopleft/64)-1;y < (ybotright/64)+1;y++) {
				
				if(x>=0&&x<world.tiles.length&&y>=0&&y<world.tiles[0].length&&world.tiles[x][y]!=null)
				g2D.drawImage(world.tiles[x][y].getTexture(), (x*64)-xtopleft, (y*64)-ytopleft, 64, 64, null);
				
			}
		}

		for(int x = (xtopleft/64)-1;x < (xbotright/64)+1;x++) {
			for(int y = (ytopleft/64)-1;y < (ybotright/64)+1;y++) {
				
				if(x>=0&&x<world.blocks.length&&y>=0&&y<world.blocks[0].length&&world.blocks[x][y]!=null)
				g2D.drawImage(world.blocks[x][y].getTexture(), (x*64)-xtopleft, (y*64)-ytopleft, 64, 64, null);
				
			}
		}
		int attack = 0;
		for(Entity e : world.entities) {
			double[] pos = e.getLocation();
			
			double rx = world.player.x-pos[0];
			double ry = world.player.y-pos[1];
			g2D.drawImage(e.getTexture(), (int)(((pos[0]*64)-xtopleft)-32), (int)(((pos[1]*64)-ytopleft)-32), 64, 64, null);
			g2D.setColor(new Color(255, 0, 0));
			g2D.fillRect((int)(((pos[0]*64)-xtopleft)-25), (int)(((pos[1]*64)-ytopleft)-40), 50, 4);
			g2D.setColor(new Color(0, 255, 0));
			g2D.fillRect((int)(((pos[0]*64)-xtopleft)-25), (int)(((pos[1]*64)-ytopleft)-40), (50*e.getHealth())/e.getMaxHealth(), 4);
			if(mousemode==1&&e.isbald()&&Math.sqrt((rx*rx) + (ry*ry))<2&&attack<5) {
				g2D.setColor(new Color(255, 255, 255));
				g2D.setStroke(new BasicStroke(5));
				g2D.drawOval((int)(((pos[0]*64)-xtopleft)-32), (int)(((pos[1]*64)-ytopleft)-32), 64, 64);
				
				attack++;
			}
		}
		
		if(Panel.Fps<=45)world.particles.clear();
		for(Particle p : world.particles) {
			p.renderParticle(g2D, xtopleft, ytopleft);
		}
		
		Player player = world.player;

		double x = player.x;
		double y = player.y;

		x = (x*64)-xtopleft;
		y = (y*64)-ytopleft;
		x -= 32;
		y -= 32;
		
		g2D.drawImage(player.getCurrentTexture(), (int)x, (int)y, 64, 64, null);
		
		

		g2D.drawImage(Ressources.getImage("ui.menu"), 0, pan.getHeight()-150, 600, 150, null);
		

		g2D.drawImage(Ressources.getImage("ui.btn.attack"), 5, pan.getHeight()-34, 32, 32, null);
		g2D.drawImage(Ressources.getImage("ui.btn.build"), 5, pan.getHeight()-68, 32, 32, null);
		g2D.drawImage(Ressources.getImage("ui.btn.interact"), 5, pan.getHeight()-102, 32, 32, null);
		
		g2D.setColor(new Color(255, 255, 255, 100));
		g2D.fillRect(5, pan.getHeight()-(34*mousemode), 32, 32);

		g2D.setColor(new Color(0, 0, 0));
		g2D.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		
		for(int i = 0;i < 15;i ++) {
			if(blockpalette.length>i+scroll&&i+scroll>=0) {
				g2D.drawImage(blockpalette[i+scroll].getTexture(), 60 + (32*i), pan.getHeight()-96, 32, 32, null);
				g2D.drawString(blockpalette[i+scroll].getprice() + "$", 60 + (32*i), pan.getHeight()-48);
			}
			
			if(tilepalette.length>i+scroll&&i+scroll>=0) {
				g2D.drawImage(tilepalette[i+scroll].getTexture(), 60 + (32*i), pan.getHeight()-48, 32, 32, null);
				g2D.drawString(tilepalette[i+scroll].getprice() + "$", 60 + (32*i), pan.getHeight());
			}
		}
		
		
		
		g2D.drawImage(Ressources.getImage("ui.btn.left"), 40, pan.getHeight()-110, 16, 100, null);
		g2D.drawImage(Ressources.getImage("ui.btn.right"), 550, pan.getHeight()-110, 16, 100, null);
		
		g2D.drawImage(Ressources.getImage("ui.menuhealth"), pan.getWidth()-150, pan.getHeight()-150, 150, 150, null);
		g2D.drawImage(Ressources.getImage("ui.heart"), pan.getWidth()-115, pan.getHeight()-100, 32, 32, null);
		g2D.drawImage(Ressources.getImage("ui.money"), pan.getWidth()-115, pan.getHeight()-60, 32, 32, null);
		
		g2D.setColor(new Color(255, 255, 255));
		g2D.setFont(new Font(Font.SANS_SERIF, 0, 28));
		g2D.drawString(player.health + "/" + player.maxhealth, pan.getWidth()-80, pan.getHeight()-72);
		g2D.drawString(player.money + "$", pan.getWidth()-80, pan.getHeight()-38);
		
		
		
		g2D.setFont(new Font(Font.SANS_SERIF, 0, 25));
		
		String toptext = "Wave " + world.wave + " in " + (world.nextWave/60) + "seconds";
		
		int toptxtwidth = g2D.getFontMetrics().stringWidth(toptext);
		
		g2D.setColor(new Color(50, 20, 40, 180));
		g2D.fillRect((pan.getWidth()-toptxtwidth)/2, 0, toptxtwidth, 20);
		
		g2D.setColor(new Color(255, 255, 255));
		g2D.drawString(toptext, (pan.getWidth()-toptxtwidth)/2, 20);
		
		
		
		int rguimousex = mousex;
		int rguimousey = mousey - (pan.getHeight()-150);
		
		
		if(rguimousex>0&&rguimousey>0&&rguimousey<150&&rguimousex<600) {
			g2D.setFont(new Font(Font.SANS_SERIF, 0, 16));
			if(mousex>5&&mousex<37&&mousey>pan.getHeight()-34&&mousey<pan.getHeight()-2) {
				g2D.setColor(new Color(50, 20, 40, 180));
				g2D.fillRect(mousex-5, mousey-18, g2D.getFontMetrics().stringWidth("attack")+10, 20);
				
				g2D.setColor(new Color(255, 255, 255));
				g2D.drawString("attack", mousex, mousey);
			}
			if(mousex>5&&mousex<37&&mousey>pan.getHeight()-68&&mousey<pan.getHeight()-36) {
				g2D.setColor(new Color(50, 20, 40, 180));
				g2D.fillRect(mousex-5, mousey-18, g2D.getFontMetrics().stringWidth("build")+10, 20);
				
				g2D.setColor(new Color(255, 255, 255));
				g2D.drawString("build", mousex, mousey);
			}
			if(mousex>5&&mousex<37&&mousey>pan.getHeight()-102&&mousey<pan.getHeight()-70) {
				g2D.setColor(new Color(50, 20, 40, 180));
				g2D.fillRect(mousex-5, mousey-18, g2D.getFontMetrics().stringWidth("heal wall")+10, 20);
				
				g2D.setColor(new Color(255, 255, 255));
				g2D.drawString("heal wall", mousex, mousey);
			}
			if(mousex>60&&mousex<540&&mousepressed&&!mousewaspressed) {
				if(mousey>pan.getHeight()-96&&mousey<pan.getHeight()-64) {
					int i = scroll + ((mousex-64)/32);
					
					if(i>=0&&i<blockpalette.length) {
						selected = blockpalette[i];
						mousemode = 2;
					}
				}
				if(mousey>pan.getHeight()-48&&mousey<pan.getHeight()-16) {
					int i = scroll + ((mousex-64)/32);
					
					if(i>=0&&i<tilepalette.length) {
						selected = tilepalette[i];
						mousemode = 2;
					}
				}
			}
			if(mousepressed&&!mousewaspressed) {
				if(mousex>5&&mousex<37&&mousey>pan.getHeight()-34&&mousey<pan.getHeight()-2) {
					mousemode = 1;
				}
				if(mousex>5&&mousex<37&&mousey>pan.getHeight()-68&&mousey<pan.getHeight()-36) {
					mousemode = 2;
				}
				if(mousex>5&&mousex<37&&mousey>pan.getHeight()-102&&mousey<pan.getHeight()-70) {
					mousemode = 3;
				}
				if(mousex>40&&mousey>(pan.getHeight()-110)&&mousex<56&&mousey<pan.getHeight()-10) {
					if(scroll>0)
						scroll--;
				}
				if(mousex>550&&mousey>(pan.getHeight()-110)&&mousex<566&&mousey<pan.getHeight()-10) {
					if(scroll + 4 < blockpalette.length)
						scroll++;
				}
			}
			
		}else {
			
			if(mousemode!=2) {
				int mouseblockx = (mousex + xtopleft)/64;
				int mouseblocky = (mousey + ytopleft)/64;

				g2D.setFont(new Font(Font.SANS_SERIF, 0, 14));
				if(mouseblockx>0&&mouseblockx<world.blocks.length&&mouseblocky>0&&mouseblocky<world.blocks[0].length) {
					Block block = world.blocks[mouseblockx][mouseblocky];
					
					if(block!=null) {
						g2D.setFont(new Font(Font.SANS_SERIF, 0, 16));
						
						g2D.setColor(new Color(50, 20, 40, 180));
						g2D.fillRect(mousex-5, mousey-18, g2D.getFontMetrics().stringWidth(block.gethealth() + "/" + 
						block.getmaxhealth())+10, 20);
						
						g2D.setColor(new Color(255, 255, 255));
						g2D.drawString(block.gethealth() + "/" + block.getmaxhealth(), mousex, mousey);

						g2D.setColor(new Color(255, 0, 0));
						g2D.fillRect(mousex, mousey-24, 100, 4);;
						g2D.setColor(new Color(0, 255, 0));
						g2D.fillRect(mousex, mousey-24, 100*block.gethealth()/block.getmaxhealth(), 4);;
					}else {
						if(mousemode==1) {
							String text = "Click to attack monster in white circle";
							g2D.setColor(new Color(50, 20, 40, 180));
							g2D.fillRect(mousex-5, mousey-14, g2D.getFontMetrics().stringWidth(text)+10, 14);
							
							g2D.setColor(new Color(255, 255, 255));
							g2D.drawString(text, mousex, mousey);
							
						}
						if(mousemode==3) {
							String text = "Click on a wall to heal it.";
							g2D.setColor(new Color(50, 20, 40, 180));
							g2D.fillRect(mousex-5, mousey-14, g2D.getFontMetrics().stringWidth(text)+10, 14);
							
							g2D.setColor(new Color(255, 255, 255));
							g2D.drawString(text, mousex, mousey);
						}
					}
				}else {
					if(mousemode==1) {
						String text = "Click to attack";
						g2D.setColor(new Color(50, 20, 40, 180));
						g2D.fillRect(mousex-5, mousey-14, g2D.getFontMetrics().stringWidth(text)+10, 14);
						
						g2D.setColor(new Color(255, 255, 255));
						g2D.drawString(text, mousex, mousey);
						
						
					}
					if(mousemode==3) {
						String text = "Click on a wall to heal it.";
						g2D.setColor(new Color(50, 20, 40, 180));
						g2D.fillRect(mousex-5, mousey-14, g2D.getFontMetrics().stringWidth(text)+10, 14);
						
						g2D.setColor(new Color(255, 255, 255));
						g2D.drawString(text, mousex, mousey);
					}
				}
			}else {
				g2D.setFont(new Font(Font.SANS_SERIF, 0, 14));
				String text = "Left click to place, right click to remove.";
				g2D.setColor(new Color(50, 20, 40, 180));
				g2D.fillRect(mousex-5, mousey-14, g2D.getFontMetrics().stringWidth(text)+10, 14);
				
				g2D.setColor(new Color(255, 255, 255));
				g2D.drawString(text, mousex, mousey);
			}
			
			if(mousemode==2&&selected!=null) {
				Composite c = g2D.getComposite();
				AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5f);
				g2D.setComposite(ac);

				if(selected instanceof Block)
				g2D.drawImage(((Block)selected).getTexture(), (((mousex+xtopleft)/64)*64)-xtopleft, (((mousey+ytopleft)/64)*64)-ytopleft,
						64, 64, null);
				if(selected instanceof Tile)
				g2D.drawImage(((Tile)selected).getTexture(), (((mousex+xtopleft)/64)*64)-xtopleft, (((mousey+ytopleft)/64)*64)-ytopleft,
						64, 64, null);
				
				g2D.setComposite(c);
			}
			
			if(mousepressed&&!mousewaspressed) {
				if(mousemode==1) {
					world.player.attack();
				}
				if(mousemode==2&&selected!=null) {
					if(selected instanceof Block) {
						Block s = (Block) selected;
						if(s.getprice()<=world.player.money&&world.blocks[(xtopleft+mousex)/64][(ytopleft+mousey)/64]==null) {
							world.player.money -= s.getprice();
							world.blocks[(xtopleft+mousex)/64][(ytopleft+mousey)/64] = s.createNew();
						}
					}
					if(selected instanceof Tile) {
						Tile s = (Tile) selected;
						if(s.getprice()<=world.player.money&&
								world.tiles[(xtopleft+mousex)/64][(ytopleft+mousey)/64].getClass()!=s.getClass()) {
							world.player.money -= s.getprice();
							world.tiles[(xtopleft+mousex)/64][(ytopleft+mousey)/64] = s.createNew();
						}
					}
					
				}
				if(mousemode==3) {
					Block b = world.blocks[(xtopleft+mousex)/64][(ytopleft+mousey)/64];
					if(b!=null)
						b.clicked();
				}
			}
			if(mouserightwaspressed&&!mouserightpressed&&mousemode==2&&
					world.blocks[(xtopleft+mouserightclickx)/64][(ytopleft+mouserightclicky)/64]!=null) {
				if((xtopleft+mouserightclickx)/64==(xtopleft+mousex)/64&&(ytopleft+mouserightclicky)/64==(ytopleft+mousey)/64) {
					world.player.money += world.blocks[(xtopleft+mouserightclickx)/64][(ytopleft+mouserightclicky)/64].getprice()/2;
					world.blocks[(xtopleft+mouserightclickx)/64][(ytopleft+mouserightclicky)/64]=null;
				}
			}
		}
		if(mousex>60&&mousex<540) {
			if(mousey>pan.getHeight()-96&&mousey<pan.getHeight()-64) {
				int i = scroll + ((mousex-64)/32);
				
				if(i>=0&&i<blockpalette.length) {
					String text = blockpalette[i].getClass().getSimpleName() + ": " + blockpalette[i].hoverText();
					g2D.setFont(new Font(Font.SANS_SERIF, 0, 14));
					g2D.setColor(new Color(50, 20, 40, 180));
					g2D.fillRect(mousex-5, mousey-14, g2D.getFontMetrics().stringWidth(text)+10, 14);
					
					g2D.setColor(new Color(255, 255, 255));
					g2D.drawString(text, mousex, mousey);
				}
			}
			if(mousey>pan.getHeight()-48&&mousey<pan.getHeight()-16) {
				int i = scroll + ((mousex-64)/32);
				
				if(i>=0&&i<tilepalette.length) {
					String text = tilepalette[i].getClass().getSimpleName() + ": Speed may change depending on tiles.";
					g2D.setFont(new Font(Font.SANS_SERIF, 0, 14));
					g2D.setColor(new Color(50, 20, 40, 180));
					g2D.fillRect(mousex-5, mousey-14, g2D.getFontMetrics().stringWidth(text)+10, 14);
					
					g2D.setColor(new Color(255, 255, 255));
					g2D.drawString(text, mousex, mousey);
				}
			}
		}
		
		boolean catalive = false;
		for(Cat c : world.toDefend) {
			if(c.isAlive())
				catalive = true;
		}
		
		if(!catalive) {
			pan.removeKeyListener(key);
			pan.removeMouseListener(mouselistener);
			pan.removeMouseMotionListener(motionlistener);
			
			pan.handler = new GameEnd(pan, world.score + world.wave);
		}
		
		mousewaspressed = mousepressed;
		mouserightwaspressed = mouserightpressed;
	}
	
	
	
	
}
