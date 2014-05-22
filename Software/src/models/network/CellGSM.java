package models.network;

import models.mobile.Mobile;
import config.MainConfig;

public class CellGSM extends Cell {
	
	public static int STRENGTH = 20;
	public static int FREQUENCY = 900;		//MHz
	public static int MINSTRENGTH = -90;	//dBm
	
	public static int RX_ACCESS_MIN = -90;
	public static int RESELECT_HYSTERESIS = 5; 	//dbm
	
	private int group;
	private boolean isSelected;
	private int rxAccessMin;			
	private int reselectOffset;					//used to prioritize the cell
	private int reselectHysteresis; 			//usually used only if not same Location Area (LA)
	
	//power of GSM antenna is between 34 dBm and 55 dBm depending on its class

	
	public CellGSM(int power, int frequency, int group) {
		
		super(power, frequency);
		this.group = group;
		this.setType(Cell.CELLTYPE_GSM);
		this.rxAccessMin = CellGSM.RX_ACCESS_MIN;
		this.reselectHysteresis = CellGSM.RESELECT_HYSTERESIS;
		this.reselectOffset = 0;
	}
	
	protected void calculateRadius() {

		//int radius = (int) (Math.pow(10, (-CellGSM.MINSTRENGTH + this.getPower() - 32.44 - 20 * Math.log10(this.getFrequency()))/ (20 * MainConfig.COEFF_REFRACTION)) * 1000);
		int radius = 1000;
		/*System.out.println("------------");
		System.out.println(CellGSM.MINSTRENGTH);
		System.out.println(this.getPower());
		System.out.println(this.getFrequency());
		System.out.println(MainConfig.COEFF_REFRACTION);
		System.out.println("radius" + diameter /2);*/
		this.setRadius(radius);
	}
	
	public int getStrength() {
		//double distance =  Math.sqrt(Math.pow(Math.abs(this.getX() - this.getMobile().getX()), 2) + Math.pow(Math.abs(this.getY() - this.getMobile().getY()), 2));
		//double strength = this.getPower() - 32.44 - 20 * Math.log10(this.getFrequency()) - 20 * MainConfig.COEFF_REFRACTION * Math.log10(distance / 1000);
		
		return 0; //return (int) Math.round(strength);
	}
	
	public int getC1Criterion() {
		return this.getStrength() - this.rxAccessMin;
	}
	
	public int getC2Criterion(boolean isService) {
		//this.reselectHysteresis is only use when it's not the same location Area
		int res = getC1Criterion() + this.reselectOffset;
		if(! isService) {
			res -= this.reselectHysteresis;
		}
		
		return res;
	}
	
	public boolean isSelected() {
		return this.isSelected;
	}
	
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public int getGroup() {
		return this.group;
	}

	public void setGroup(int group) {
		this.group = group;
	}
	
	

}
