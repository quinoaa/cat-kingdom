package fr.quix.catkingdom.ui.handler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import fr.quix.catkingdom.ressources.Ressources;
import fr.quix.catkingdom.ui.Panel;

public class Introduction extends Handler {
	String[] txt = ("A long time ago, the cat \nkingdom was powerful. But one day\n, cucumber camed and killed every(i think?) \ncats."
			+ " Simon saids \n\"OH NO, WE NEED TO SAVE THEM \nFROM EVIL CUCUMBRE\n KINGDOM\" but cucumber \nkingdom ate every military "
			+ "complexes.\n It's a sad story but it's the truth. \nDoggo liked cats but cats didn't like \ndoggo because doggo mlemed cat so"
			+ " cat \nfought back and doggo killed\n cat and cat killed doggo. \nThe cat empire may\n one day be as powerful \nas before "
			+ "with a new technology \nthat can teleport someone into cat's \nmind. You were chosen to"
			+ " protect the cat \nempire, good luck...")
			.split("\n") ;
	
	public BufferedImage img = Ressources.getImage("introduction.background");
	
	public MouseListener listener = new MouseListener() {
		@Override public void mouseReleased(MouseEvent e) { }
		@Override public void mouseExited(MouseEvent e) { }
		@Override public void mouseEntered(MouseEvent e) { }
		@Override public void mouseClicked(MouseEvent e) { }
		@Override public void mousePressed(MouseEvent e) {
			skip = true;
		}
	};
	
	Panel pan;
	
	boolean skip = false;
	public Introduction(Panel pan) {
		super(pan);
		this.pan = pan;
		pan.addMouseListener(listener);
	}
	double y = 0;
	@Override
	public void paint(Graphics2D g2D) {
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2D.drawImage(img, 0, 0, pan.getWidth(), pan.getHeight(), null);
		
		if(System.currentTimeMillis()%2000>1000)
			g2D.setColor(Color.white);
		else
			g2D.setColor(Color.yellow);
		
		g2D.setFont(new Font(Font.SANS_SERIF, 0, 30));
		g2D.drawString("Click to skip", pan.getWidth()-200, pan.getHeight()-20);
		
		y -= 0.5;
		int line = 0;
		for(int i = txt.length-1;i >= 0;i --) {
			g2D.drawString(txt[i], 10, (int) (y + (line*(-30)) + pan.getHeight() - (txt.length*(-30))));
			line++;
		}
		if(skip||y<=-1600) {
			pan.removeMouseListener(listener);
			pan.handler = new Game(pan);
		}
	}
	
	
	
}
