package models.network;

import models.mobile.Mobile;
import config.MainConfig;

public class CellUMTS extends Cell {
	
	public static int FREQUENCY = 2100;		//MHz
	public static int STRENGTH = 20;		//dBm
	//public static int MINSTRENGTH = -117;	//dBm
	public static int MINSTRENGTH = -75;	//dBm
	
	public CellUMTS(int power, int frequency) {
		
		super(power, frequency);
		this.setType(Cell.CELLTYPE_UMTS);
	}
	
	protected void calculateRadius() {
				
		int diameter = (int) (Math.pow(10, (-CellUMTS.MINSTRENGTH + this.getPower() - 32.44 - 20 * Math.log10(this.getFrequency()))/ (20 * MainConfig.COEFF_REFRACTION)) * 1000);
		this.setRadius(diameter / 2);
	}
}
