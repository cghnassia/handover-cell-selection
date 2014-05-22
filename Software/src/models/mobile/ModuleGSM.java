package models.mobile;

import java.util.Set;

import models.network.CellGSM;

public class ModuleGSM extends Module{
	
	protected CellGSM service;
	protected Set<CellGSM> neighbors;
	protected Set<CellGSM> cells;
	
	public ModuleGSM() {
		super();
	}
	
	public CellGSM getService() {
		return this.service;
	}
	
	public void setService(CellGSM service) {
		if(this.service != null) {
			System.out.println("-------Reselection-------- \n last :" + this.service.getC2Criterion(true));
			this.service.setSelected(false);
		}
		
		if(service != null) {
			service.setSelected(true);
			System.out.println("new :" + service.getC2Criterion(false));
		}
		
		
		this.service = service;
	}
	
	public Set<CellGSM> getCells() {
		return this.cells;
	}
	
	public void setCells(Set<CellGSM> cells) {
		this.cells = cells;
	}
	
	public void updateModule() {
		if (this.getService() != null && this.getService().getStrength() < CellGSM.MINSTRENGTH) {
			this.setService(null);
		}
		
		if (this.service == null) {
			this.makeSelection();
		}
		else {
			this.makeIdleReselection();
		}
	}
	
	private void makeSelection () {
		CellGSM current = null;
		for(CellGSM cell: this.getCells()) {
			if (cell.getC1Criterion() >= 0) {
				if(current == null) {
					current = cell;
				}
				else if(cell.getC1Criterion() > current.getC1Criterion()) {
					current = cell;
				}
			}
		}
		
		if (current != null) {
			this.setService(current);
		}
	}
	
	private void makeIdleReselection() {
		//we only check neighbors -- only for test
		CellGSM current = null;
		for(CellGSM cell: this.getCells()) {
			if(cell == this.getService()) {
				continue;
			}
			
			if (cell.getC1Criterion() >= 0) {
				if(current == null && cell.getC2Criterion(false) > this.getService().getC2Criterion(true)) {
					current = cell;
				}
				else if (current != null && cell.getC2Criterion(false) > current.getC2Criterion(false)) {
					current = cell;
				}
			}
		}
		
		if(current != null && current != this.service) {
			this.setService(current);
			System.out.println("reselected!!!");
		}
	}

}
