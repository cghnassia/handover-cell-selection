package models.mobile;

import java.util.Set;

import models.network.Cell;

public abstract class Module {
	
	private Mobile mobile;
	
	public Module(Mobile mobile) {
		this.setMobile(mobile);
	}

	public Mobile getMobile() {
		return this.mobile;
	}

	public void setMobile(Mobile mobile) {
		this.mobile = mobile;
	}
	
	public abstract void doMeasurements();
	
	public abstract void resetMeasurements();
	
	public abstract Cell doSelection();
	
	public abstract Cell doIdleReselection();
	
	public abstract Cell doHandover();
	
	public abstract Cell doDataReselection();
}
