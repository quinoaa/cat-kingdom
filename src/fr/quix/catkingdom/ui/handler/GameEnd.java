package fr.quix.catkingdom.ui.handler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import fr.quix.catkingdom.ui.Panel;

public class GameEnd extends Handler {

	public MouseListener listener = new MouseListener() {
		@Override public void mouseReleased(MouseEvent e) { }
		@Override public void mouseExited(MouseEvent e) { }
		@Override public void mouseEntered(MouseEvent e) { }
		@Override public void mouseClicked(MouseEvent e) { }
		@Override public void mousePressed(MouseEvent e) {
			skip = true;
		}
	};
	boolean skip = false;
	
	int score = 0;
	
	public GameEnd(Panel pan, int score) {
		super(pan);
		
		this.score = score;
		
		pan.addMouseListener(listener);
	}
	@Override
	public void paint(Graphics2D g2D) {
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2D.setColor(Color.black);
		g2D.fillRect(0, 0, pan.getWidth(), pan.getHeight());
		
		g2D.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 64));
		
		g2D.setColor(new Color(255, 255, 255));
		
		String scoretext = score + "";
		g2D.drawString(scoretext, (pan.getWidth()-g2D.getFontMetrics().stringWidth(scoretext))/2, pan.getHeight()/2);
		
		
		g2D.setFont(new Font(Font.SANS_SERIF, 0, 18));
		String post = "You can post your score on ludum dare :)";
		g2D.drawString(post, (pan.getWidth()-g2D.getFontMetrics().stringWidth(post))/2, (pan.getHeight()/2)+20);
		

		g2D.setFont(new Font(Font.SANS_SERIF, 0, 64));
		String txt = "Your score is:";
		g2D.drawString(txt, (pan.getWidth()-g2D.getFontMetrics().stringWidth(txt))/2, (pan.getHeight()/2)-70);
		
		if(skip) {
			pan.removeMouseListener(listener);
			pan.handler = new MainMenu(pan);
		}
	}
}
