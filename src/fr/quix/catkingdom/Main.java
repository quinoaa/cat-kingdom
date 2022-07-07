package fr.quix.catkingdom;


import java.awt.Dimension;

import javax.swing.JFrame;

import fr.quix.catkingdom.ressources.Ressources;
import fr.quix.catkingdom.ui.Panel;
import fr.quix.catkingdom.ui.handler.Loading;

public class Main {
	public static void main(String[] args) {
		
		
		JFrame frame = new JFrame();
		
		Panel pan = new Panel(frame);
		pan.handler = new Loading(pan);
		frame.add(pan);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setMinimumSize(new Dimension(700, 450));
		frame.setSize(new Dimension(900, 550));
		
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
		pan.updater.start();
		
		Ressources.Load();
		
	}
}
