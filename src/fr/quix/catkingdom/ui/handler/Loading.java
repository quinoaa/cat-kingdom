package fr.quix.catkingdom.ui.handler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import fr.quix.catkingdom.ressources.Ressources;
import fr.quix.catkingdom.ui.Panel;

public class Loading extends Handler {
	Panel pan;
	public Loading(Panel pan) {
		super(pan);
		this.pan = pan;
	}
	@Override
	public void paint(Graphics2D g2D) {
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		g2D.setColor(new Color(25, 27, 36));
		g2D.fillRect(0, 0, pan.getWidth(), pan.getHeight());
		
		g2D.setColor(new Color(92, 95, 102));
		g2D.fill(new RoundRectangle2D.Double((pan.getWidth()/2)-200 , (pan.getHeight()/1.5)-5, 400, 10, 10, 10));
		

		g2D.setColor(new Color(255, 255, 255));
		g2D.fill(new RoundRectangle2D.Double((pan.getWidth()/2)-200 , (pan.getHeight()/1.5)-5, 
				400/Ressources.loadingmax*Ressources.loading, 10, 10, 10));
		
		
		String loadingText = "Keep calm and wait";
		g2D.setFont(new Font(Font.SANS_SERIF, 0, 40));
		g2D.drawString(loadingText, (pan.getWidth()-g2D.getFontMetrics().stringWidth(loadingText))/2, (pan.getHeight()/2));
		
		g2D.setFont(new Font(Font.SANS_SERIF, 0, 10));
		g2D.drawString(Ressources.loadingStatus, (pan.getWidth()/2)-200, (int) ((pan.getHeight()/1.5)+20));
		
		if(Ressources.loadingfinished) {
			pan.handler = new MainMenu(pan);
		}
	}
}
