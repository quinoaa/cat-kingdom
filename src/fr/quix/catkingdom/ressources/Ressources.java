package fr.quix.catkingdom.ressources;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Ressources {
	public static final String[][] ImageToLoad = {{"introduction.background", "introduction_background.png"},
			{"tile.water", "tile/water.png"},{"block.brick", "block/brick.png"},{"tile.grass", "tile/grass.png"},
			{"entity.player","entity/player.png"},{"ui.menu","ui/menu.png"},{"ui.menuhealth","ui/menu_health.png"}
			,{"ui.heart","ui/heart.png"},{"ui.money","ui/money.png"},{"tile.carpet","tile/carpet.png"},{"tile.planks","tile/planks.png"},
			{"entity.cat1","entity/cat1.png"},{"entity.cat2","entity/cat2.png"},{"entity.cucumber","entity/cucumber.png"},
			{"ui.btn.attack","ui/attack_btn.png"},{"ui.btn.build","ui/build_btn.png"},{"ui.btn.interact","ui/interact_btn.png"}
			,{"ui.btn.left","ui/left.png"},{"ui.btn.right","ui/right.png"},{"entity.pickle","entity/pickle.png"}
			,{"block.canon.head","block/canon_head.png"},{"block.canon.body","block/canon_body.png"}
			,{"block.sniper.body","block/sniper_body.png"},{"block.sniper.head","block/sniper_head.png"}
			,{"block.healer.body","block/healer_body.png"},{"block.healer.head","block/healer_head.png"}
			,{"entity.doggo","entity/doggo.png"},{"entity.ghost","entity/ghost.png"}};
	
	public static final HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	
	public static BufferedImage missing = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
	
	public static int loading = 0;
	public static int loadingmax = 1 + ImageToLoad.length;
	
	public static boolean loadingfinished = false;
	
	public static String loadingStatus = "Waiting function to be called";
	
	public static void Load() {
		loadingStatus = "creating image for missing texture";
		missing.setRGB(0, 0, 16724680);
		missing.setRGB(1, 1, 16724680);
		missing.setRGB(1, 0, 657930);
		missing.setRGB(0, 1, 657930);
		loading++;
		
		for(String[] img : ImageToLoad) {
			loadingStatus = "Loading image: " + img[0];
			try {
				images.put(img[0], ImageIO.read(Ressources.class.getResourceAsStream(img[1])));
			} catch (Exception e) {
				System.err.println("Couldn't load " + img[0] + "(" + img[1] + ")");
			}
			loading++;
		}
		
		loadingfinished = true;
	}
	
	public static BufferedImage getImage(String key) {
		if(images.containsKey(key))
			return images.get(key);
		else
			return missing;
	}
}




