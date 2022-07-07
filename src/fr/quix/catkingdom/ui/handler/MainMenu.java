package fr.quix.catkingdom.ui.handler;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import fr.quix.catkingdom.ui.Panel;

public class MainMenu extends Handler{
	public final MouseListener mouseListener = new MouseListener() {
		@Override public void mouseReleased(MouseEvent e) {}
		@Override public void mouseExited(MouseEvent e) {}
		@Override public void mouseEntered(MouseEvent e) {}
		@Override public void mouseClicked(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {
			if(mousex>(pan.getWidth()-300)/2&&mousex<(pan.getWidth()+300)/2&&mousey>(pan.getHeight()-100)/2&&mousey<(pan.getHeight())/2)
				clicked = true;
			if(mousex>(pan.getWidth()-400)/2&&mousex<(pan.getWidth()+400)/2&&mousey>(pan.getHeight()+45)/2&&mousey<(pan.getHeight()/2)+85)
				ldpage = true;
		}
		
	};
	public final MouseMotionListener mouseMotionListener = new MouseMotionListener() {
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
	
	public int mousex=-1, mousey=-1;
	public boolean clicked = false;
	public boolean ldpage = false;
	
	Panel pan;
	public MainMenu(Panel pan) {
		super(pan);
		this.pan = pan;
		pan.addMouseListener(mouseListener);
		pan.addMouseMotionListener(mouseMotionListener);
	}
	@Override
	public void paint(Graphics2D g2D) {
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2D.setColor(new Color(102, 173, 255));
		g2D.fillRect(0, 0, pan.getWidth(), pan.getHeight());

		g2D.setColor(Color.WHITE);
		g2D.fill(new RoundRectangle2D.Double((pan.getWidth()-300)/2, (pan.getHeight()-100)/2, 300, 50, 25, 25));

		g2D.setColor(Color.BLACK);
		g2D.setFont(new Font(Font.SANS_SERIF, 0, 40));
		g2D.drawString("Play", (pan.getWidth()-g2D.getFontMetrics().stringWidth("Play"))/2, (pan.getHeight()/2)-12);

		g2D.setColor(Color.WHITE);
		g2D.fill(new RoundRectangle2D.Double((pan.getWidth()-400)/2, (pan.getHeight()+45)/2, 400, 50, 25, 25));

		g2D.setColor(Color.BLACK);
		g2D.drawString("Ludum dare page", (pan.getWidth()-g2D.getFontMetrics().stringWidth
				("Ludum dare page"))/2, (pan.getHeight()/2)+60);

		g2D.setFont(new Font(Font.SANS_SERIF, 0, 50));
		g2D.setColor(Color.WHITE);
		g2D.drawString("Cat Kingdom", (pan.getWidth()-g2D.getFontMetrics().stringWidth
				("Cat Kingdom"))/2, 60);

		if(clicked == true) {
			pan.removeMouseListener(mouseListener);
			pan.removeMouseMotionListener(mouseMotionListener);
			
			pan.handler = new Introduction(pan);
		}
		if(ldpage==true) {
			ldpage = false;
			try {
				Desktop.getDesktop().browse(new URI("https://google.fr"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}






