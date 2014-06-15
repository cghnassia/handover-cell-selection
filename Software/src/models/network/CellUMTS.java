package models.network;

import models.mobile.Mobile;
import models.utilities.Formulas;
import config.MainConfig;
import events.CellEvent;
import events.CellListener;

public class CellUMTS extends Cell {
	
	public static final int COVERAGE_STRENGTH = -116;	//dBm
	
	public static final int DEFAULT_STRENGTH = 27;
	public static final int MIN_STRENGTH = 0;
	public static final int MAX_STRENGTH = 50;
	
	public static final int DEFAULT_QRXLEV_MIN = -112;
	public static final int MIN_QRXLEV_MIN = -112;
	public static final int MAX_QRXLEV_MIN = 0;
	
	public static final int DEFAULT_QQUAL_MIN = -112;
	public static final int MIN_QQUAL_MIN = -112;
	public static final int MAX_QQUAL_MIN = 0;
	
	public static final int DEFAULT_ACTIVESET_RANGE = 3;
	public static final int MIN_ACTIVESET_RANGE = 0;
	public static final int MAX_ACTIVESET_RANGE = 10;
	
	public static int DEFAULT_FREQUENCY = 2100;		//MHz
	
	private int qRxLevMin;		//dBm
	private int qRxLevOffset;	//dBm (for cell priority)
	private int qQualMin;		//dB
	private int qQualOffset;	//dB (for cell priority)
	private int activeSetRange ;
	
	private boolean inActiveSet;
	
	public CellUMTS(int id) {
		
		super(id);
		this.setType(Cell.CELLTYPE_UMTS);
		this.setPower(CellUMTS.DEFAULT_STRENGTH);
		this.setFrequency(CellUMTS.DEFAULT_FREQUENCY);
		this.setQRxLevMin(DEFAULT_QRXLEV_MIN);
		this.setQQualMin(DEFAULT_QQUAL_MIN);
		this.setActiveSetRange(DEFAULT_ACTIVESET_RANGE);
		
		//not used at this time
		this.qRxLevOffset = 0;
		this.qQualOffset = 0;
		
		this.inActiveSet = false;
	}
	
	public int getRadius() {		
		return (int) (Math.pow(10, (-this.qRxLevMin + this.getPower() - 20 * Math.log10(this.getFrequency()) + 27.55)/ (20 * MainConfig.COEFF_REFRACTION)));		
	}
	
	@Override
	public int getPower() {
		return super.getPower() - 10;
	}
	
	public int getFullPower() {
		return super.getPower();
	}
	
	public int getQRxLevMin() {
		return this.qRxLevMin;
	}
	
	public void setQRxLevMin(int qRxLevMin) {
		this.qRxLevMin = qRxLevMin;
	}
	
	public int getQQualMin() {
		return this.qQualMin;
	}
	
	public void setQQualMin(int qQualMin) {
		this.qQualMin = qQualMin;
	}
	
	public int getActiveSetRange() {
		return this.activeSetRange;
	}

	public void setActiveSetRange(int activeSetRange) {
		this.activeSetRange = activeSetRange;
	}
	
	public int getSRxLevCriterion(int strength) {
		return strength - (this.qRxLevMin + this.qRxLevOffset);
	}
	
	public int getSQualCriterion(int EcIo) {
		return EcIo - (this.qQualMin + this.qQualOffset);
	}
	
	public void setInActiveSet(boolean inActiveSet) {
		
		this.inActiveSet = inActiveSet;
		
		if(this.inActiveSet) {
			fireAddActiveSetEvent(new CellEvent(this, CellEvent.CELL_ACTIVESET_ADDED));
		}
		else {
			fireRemoveActiveSetEvent(new CellEvent(this, CellEvent.CELL_ACTIVESET_REMOVED));
		}
	}
	
	public boolean isInActiveSet() {
		return this.inActiveSet;
	}
	
	protected void fireAddActiveSetEvent(CellEvent cellEvent) {
		Object[] listeners = this.listenerList.getListenerList();
	     
	     int numListeners = listeners.length;
	     for (int i = 0; i< numListeners; i += 2) 
	     {
	          if (listeners[i] == CellListener.class) 
	          {
	               // pass the event to the listeners event dispatch method
	                ((CellListener)listeners[i+1]).cellAddedInActiveSet(cellEvent);
	          }            
	     }
	}
	
	protected void fireRemoveActiveSetEvent(CellEvent cellEvent) {
		Object[] listeners = this.listenerList.getListenerList();
	     
	     int numListeners = listeners.length;
	     for (int i = 0; i< numListeners; i += 2) 
	     {
	          if (listeners[i] == CellListener.class) 
	          {
	               // pass the event to the listeners event dispatch method
	                ((CellListener)listeners[i+1]).cellRemovedFromActiveSet(cellEvent);
	          }            
	     }
	}
	
}
