package models.network;

import java.util.Set;

import models.mobile.Mobile;
import config.MainConfig;

public class CellGSM extends Cell {
	

	public static final int COVERAGE_STRENGTH = -94;	//dBm
	
	public static final int DEFAULT_STRENGTH = 35;
	public static final int MIN_STRENGTH = 0;
	public static final int MAX_STRENGTH = 50;
	
	public static final int DEFAULT_RX_ACCESS_MIN = -90;
	public static final int MIN_RX_ACCESS_MIN = -90;
	public static final int MAX_RX_ACCESS_MIN = 0;
	
	public static final int DEFAULT_RESELECT_OFFSET = 0; 
	public static final int MIN_RESELECT_OFFSET = 0; 
	public static final int MAX_RESELECT_OFFSET = 20; 
	
	public static final int DEFAULT_RESELECT_HYSTERESIS = 5; 	//dbm
	public static final int MIN_RESELECT_HYSTERESIS = 0;
	public static final int MAX_RESELECT_HYSTERESIS = 20;
	
	public static int DEFAULT_FREQUENCY = 900;		//MHz
	
	private int offset;							//offset of the channel frequency
	private int rxAccessMin;					//minimum
	private int reselectOffset;					//used to prioritize the cell
	private int reselectHysteresis; 			//usually used only if not same Location Area (LA)

	
	public CellGSM(int id, int power, int frequency, int offset) {
		
		super(id, power, frequency);
		this.setType(Cell.CELLTYPE_GSM);
		this.offset = offset;
		this.rxAccessMin = CellGSM.DEFAULT_RX_ACCESS_MIN;
		this.reselectHysteresis = CellGSM.DEFAULT_RESELECT_HYSTERESIS;
		this.reselectOffset = CellGSM.DEFAULT_RESELECT_OFFSET;
	}
	
	public int getRadius() {
		return (int) (Math.pow(10, (-this.getRxAccessMin() + this.getPower() - 20 * Math.log10(this.getFrequency()) + 27.55)/ (20 * MainConfig.COEFF_REFRACTION)));		
	}
	
	public int getC1Criterion(int mobileStrength) {
		return  mobileStrength - this.rxAccessMin;
	}
	
	public int getC2Criterion(int mobileStrength, boolean isSameLocationArea) {
	
		int res = getC1Criterion(mobileStrength) + this.reselectOffset;
		
		if(! isSameLocationArea) {
			res -= this.reselectHysteresis;
		}
		
		return res;
	}

	public int getOffset() {
		return this.offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public int getRxAccessMin() {
		return this.rxAccessMin;
	}
	
	public void setRxAccessMin(int rxAccessMin) {
		this.rxAccessMin = rxAccessMin;
	}
	
	public int getReselectOffset() {
		return this.reselectOffset;
	}
	
	public void setReselectOffset(int reselectOffset) {
		this.reselectOffset = reselectOffset;
	}
	
	public int getReselectHysteresis() {
		return this.reselectHysteresis;
	}
	
	public void setReselectHysteresis(int reselectHysteresis) {
		this.reselectHysteresis = reselectHysteresis;
	}
	
	

}
