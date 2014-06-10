package config;

import java.awt.Color;

public final class MainConfig {
	
	public static final String FILE_CONFIG = "config/application.xml";

	public static final double COEFF_REFRACTION = 1.5;
	
	public static final Color AREA_COLOR = new Color(239, 235, 214);
	public static final Color CELLGSM_COLOR = new Color(232, 104, 80);
	public static final Color CELLGSM_COLOR_FILLED = new Color(232, 104, 80, 85);
	public static final Color CELLUMTS_COLOR = new Color(88, 112, 88);
	public static final Color CELLUMTS_COLOR_FILLED_ACTIVE = new Color(88, 112, 88, 85);
	public static final Color CELLUMTS_COLOR_FILLED_ACTIVESET = new Color(88, 112, 88, 35);
	
	public static final int MOBILE_WIDTH = 12;
	public static final int MOBILE_HEIGHT = 25;
	
	public static final int ANTENNA_WIDTH = 29;
	public static final int ANTENNA_HEIGHT = 25;
	
	public static final int SPEED_MIN = 0;
	public static final int SPEED_MAX = 500;
}
