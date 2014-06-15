package models.network;

import java.util.Set;

import models.mobile.Mobile;
import models.utilities.Formulas;
import config.MainConfig;

public class CellGSM extends Cell {
	

	public static final int COVERAGE_STRENGTH = -94;	//dBm
	
	public static final int DEFAULT_STRENGTH = 38;
	public static final int MIN_STRENGTH = 0;
	public static final int MAX_STRENGTH = 50;
	
	public static final int DEFAULT_RX_ACCESS_MIN = -90;
	public static final int MIN_RX_ACCESS_MIN = -90;
	public static final int MAX_RX_ACCESS_MIN = 0;
	
	public static final int DEFAULT_RX_ACCESS_GRPS_MIN = -90;
	public static final int MIN_RX_ACCESS_GPRS_MIN = -90;
	public static final int MAX_RX_ACCESS_GPRS_MIN = 0;
	
	public static final int DEFAULT_RESELECT_OFFSET = 0; 
	public static final int MIN_RESELECT_OFFSET = 0; 
	public static final int MAX_RESELECT_OFFSET = 20; 
	
	public static final int DEFAULT_RESELECT_HYSTERESIS = 5; 	//dbm
	public static final int MIN_RESELECT_HYSTERESIS = 0;
	public static final int MAX_RESELECT_HYSTERESIS = 20;
	
	public static int DEFAULT_FREQUENCY = 900;		//MHz
	public static int DEFAULT_FREQUENCY_OFFSET = 0;
	
	public static int DEFAULT_QSC = 7;
	public static int DEFAULT_QSI = 7;

	
	private int frequencyOffset;							//offset of the channel frequency
	private int rxAccessMin;					//minimum
	private int rxAccessGPRSMin;
	private int reselectOffset;					//used to prioritize the cell
	private int reselectHysteresis; 			//usually used only if not same Location Area (LA)
	private int qsi;
	private int qsc;

	
	public CellGSM(int id) {
		
		super(id);
		this.setType(Cell.CELLTYPE_GSM);
		this.setPower(CellGSM.DEFAULT_STRENGTH);
		this.setFrequency(CellGSM.DEFAULT_FREQUENCY);
		this.setFrequencyOffset(CellGSM.DEFAULT_FREQUENCY_OFFSET);
		this.setRxAccessMin(CellGSM.DEFAULT_RX_ACCESS_MIN);
		this.setReselectHysteresis(CellGSM.DEFAULT_RESELECT_HYSTERESIS);
		this.setReselectOffset(CellGSM.DEFAULT_RESELECT_OFFSET);;
		this.setRxAccessGPRSMin(CellGSM.DEFAULT_RX_ACCESS_GRPS_MIN);
		this.setQSC(CellGSM.DEFAULT_QSC);
		this.setQSI(CellGSM.DEFAULT_QSI);
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
	
	public int getC1PCriterion(int mobileStrength) {
		return mobileStrength - this.rxAccessGPRSMin;
	}
	
	//we don't use it right now
	public int C31Criterion(int mobileStrength) {
		return this.getC1PCriterion(mobileStrength);
	}
	
	public int getC32Criterion(int mobileStrength, boolean isSameLocationArea) {
		return this.getC2Criterion(mobileStrength, isSameLocationArea);
	}

	public int getFrequencyOffset() {
		return this.frequencyOffset;
	}

	public void setFrequencyOffset(int frequencyOffset) {
		this.frequencyOffset = frequencyOffset;
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
	
	public int getRxAccessGPRSMin() {
		return this.rxAccessGPRSMin;
	}
	
	public void setRxAccessGPRSMin(int rxAccessGPRSMin) {
		this.rxAccessGPRSMin = rxAccessGPRSMin;
	}
	
	public int getQSC() {
		return this.qsc;
	}

	public void setQSC(int qsc) {
		this.qsc = qsc;
	}

	public int getQSI() {
		return this.qsi;
	}

	public void setQSI(int qsi) {
		this.qsi = qsi;
	}

}
