package models.utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class LoadImage {
	
	public static final String MOBILE = "img/mobile.png";
	public static final String ANTENNA = "img/antenna.png";
	
	public static final String BUTTON_POWER_ON = "img/button_power_on.png";
	public static final String BUTTON_POWER_OFF = "img/button_power_off.png";
	
	public static final String BUTTON_CALL_ON = "img/button_call_on.png";
	public static final String BUTTON_CALL_OFF = "img/button_call_off.png";
	//public static final String BUTTON_CALL_INACTIVE = "img/button_call_inactive.png";
	
	public static final String BUTTON_DATA_ON = "img/button_data_on.png";
	public static final String BUTTON_DATA_OFF = "img/button_data_off.png";
	//public static final String BUTTON_DATA_INACTIVE = "img/button_data_inactive.png";
	
	private LoadImage() {
		//classe statique
	}
	
	public static ImageIcon load(String location) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(location));
		} catch (IOException e) {
			System.out.println("Erreur lors du chargement de : " +  location);
			e.printStackTrace();
		}	
		return new ImageIcon(img);
	}
}
