package models.mobile;

import java.util.HashSet;
import java.util.Set;

import models.application.ApplicationModel;
import models.area.AreaModel;
import models.network.Cell;
import models.network.CellGSM;
import models.network.CellUMTS;
import config.MainConfig;

public class Mobile implements Runnable {
	
	public static final int POWER = 10;
	
	private float x;
	private float y;
	private int speed;
	
	private Cell service;
	private boolean isCalling;
	private boolean isData;
	
	protected int measureFrequency;	//number of measures per second
	protected int measureCount;		//number of measure for average
	
	private ModuleGSM moduleGSM;
	private ModuleUMTS moduleUMTS;
	
	private Motion motion;
	
	private static Mobile mobile;
	
	public static final int DEFAULT_MEASURE_FREQUENCY = 10;	
	public static final int DEFAULT_MEASURE_COUNT = 10;		
	
	public Mobile() {
		this.moduleGSM = new ModuleGSM(this);
		this.moduleUMTS = new ModuleUMTS(this);
		
		this.setCalling(false);
		this.setData(false);
		
		this.setMeasureFrequency(DEFAULT_MEASURE_FREQUENCY);
		this.setMeasureCount(DEFAULT_MEASURE_COUNT);
		
		this.motion = new Motion();
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
			for(Cell cell: service.getNeighbors()) {
	
				if(cell.getType() == Cell.CELLTYPE_GSM) {
					neighborsGSM.add((CellGSM) cell); 
				}
				else {
					neighborsUMTS.add((CellUMTS) cell); 
				}
			}
		}
		else if (this.getService().getType() == Cell.CELLTYPE_UMTS) {
			this.getModuleUMTS().getActiveSet().clear();
		}
		
		this.service = service;
		this.getModuleGSM().setNeighbors(neighborsGSM);
		this.getModuleUMTS().setNeighbors(neighborsUMTS);
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

		this.getModuleGSM().doMeasurements();
		this.getModuleUMTS().doMeasurements();
		
		Cell serviceTmp = null;
		
		// No Service : We need selection
		if(this.getService() == null) {	
			serviceTmp = this.getModuleGSM().doSelection();
			
			if(serviceTmp == null) {
				serviceTmp = this.getModuleUMTS().doSelection();
			}
		}
		// Maybe we need reselection / handover
		else {
			if(! this.isCalling() && ! this.isData()) {
				if(this.getService().getType() == Cell.CELLTYPE_GSM) {
					serviceTmp = this.getModuleGSM().doIdleReselection();
					
					if (serviceTmp == null) {
						serviceTmp = this.getModuleUMTS().doSelection();
					}
				}
				else { //this.getService().getType() == Cell.CELLTYPE_UMS
					serviceTmp = this.getModuleGSM().doSelection();
					
					if (serviceTmp == null) {
						serviceTmp = this.getModuleUMTS().doIdleReselection();
					}
				}
			}
			else if (this.isData) {
				serviceTmp = this.getModuleUMTS().doDataReselection();
				
				if(serviceTmp == null) {
					serviceTmp = this.getModuleGSM().doDataReselection();
				}
			}
			else if (this.isCalling) {
				serviceTmp = this.getModuleGSM().doHandover();
				
				if(serviceTmp == null) {
					serviceTmp = this.getModuleUMTS().doHandover();
				}
			}
		}
		
		//If we are too far, we instantanly loose connection
		if(serviceTmp != null) {
			int strength = serviceTmp.getStrength(this.getX(), this.getY());
			
			if(serviceTmp.getType() == Cell.CELLTYPE_GSM && strength < CellGSM.COVERAGE_STRENGTH) {
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
}
