package models.mobile;

import java.util.HashSet;
import java.util.Set;

import javax.swing.event.EventListenerList;

import models.application.ApplicationModel;
import models.area.AreaModel;
import models.network.Cell;
import models.network.CellGSM;
import models.network.CellUMTS;
import models.utilities.Formulas;
import config.MainConfig;
import events.CellEvent;
import events.CellListener;
import events.MobileEvent;
import events.MobileListener;

public class Mobile implements Runnable {
	
	public static final int POWER = 10;
	
	private float x;
	private float y;
	private int speed;
	
	private Cell service;
	
	private boolean isPower;
	private boolean isCalling;
	private boolean isData;
	
	private boolean isGSM;
	private boolean isUMTS;
	private boolean isEDGE;
	private boolean isGPRS;
	
	protected int measureFrequency;	//number of measures per second
	protected int measureCount;		//number of measure for average
	
	private ModuleGSM moduleGSM;
	private ModuleUMTS moduleUMTS;
	
	private Motion motion;
	
	protected EventListenerList listenerList;
	
	private static Mobile mobile;
	
	public static final int DEFAULT_MEASURE_FREQUENCY = 10;	
	public static final int DEFAULT_MEASURE_COUNT = 10;		
	
	public Mobile() {
		this.moduleGSM = new ModuleGSM(this);
		this.moduleUMTS = new ModuleUMTS(this);
		
		this.setPower(false);
		this.setCalling(false);
		this.setData(false);
		
		this.setGSM(true);
		this.setGPRS(true);
		this.setEDGE(true);
		this.setUMTS(true);
		
		this.setMeasureFrequency(DEFAULT_MEASURE_FREQUENCY);
		this.setMeasureCount(DEFAULT_MEASURE_COUNT);
		
		this.motion = new Motion();
		this.listenerList = new EventListenerList();
	}
	
	public static Mobile Instance() {
		if (Mobile.mobile == null) {
			Mobile.mobile = new Mobile();
		}
		
		return Mobile.mobile;
	}
	
	public float getX() {
		return this.x;
	}
	
	public void setX(float x) {
		AreaModel areaModel = AreaModel.Instance();
		
		this.x = x;
		if(this.x < 0) {
			this.x = 0;
		}
		else if (this.x > areaModel.getAreaWidth() * AreaModel.AREA_MAX_SCALE) {
			this.x = areaModel.getAreaWidth() * AreaModel.AREA_MAX_SCALE;
		}
	}
	
	public float getY() {
		return this.y;
	}
	
	public void setY(float y) {
		AreaModel areaModel = AreaModel.Instance();
		
		this.y = y;
		if(this.y < 0) {
			this.y = 0;
		}
		else if (this.y > areaModel.getAreaHeight() * AreaModel.AREA_MAX_SCALE) {
			this.y = areaModel.getAreaHeight() * AreaModel.AREA_MAX_SCALE;
		}
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public Cell getService() {
		return this.service;
	}
	
	public void setService(Cell service) {
		
		if(this.service == service) {
			return;
		}
		
		Set<CellGSM> neighborsGSM = new HashSet<>();
		Set<CellUMTS> neighborsUMTS = new HashSet<>();

		if(service != null) {
			
			//If we go from UMTS to GSM, we need to empty active set
			if(service.getType() == Cell.CELLTYPE_GSM) {
				this.getModuleUMTS().getActiveSet().clear();
			}
			
			//We need to update neighbors
			if(service.getNeighbors() != null) {
			
				for(Cell cell: service.getNeighbors()) {
		
					if(cell.getType() == Cell.CELLTYPE_GSM) {
						neighborsGSM.add((CellGSM) cell); 
					}
					else {
						neighborsUMTS.add((CellUMTS) cell); 
					}
				}
			}
		}
		else if (this.getService().getType() == Cell.CELLTYPE_UMTS) {
			this.getModuleUMTS().getActiveSet().clear();
		}
		
		this.service = service;
		this.getModuleGSM().setNeighbors(neighborsGSM);
		this.getModuleUMTS().setNeighbors(neighborsUMTS);
		
		//dispatch events
		if(this.getService() == null) {
			this.fireCallChangeEvent(new MobileEvent(this, MobileEvent.MOBILE_CALL_NO));
			this.fireDataChangeEvent(new MobileEvent(this, MobileEvent.MOBILE_DATA_NO));
		}
		else {
			this.fireCallChangeEvent(new MobileEvent(this, MobileEvent.MOBILE_CALL_OK));
			
			if(this.getService().getType() == Cell.CELLTYPE_GSM && ! this.isGPRS() && ! this.isEDGE()) {
				this.fireDataChangeEvent(new MobileEvent(this, MobileEvent.MOBILE_DATA_NO));
			}
			else {
				this.fireDataChangeEvent(new MobileEvent(this, MobileEvent.MOBILE_DATA_OK));
			}
		}
	}
	
	public boolean isPower() {
		return this.isPower;
	}
	
	public void setPower(boolean isPower) {
		this.isPower = isPower;
		if(! this.isPower) {
			
			if(this.getService() != null) {
				this.getService().setSelected(false);
				this.getService().setActive(false);
				this.setService(null);
			}
		}
	}
	
	public boolean isCalling() {
		return this.isCalling;
	}

	public void setCalling(boolean isCalling) {
		this.isCalling = isCalling;
		
		/*if(this.getService() != null) {
			this.getService().setActive(this.isCalling() || this.isData());
		}*/
		
		if(this.getService() == null || (! this.isCalling() && ! this.isData())){
 			this.getModuleUMTS().getActiveSet().clear();
		}
	}

	public boolean isData() {
		return this.isData;
	}

	public void setData(boolean isData) {
		this.isData = isData;
		
		/*if(this.getService() != null) {
			this.getService().setActive(this.isCalling() || this.isData());
		}*/
		
		if(this.getService() == null || (! this.isCalling() && ! this.isData())){
			this.getModuleUMTS().getActiveSet().clear();
		}
	}
	
	public int getMeasureFrequency() {
		return this.measureFrequency;
	}
	
	public void setMeasureFrequency(int measureFrequency) {
		this.measureFrequency = measureFrequency;
	}
	
	public int getMeasureCount() {
		return this.measureCount;
	}
	
	public void setMeasureCount(int measureCount) {
		this.measureCount = measureCount;
	}
	
	public ModuleGSM getModuleGSM() {
		return this.moduleGSM;
	}
	
	public ModuleUMTS getModuleUMTS() {
		return this.moduleUMTS;
	}
	
	public Motion getMotion() {
		return this.motion;
	}
	
	public void updatePosition() {
		ApplicationModel applicationModel = ApplicationModel.Instance();

		if (this.getMotion().getMotionHorizontal() == Motion.MOVE_LEFT) {
			this.setX(this.getX() - this.speed / (float) applicationModel.getFpsRate());
		}
		else if (this.getMotion().getMotionHorizontal() == Motion.MOVE_RIGHT) {
			this.setX(this.getX() + this.speed / (float) applicationModel.getFpsRate());
		}
		
		if (this.getMotion().getMotionVertical() == Motion.MOVE_UP) {
			this.setY(this.getY() - this.speed / (float) applicationModel.getFpsRate());
		}
		else if (this.getMotion().getMotionVertical() == Motion.MOVE_DOWN) {
			this.setY(this.getY() + this.speed / (float) applicationModel.getFpsRate());
		}
	}
	
	public void checkIsInArea() {
		//To be sure the phone stay in the Area
		this.setX(this.getX());
		this.setY(this.getY());
	}
	
	//for handling connections with network
	public void updateMobile() {
		
		if(! this.isPower()) {
			this.setService(null);
			return;
		}
		
		if(this.isGSM()) {
			this.getModuleGSM().doMeasurements();
		}
		if(this.isUMTS()) {
			
			boolean isUMTSMeasurement = true;
			if(this.getService() != null && this.getService().getType() == Cell.CELLTYPE_GSM) {
				
				int qs;
				if(this.isData() || this.isCalling()) {
					qs = ((CellGSM) service).getQSC();
				}
				else {
					qs = ((CellGSM) service).getQSI();
				}
				isUMTSMeasurement = Formulas.isUMTSMeasurement(this.getService().getStrength(this.getX(), this.getY()), qs);
			}
		
			if(isUMTSMeasurement) {
				this.getModuleUMTS().doMeasurements();
			}
		}
		
		Cell serviceTmp = null;
		
		// No Service : We need selection
		if(this.getService() == null) {	
			if(this.isGSM()) {
				serviceTmp = this.getModuleGSM().doSelection();
			}
			
			if(this.isUMTS() && serviceTmp == null) {
				serviceTmp = this.getModuleUMTS().doSelection();
			}
		}
		// Maybe we need reselection / handover
		else {
			if(! this.isCalling() && ! this.isData()) {
				if(this.getService().getType() == Cell.CELLTYPE_GSM) {
					serviceTmp = this.getModuleGSM().doIdleReselection();
					
					if (this.isUMTS() && serviceTmp == null) {
						serviceTmp = this.getModuleUMTS().doSelection();
					}
				}
				else { //this.getService().getType() == Cell.CELLTYPE_UMTS
					
					if(this.isGSM()) {
						serviceTmp = this.getModuleGSM().doSelection();
					}
					
					if (this.isUMTS() && serviceTmp == null) {
						serviceTmp = this.getModuleUMTS().doIdleReselection();
					}
				}
			}
			else if (this.isData) {
				
				if(this.isUMTS()) {
					serviceTmp = this.getModuleUMTS().doDataReselection();
				}
				
				if(this.isGSM() && serviceTmp == null) {
					serviceTmp = this.getModuleGSM().doDataReselection();
				}
			}
			else if (this.isCalling) {
				
				if (this.isGSM()) {
					serviceTmp = this.getModuleGSM().doHandover();
				}
				
				if(this.isUMTS() && serviceTmp == null) {
					serviceTmp = this.getModuleUMTS().doHandover();
				}
			}
		}
		
		//If the select cell is disabled or we are too far, we instantanly loose connection
		if(serviceTmp != null) {
			int strength = serviceTmp.getStrength(this.getX(), this.getY());
			
			if(! serviceTmp.isEnabled()) {
				serviceTmp = null;
			}
			else if(serviceTmp.getType() == Cell.CELLTYPE_GSM && strength < CellGSM.COVERAGE_STRENGTH) {
				serviceTmp = null;
			}
			else if (serviceTmp.getType() == Cell.CELLTYPE_UMTS && strength < CellUMTS.COVERAGE_STRENGTH) {
				serviceTmp  = null;
			}
		}
		
		if(this.getService() != null) {
			this.getService().setSelected(false);
			this.getService().setActive(false);
		}
		
		if(serviceTmp != null) {
			serviceTmp.setSelected(true);
			serviceTmp.setActive(this.isCalling() || this.isData());
		}
		else {
			this.setCalling(false);
			this.setData(false);
		}
		
		if(serviceTmp == null || serviceTmp.getType() == Cell.CELLTYPE_GSM) {
			this.getModuleUMTS().getActiveSet().clear();
		}
		
		this.setService(serviceTmp);
	}
	
	public void addMobileListener(MobileListener mobileListener) {
		this.listenerList.add(MobileListener.class, mobileListener);
	}
	
	public void removeMobileListener(MobileListener mobileListener) {
		this.listenerList.remove(MobileListener.class, mobileListener);
	}
	
	protected void fireDataChangeEvent(MobileEvent mobileEvent) {
		Object[] listeners = this.listenerList.getListenerList();
	     
	     int numListeners = listeners.length;
	     for (int i = 0; i< numListeners; i += 2) 
	     {
	          if (listeners[i] == MobileListener.class) 
	          {
	               // pass the event to the listeners event dispatch method
	                ((MobileListener)listeners[i+1]).dataChanged(mobileEvent);
	          }            
	     }
	}
	
	protected void fireCallChangeEvent(MobileEvent mobileEvent) {
		Object[] listeners = this.listenerList.getListenerList();
	     
	     int numListeners = listeners.length;
	     for (int i = 0; i< numListeners; i += 2) 
	     {
	          if (listeners[i] == MobileListener.class) 
	          {
	               // pass the event to the listeners event dispatch method
	                ((MobileListener)listeners[i+1]).callChanged(mobileEvent);
	          }            
	     }
	}
	
	
	@Override
	public void run() {
		while(true) {
			try {
				this.updateMobile();
				Thread.currentThread().sleep(1000 / this.getMeasureFrequency());
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isGSM() {
		return this.isGSM;
	}

	public void setGSM(boolean isGSM) {
		this.isGSM = isGSM;
		
		if(! isGSM) {
			this.isGPRS = false;
			this.isEDGE = false;
			
			if(this.getService() != null && this.getService().getType() == Cell.CELLTYPE_GSM) {
				this.getService().setSelected(false);
				this.getService().setActive(false);
				this.setService(null);
			}
		}
	}

	public boolean isUMTS() {
		return this.isUMTS;
	}

	public void setUMTS(boolean isUMTS) {
		this.isUMTS = isUMTS;
		
		if(! isUMTS && this.getService() != null &&  this.getService().getType() == Cell.CELLTYPE_UMTS) {
			this.getService().setSelected(false);
			this.getService().setActive(false);
			this.setService(null);
		}
	}

	public boolean isEDGE() {
		return this.isEDGE;
	}

	public void setEDGE(boolean isEDGE) {
		this.isEDGE = isEDGE;
		
		if(this.getService() != null) { 
			if (this.getService().getType() == Cell.CELLTYPE_GSM  && ! this.isEDGE() && ! this.isGPRS()) {
				this.setData(false);
				this.fireDataChangeEvent(new MobileEvent(this, MobileEvent.MOBILE_DATA_NO));
			}
			else {
				this.fireDataChangeEvent(new MobileEvent(this, MobileEvent.MOBILE_DATA_OK));
			}
		}
	}

	public boolean isGPRS() {
		return this.isGPRS;
	}

	public void setGPRS(boolean isGPRS) {
		this.isGPRS = isGPRS;
		
		if(this.getService() != null) { 
			if (this.getService().getType() == Cell.CELLTYPE_GSM  && ! this.isEDGE() && ! this.isGPRS()) {
				this.setData(false);
				this.fireDataChangeEvent(new MobileEvent(this, MobileEvent.MOBILE_DATA_NO));
			}
			else {
				this.fireDataChangeEvent(new MobileEvent(this, MobileEvent.MOBILE_DATA_OK));
			}
		}
	}
}
